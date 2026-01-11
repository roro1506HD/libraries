package ovh.roro.libraries.componentutil;

import com.google.common.base.Preconditions;
import io.netty.buffer.Unpooled;
import io.papermc.paper.adventure.PaperAdventure;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A class containing utilities for both vanilla and adventure components
 */
public final class ComponentUtil {

    /**
     * The default chat width of a vanilla client, minus 20 to take into account players that
     * reduce their chatbox slightly
     */
    public static final int DEFAULT_CHAT_BOX_WIDTH = 300; // It's actually 320px, but we remove 20 for people that reduce it slightly

    /**
     * Half of the default chat width of a vanilla client.
     * Same as {@code ComponentUtil.DEFAULT_CHAT_BOX_WIDTH / 2}
     */
    public static final int HALF_CHAT_BOX_WIDTH = ComponentUtil.DEFAULT_CHAT_BOX_WIDTH / 2;

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger("ComponentUtil");
    private static final @NotNull Int2ObjectMap<GlyphData> GLYPH_DATA = new Int2ObjectOpenHashMap<>();
    private static final int NEWLINE_CODE_POINT = '\n';

    private static final @NotNull net.minecraft.network.chat.Component FOREGROUND_PREFIX_COMPONENT = net.minecraft.network.chat.Component.empty().withStyle(style -> style.withFont(new FontDescription.Resource(Identifier.fromNamespaceAndPath("component-util", "foreground"))));
    private static final @NotNull net.minecraft.network.chat.Style BACKGROUND_STYLE = net.minecraft.network.chat.Style.EMPTY.withFont(new FontDescription.Resource(Identifier.fromNamespaceAndPath("component-util", "default"))).withShadowColor(0);

    /**
     * @hidden
     */
    private ComponentUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Loads font data from the plugin's {@literal font_data} file, without providing extra glyphs
     *
     * @param plugin the plugin that includes the {@literal font_data} file
     */
    public static void load(@NotNull JavaPlugin plugin) {
        ComponentUtil.load(plugin, Int2ObjectMaps.emptyMap());
    }

    /**
     * Loads font data from the plugin's {@literal font_data} file, and add extra glyphs.
     * Extra glyphs override any glyphs included in {@literal font_data} if they have the same codepoint
     *
     * @param plugin the plugin that includes the {@literal font_data} file
     */
    public static void load(@NotNull JavaPlugin plugin, @NotNull Int2ObjectMap<GlyphData> extraGlyphData) {
        try (InputStream inputStream = plugin.getClass().getResourceAsStream("/font_data")) {
            ComponentUtil.load(Objects.requireNonNull(inputStream, "font_data not found").readAllBytes());

            ComponentUtil.GLYPH_DATA.putAll(extraGlyphData);
        } catch (IOException ex) {
            ComponentUtil.LOGGER.error("An error occurred while loading ComponentUtil's font data", ex);
        }
    }

    /**
     * Returns the width of the adventure component
     *
     * @param component the component to get the width for
     * @return the width
     */
    public static float widthAdventure(@NotNull Component component) {
        return ComponentUtil.widthVanilla(PaperAdventure.asVanilla(component));
    }

    /**
     * Returns the width of the vanilla component
     *
     * @param component the component to get the width for
     * @return the width
     */
    public static float widthVanilla(@NotNull net.minecraft.network.chat.Component component) {
        MutableFloat width = new MutableFloat();

        StringDecomposer.iterateFormatted(component, net.minecraft.network.chat.Style.EMPTY, (index, style, codePoint) -> {
            if (codePoint == ComponentUtil.NEWLINE_CODE_POINT) {
                return true;
            }

            GlyphData glyphData = ComponentUtil.GLYPH_DATA.get(codePoint);

            if (style.isBold()) {
                width.add(glyphData.boldWidth());
            } else {
                width.add(glyphData.normalWidth());
            }

            return true;
        });

        return width.floatValue();
    }

    /**
     * Returns the provided adventure component centered based on {@link #DEFAULT_CHAT_BOX_WIDTH}
     *
     * @param component the component to center
     * @return the centered component
     */
    public static @NotNull Component centerAdventure(@NotNull Component component) {
        net.minecraft.network.chat.Component componentVanilla = PaperAdventure.asVanilla(component);
        net.minecraft.network.chat.Component resultVanilla = ComponentUtil.centerVanilla(componentVanilla);

        return PaperAdventure.asAdventure(resultVanilla);
    }

    /**
     * Returns the provided vanilla component centered based on {@link #DEFAULT_CHAT_BOX_WIDTH}
     *
     * @param component the component to center
     * @return the centered component
     */
    public static @NotNull net.minecraft.network.chat.Component centerVanilla(@NotNull net.minecraft.network.chat.Component component) {
        return new ComponentCenterer().apply(component);
    }

    public static @NotNull Component fillAdventure(
            @NotNull String characters,
            @Nullable ComponentStyleFillProcessor<Style> processor
    ) {
        return ComponentUtil.<Component, Style>fill(
                characters,
                processor,
                Component::empty,
                Component::text,
                ComponentUtil::widthAdventure,
                Component::style,
                Component::append
        );
    }

    public static @NotNull net.minecraft.network.chat.Component fillVanilla(
            @NotNull String characters,
            @Nullable ComponentStyleFillProcessor<net.minecraft.network.chat.Style> processor
    ) {
        return ComponentUtil.fill(
                characters,
                processor,
                net.minecraft.network.chat.Component::empty,
                net.minecraft.network.chat.Component::literal,
                ComponentUtil::widthVanilla,
                MutableComponent::setStyle,
                MutableComponent::append
        );
    }

    private static <T, U> @NotNull T fill(
            @NotNull String characters,
            @Nullable ComponentStyleFillProcessor<U> processor,
            @NotNull Supplier<T> emptyComponentSupplier,
            @NotNull Function<String, T> componentFromString,
            @NotNull ComponentWidthFunction<T> componentToWidth,
            @NotNull BiFunction<T, U, T> styleApplier,
            @NotNull BiFunction<T, T, T> componentAppender
    ) {
        Preconditions.checkArgument(!characters.isEmpty(), "There must be at least one character");

        char[] chars = characters.toCharArray();
        List<T> components = new ObjectArrayList<>();

        float currentWidth = 0.0F;
        int index = 0;

        while (currentWidth < ComponentUtil.DEFAULT_CHAT_BOX_WIDTH) {
            char value = chars[index++ % chars.length];
            T component = componentFromString.apply(Character.toString(value));

            if (processor != null) {
                U style = processor.process(value, index - 1);

                if (style != null) {
                    component = styleApplier.apply(component, style);
                }
            }

            currentWidth += componentToWidth.apply(component);

            components.add(component);
        }

        T empty = emptyComponentSupplier.get();
        T result = empty;

        for (int i = components.size() - 1; i >= 0; i--) {
            if (result == empty) {
                result = components.get(i);
            } else {
                result = componentAppender.apply(components.get(i), result);
            }
        }

        return result;
    }

    public static @NotNull Component addBackgroundAdventure(@NotNull Component component) {
        net.minecraft.network.chat.Component componentVanilla = PaperAdventure.asVanilla(component);
        net.minecraft.network.chat.Component resultVanilla = ComponentUtil.addBackgroundVanilla(componentVanilla);

        return PaperAdventure.asAdventure(resultVanilla);
    }

    public static @NotNull net.minecraft.network.chat.Component addBackgroundVanilla(@NotNull net.minecraft.network.chat.Component component) {
        int width = Mth.ceil(ComponentUtil.widthVanilla(component));
        int backgroundPadding = -width - 2;
        int backgroundWidth = width + 4 - 1; // Remove the extra pixel from the size output

        StringBuilder builder = new StringBuilder();
        builder.append(ComponentGlyphs.BACKGROUND_EDGE);

        while (backgroundWidth > 0) {
            for (Int2ObjectMap.Entry<String> entry : ComponentGlyphs.BACKGROUND.int2ObjectEntrySet()) {
                if (backgroundWidth >= entry.getIntKey()) {
                    builder.append(entry.getValue());
                    backgroundWidth -= entry.getIntKey();
                }
            }
        }

        builder.append(ComponentGlyphs.BACKGROUND_EDGE);

        outerLoop:
        while (backgroundPadding < 0) {
            for (Int2ObjectMap.Entry<String> entry : ComponentGlyphs.SPACE.int2ObjectEntrySet()) {
                if (backgroundPadding <= entry.getIntKey()) {
                    builder.append(entry.getValue());
                    backgroundPadding -= entry.getIntKey();
                    continue outerLoop;
                }
            }
        }

        return net.minecraft.network.chat.Component.literal(builder.toString()).withStyle(ComponentUtil.BACKGROUND_STYLE)
                .append(ComponentUtil.FOREGROUND_PREFIX_COMPONENT.copy().append(component))
                .append("\uE612\uE611");
    }

    private static void load(byte @NotNull [] data) throws IOException {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.copiedBuffer(data));

        // "Missing" data
        float boldWidth = ComponentUtil.readWidth(byteBuf);
        float normalWidth = ComponentUtil.readWidth(byteBuf);

        ComponentUtil.GLYPH_DATA.defaultReturnValue(new GlyphData(boldWidth, normalWidth));

        while (byteBuf.readableBytes() > 0) {
            boldWidth = ComponentUtil.readWidth(byteBuf);
            normalWidth = ComponentUtil.readWidth(byteBuf);
            GlyphData glyphData = new GlyphData(boldWidth, normalWidth);
            int codePoints = byteBuf.readVarInt();

            for (int i = 0; i < codePoints; i++) {
                ComponentUtil.GLYPH_DATA.put(byteBuf.readVarInt(), glyphData);
            }
        }
    }

    private static float readWidth(@NotNull FriendlyByteBuf byteBuf) {
        byte whole = byteBuf.readByte();
        byte decimal = byteBuf.readByte();

        return whole + (decimal / 10.0F);
    }
}
