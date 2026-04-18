package ovh.roro.libraries.language.api.data;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents the data of numbers of a language.
 * Languages may have different ways of writing large or decimal numbers, this handles it
 */
@ApiStatus.NonExtendable
public interface LanguageNumberData {

    /**
     * Represents the group separator of the language.
     * A group separator is a String that separates groups of 3 digits.
     * <p>
     * In the following number, the group separator is a space: 12 345 678
     *
     * @return the group separator
     */
    String groupSeparator();

    /**
     * Represents the decimal separator of the language.
     * A decimal separator is a String that separates the whole part from the decimal part.
     * <p>
     * In the following number, the decimal separator is a dot: 3.14
     *
     * @return the decimal separator
     */
    String decimalSeparator();

}
