package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ovh.roro.libraries.language.api.data.LanguageNumberData;

/**
 * Represents language properties
 */
@ApiStatus.NonExtendable
public interface Language {

    /**
     * Returns the unique numeral id of this language
     *
     * @return a unique numeral id
     */
    int id();

    /**
     * Returns the name of this language
     *
     * @return the name
     */
    @NotNull String name();

    /**
     * Returns the alpha of this language, a short and unique string that represents this language
     *
     * @return the alpha
     */
    @NotNull String alpha();

    /**
     * Returns the fallback language. When a translation is missing from this language, the fallback language will be used instead.
     * If the fallback language also misses the language, it will look into the fallback language's fallback language and so on.
     * <p>
     * If a translation cannot be resolved, the translation key is returned as-is
     *
     * @return the fallback language, if any
     */
    @Nullable Language fallbackLanguage();

    /**
     * Returns the texture value of a player head associated with this language, to be shown in an inventory
     *
     * @return the texture value
     */
    @NotNull String headTexture();

    /**
     * Returns the number data of this language, including group separator and decimal separator.
     *
     * @return the number data
     */
    @NotNull LanguageNumberData numberData();

}
