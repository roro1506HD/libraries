package ovh.roro.libraries.componentutil;

import org.jetbrains.annotations.Nullable;

/**
 * A style processor specifically used in {@link ComponentUtil#fillAdventure(String, ComponentStyleFillProcessor)}
 * and {@link ComponentUtil#fillVanilla(String, ComponentStyleFillProcessor)}.
 * <p>
 * This processor allows customizing the style used for each character
 *
 * @param <T> the style to be returned by this processor
 */
@FunctionalInterface
public interface ComponentStyleFillProcessor<T> {

    /**
     * Called by {@link ComponentUtil#fillAdventure(String, ComponentStyleFillProcessor)} and
     * {@link ComponentUtil#fillVanilla(String, ComponentStyleFillProcessor)} for each character
     *
     * @param value the character that's being processed
     * @param index the index the character is at
     * @return the style to apply to the character
     */
    @Nullable T process(char value, int index);

}
