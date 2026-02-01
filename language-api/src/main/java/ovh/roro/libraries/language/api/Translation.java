package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import ovh.roro.libraries.language.impl.TranslationImpl;

/**
 * Represents a translation consisting of a translation key and placeholders
 * A translation can have placeholders, but has no obligation
 *
 * @see Placeholder
 */
@ApiStatus.NonExtendable
public interface Translation extends Translatable {

    /**
     * Creates a new translation from the provided translation key and the provided placeholders.
     * The translation key is the key that will be look for in lang files.
     * Placeholders are optional, but great for dynamic content in translations.
     *
     * @param translationKey the translation key
     * @param placeholders   the placeholders
     * @return a new translation
     */
    static @NotNull Translation translation(@NotNull String translationKey, @NotNull Placeholder... placeholders) {
        return new TranslationImpl(translationKey, placeholders);
    }

    /**
     * Creates a new translation from the provided translatable object and the provided placeholders.
     * The translatable object's translation key is the key that will be look for in lang files.
     * Placeholders are optional, but great for dynamic content in translations.
     *
     * @param translatable the translatable object
     * @param placeholders the placeholders
     * @return a new translation
     */
    static @NotNull Translation translation(@NotNull Translatable translatable, @NotNull Placeholder... placeholders) {
        return Translation.translation(translatable.translationKey(), placeholders);
    }

    /**
     * Returns this translation's translation key
     *
     * @return the translation key
     */
    @NotNull String translationKey();

    /**
     * Returns this translation's placeholders, if any.
     * If no placeholder is associated with this translation, an empty array is returned.
     *
     * @return the placeholders
     */
    @NotNull Placeholder @NotNull [] placeholders();

}
