package ovh.roro.libraries.language.api;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ovh.roro.libraries.language.impl.LanguageManagerImpl;

import java.nio.file.Path;
import java.util.List;

@ApiStatus.NonExtendable
public interface LanguageManager {

    static @NotNull LanguageManager languageManager() {
        return LanguageManagerImpl.LOADER.getOrCreate();
    }

    /**
     * Loads all languages in the provided language folder. If that folder does not yet exist, it is being created
     * and filled with the default files.
     *
     * @param languageFolder The folder to scan languages from
     * @param plugin The plugin to which default files are loaded with
     * @param defaultFiles The files to copy when the language folder does not exist
     */
    void load(@NotNull Path languageFolder, @NotNull JavaPlugin plugin, @NotNull String @NotNull [] defaultFiles);

    /**
     * Loads all languages in the provided language folder, if that folder does not exist, no languages are loaded.
     *
     * @param languageFolder The folder to scan languages from
     */
    void load(@NotNull Path languageFolder);

    @Nullable Language language(@NotNull String alpha);

    @Nullable Language language(int languageId);

    @NotNull List<Language> languages();

    @NotNull Component translate(@NotNull Language language, @NotNull String translationKey, @NotNull Placeholder... placeholders);

    @NotNull Component translate(@NotNull Language language, @NotNull Translation translation);

}
