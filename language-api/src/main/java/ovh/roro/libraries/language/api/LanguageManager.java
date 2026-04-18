package ovh.roro.libraries.language.api;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.language.impl.LanguageManagerImpl;

import java.nio.file.Path;
import java.util.List;

@ApiStatus.NonExtendable
public interface LanguageManager {

    static LanguageManager languageManager() {
        return LanguageManagerImpl.LOADER.getOrCreate();
    }

    /**
     * Loads all languages in the provided language folder. If that folder does not yet exist, it is being created
     * and filled with the default files.
     *
     * @param languageFolder The folder to scan languages from
     * @param plugin         The plugin to which default files are loaded with
     * @param defaultFiles   The files to copy when the language folder does not exist
     */
    void load(Path languageFolder, JavaPlugin plugin, String[] defaultFiles);

    /**
     * Loads all languages in the provided language folder, if that folder does not exist, no languages are loaded.
     *
     * @param languageFolder The folder to scan languages from
     */
    void load(Path languageFolder);

    /**
     * Retrieves a language from its alpha code
     *
     * @param alpha the alpha code of the language to find
     * @return the language associated with the alpha, if found, otherwise {@code null}
     */
    @Nullable Language language(String alpha);

    /**
     * Retrieves a language from its numeral id
     *
     * @param languageId the numeral id of the language to find
     * @return the language associated with the id, if found, otherwise {@code null}
     */
    @Nullable Language language(int languageId);

    /**
     * Returns all loaded languages
     *
     * @return the loaded languages
     */
    List<Language> languages();

    /**
     * Translates a translation key using placeholders for the specified language
     *
     * @param language       the language to translate to
     * @param translationKey the translation key to translate
     * @param placeholders   the placeholders to use
     * @return the translated component
     */
    Component translate(Language language, String translationKey, Placeholder... placeholders);

    /**
     * Translates a translatable object using placeholders for the specified language
     *
     * @param language     the language to translate to
     * @param translatable the translatable object to translate
     * @param placeholders the placeholders to use
     * @return the translated component
     */
    Component translate(Language language, Translatable translatable, Placeholder... placeholders);

    /**
     * Translates a translation for the specified language
     *
     * @param language    the language to translate to
     * @param translation the translation to translate
     * @return the translated component
     */
    Component translate(Language language, Translation translation);

}
