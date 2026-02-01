package ovh.roro.libraries.language.api;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that can be translated.
 * This is used internally for {@link Translation}, but can be implemented anywhere
 */
public interface Translatable {

    /**
     * Returns the translation key of this object
     *
     * @return the translation key
     */
    @NotNull String translationKey();

}
