package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that has a language.
 * This class can be implemented to facilitate abstraction and/or language holders
 */
public interface LanguagePlayerHolder {

    /**
     * Returns the language associated with this holder
     *
     * @return this holder's language
     */
    @NotNull Language language();

}
