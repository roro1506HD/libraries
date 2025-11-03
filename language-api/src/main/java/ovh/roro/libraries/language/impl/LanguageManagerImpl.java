package ovh.roro.libraries.language.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minecraft.util.GsonHelper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.roro.libraries.language.api.Language;
import ovh.roro.libraries.language.api.LanguageManager;
import ovh.roro.libraries.language.api.Placeholder;
import ovh.roro.libraries.language.api.Translation;
import ovh.roro.libraries.language.api.data.LanguageNumberData;
import ovh.roro.libraries.language.impl.data.LanguageNumberDataImpl;
import ovh.roro.libraries.loader.LibraryInstanceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

@ApiStatus.Internal
public class LanguageManagerImpl implements LanguageManager {

    public static final @NotNull LibraryInstanceLoader<LanguageManagerImpl> LOADER = new LibraryInstanceLoader<>(
            "LanguageManager",
            plugin -> new LanguageManagerImpl()
    );

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger("LanguageManager");
    private static final @NotNull Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private final @NotNull MiniMessage miniMessage;

    private final @NotNull Int2ObjectMap<Language> languagesById;
    private final @NotNull Map<String, Language> languagesByAlpha;
    private final @NotNull List<Language> languages;

    private LanguageManagerImpl() {
        this.miniMessage = MiniMessage.builder()
                .tags(TagResolver.resolver(
                        StandardTags.decorations(),
                        StandardTags.color(),
                        StandardTags.hoverEvent(),
                        StandardTags.keybind(),
                        StandardTags.translatable(),
                        StandardTags.insertion(),
                        StandardTags.reset(),
                        StandardTags.newline(),
                        StandardTags.selector(),
                        TagResolver.resolver(
                                "click",
                                (args, context) -> {
                                    String actionName = args.popOr(() -> "A click tag requires an action of one of " + ClickEvent.Action.NAMES.keys()).lowerValue();
                                    ClickEvent.Action action = ClickEvent.Action.NAMES.value(actionName);

                                    if (action == null) {
                                        throw context.newException("Unknown click event action '" + actionName + "'", args);
                                    }

                                    String value = args.popOr("Click event actions require a value").value();

                                    return Tag.styling(ClickEvent.clickEvent(action, LegacyComponentSerializer.legacyAmpersand().serialize(context.deserialize(value))));
                                }
                        )
                ))
                .build();

        this.languagesById = new Int2ObjectArrayMap<>();
        this.languagesByAlpha = new Object2ObjectArrayMap<>();
        this.languages = new ObjectArrayList<>();
    }

    @Override
    public void load(@NotNull Path languageFolder, @NotNull JavaPlugin plugin, @NotNull String @NotNull [] defaultFiles) {
        if (!Files.exists(languageFolder)) {
            try {
                Files.createDirectories(languageFolder);
            } catch (IOException ex) {
                LanguageManagerImpl.LOGGER.error("Could not create directory {}", languageFolder, ex);
            }

            for (String defaultFile : defaultFiles) {
                try (InputStream inputStream = plugin.getClass().getResourceAsStream(defaultFile)) {
                    if (inputStream == null) {
                        LanguageManagerImpl.LOGGER.error("Could not export default file {} because it couldn't be found", defaultFile);
                        continue;
                    }

                    String fileName = defaultFile;
                    int lastSlash = fileName.lastIndexOf('/');

                    if (lastSlash != -1) {
                        fileName = fileName.substring(lastSlash + 1);
                    }

                    Files.copy(inputStream, languageFolder.resolve(fileName));
                } catch (IOException ex) {
                    LanguageManagerImpl.LOGGER.error("An error occurred while exporting default file {}", defaultFile, ex);
                }
            }
        }

        this.load(languageFolder);
    }

    @Override
    public void load(@NotNull Path languageFolder) {
        if (!Files.exists(languageFolder)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(languageFolder, 1)) {
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    return;
                }

                try (InputStream inputStream = Files.newInputStream(path)) {
                    JsonObject root = LanguageManagerImpl.GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);

                    JsonObject data = GsonHelper.getAsJsonObject(root, "data");
                    JsonObject translations = GsonHelper.getAsJsonObject(root, "translations");

                    int id = GsonHelper.getAsInt(data, "id");
                    String name = GsonHelper.getAsString(data, "name");
                    String alpha = GsonHelper.getAsString(data, "alpha");
                    String fallbackLanguage = data.has("fallback_language") ? GsonHelper.getAsString(data, "fallback_language") : null;
                    String headTexture = GsonHelper.getAsString(data, "head_texture");

                    // Number data
                    JsonObject numberData = GsonHelper.getAsJsonObject(data, "number_data");
                    String groupSeparator = GsonHelper.getAsString(numberData, "group_separator");
                    String decimalSeparator = GsonHelper.getAsString(numberData, "decimal_separator");
                    LanguageNumberDataImpl languageNumberData = new LanguageNumberDataImpl(groupSeparator, decimalSeparator);

                    LanguageImpl language = new LanguageImpl(
                            this,
                            id,
                            name,
                            alpha,
                            fallbackLanguage,
                            headTexture,
                            languageNumberData
                    );

                    for (Map.Entry<String, JsonElement> entry : translations.entrySet()) {
                        language.translations().put(
                                entry.getKey(),
                                GsonHelper.convertToString(entry.getValue(), "translations." + entry.getKey())
                        );
                    }

                    this.languagesById.put(id, language);
                    this.languagesByAlpha.put(alpha, language);
                    this.languages.add(language);
                } catch (IOException ex) {
                    LanguageManagerImpl.LOGGER.error("An error occurred while parsing {}", path, ex);
                }
            });
        } catch (IOException ex) {
            LanguageManagerImpl.LOGGER.error("An error occurred while walking through language folder", ex);
        }
    }

    @Override
    public @Nullable Language language(@NotNull String alpha) {
        return this.languagesByAlpha.get(alpha);
    }

    @Override
    public @Nullable Language language(int languageId) {
        return this.languagesById.get(languageId);
    }

    @Override
    public @NotNull List<Language> languages() {
        return this.languages;
    }

    @Override
    public @NotNull Component translate(@NotNull Language language, @NotNull String translationKey, @NotNull Placeholder... placeholders) {
        if (!(language instanceof LanguageImpl languageImpl)) {
            throw new IllegalArgumentException("Language is expected to be LanguageImpl");
        }

        String translation = languageImpl.translations().get(translationKey);

        if (translation == null) {
            Language fallbackLanguage = language.fallbackLanguage();

            if (fallbackLanguage == null) {
                return Component.text(translationKey);
            }

            return this.translate(fallbackLanguage, translationKey, placeholders);
        }

        TagResolver[] resolvers = new TagResolver[placeholders.length];

        for (int i = 0; i < placeholders.length; i++) {
            PlaceholderImpl placeholder = (PlaceholderImpl) placeholders[i];

            resolvers[i] = placeholder.toTagResolver().apply(languageImpl);
        }

        return this.miniMessage.deserialize(translation, resolvers);
    }

    @Override
    public @NotNull Component translate(@NotNull Language language, @NotNull Translation translation) {
        return this.translate(language, translation.translationKey(), translation.placeholders());
    }

    @NotNull TagResolver resolveNumber(@NotNull Language language, @TagPattern @NotNull String key, @NotNull Number number) {
        return TagResolver.resolver(key, (argumentQueue, context) -> {
            DecimalFormat decimalFormat;
            DecimalFormatSymbols symbols = this.patchDecimalFormatSymbols(language, new DecimalFormatSymbols(Locale.forLanguageTag(language.alpha())));

            if (argumentQueue.hasNext()) {
                String format = argumentQueue.pop().value();

                decimalFormat = new DecimalFormat(format, symbols);
            } else {
                decimalFormat = new DecimalFormat();
                decimalFormat.setDecimalFormatSymbols(symbols);
            }

            return Tag.inserting(context.deserialize(decimalFormat.format(number)));
        });
    }

    private @NotNull DecimalFormatSymbols patchDecimalFormatSymbols(@NotNull Language language, @NotNull DecimalFormatSymbols original) {
        LanguageNumberData config = language.numberData();

        String groupSeparator = config.groupSeparator();
        original.setGroupingSeparator(groupSeparator.charAt(0));

        String decimalSeparator = config.decimalSeparator();
        original.setDecimalSeparator(decimalSeparator.charAt(0));

        return original;
    }
}