package ovh.roro.libraries.language.api;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.language.impl.PlaceholderImpl;

/**
 * A placeholder is a tag in translations that is replaced with a dynamic value.
 * This class is to provide the dynamic value
 * <p>
 * For example, consider the following translation:<br>
 * <code>&lt;yellow&gt;You currently have &lt;amount&gt; coins!</code><br>
 * It has two tags:
 * - <code>&lt;yellow&gt;</code>: A native MiniMessage tag that applies the yellow color
 * - <code>&lt;amount&gt;</code>: A placeholder tag that will be used to insert a dynamic value
 * <br><br>
 * The placeholder used to insert the amount of coins (assuming the coins amount is not a decimal) in the above example will be:<br>
 * <code>Placeholder.number("amount", 52)</code>
 */
@ApiStatus.NonExtendable
public interface Placeholder {

    /**
     * Creates a placeholder that will replace the provided code with the provided value.
     * The final value will be the result of a {@link java.util.Objects#toString(Object)} of the provided value
     *
     * @param code  the code to replace
     * @param value the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder string(String code, Object value) {
        return PlaceholderImpl.string(code, value);
    }

    /**
     * Creates a placeholder that will replace the provided code with the provided translation.
     * The final value will be the provided translation translated using the same language as the 'parent' translation
     *
     * @param code        the code to replace
     * @param translation the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder translation(String code, Translation translation) {
        return PlaceholderImpl.translation(code, translation);
    }

    /**
     * Creates a placeholder that will replace the provided code with the provided value.
     * The format of the date can be configured in the translation:<br>
     * <code>&lt;code:'format'&gt;</code><br>
     * In the above snippet, you have to replace <code>format</code> with a valid {@link java.time.format.DateTimeFormatter} format
     *
     * @param code the code to replace
     * @param time the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder date(String code, ZonedTime time) {
        return PlaceholderImpl.date(code, time);
    }

    /**
     * Creates a placeholder that will replace the provided code with the provided value.
     * The format of the number can be configured in the translation:<br>
     * <code>&lt;code:'format'&gt;</code><br>
     * In the above snippet, you have to replace <code>format</code> with a valid {@link java.text.DecimalFormat} format
     * <p>
     * <b>NOTE:</b> The grouping separator and the decimal separator are defined in the lang JSON
     *
     * @param code the code to replace
     * @param d    the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder decimal(String code, double d) {
        return PlaceholderImpl.decimal(code, d);
    }

    /**
     * Creates a placeholder that will replace the provided code with the provided value.
     * The format of the number can be configured in the translation:<br>
     * <code>&lt;code:'format'&gt;</code><br>
     * In the above snippet, you have to replace <code>format</code> with a valid {@link java.text.DecimalFormat} format
     * <p>
     * <b>NOTE:</b> The grouping separator and the decimal separator are defined in the lang JSON
     *
     * @param code the code to replace
     * @param l    the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder number(String code, long l) {
        return PlaceholderImpl.number(code, l);
    }

    /**
     * Creates a placeholder that will replace the provided code with the provided value
     *
     * @param code      the code to replace
     * @param component the value to replace the code with
     * @return a new Placeholder
     */
    static Placeholder translated(String code, Component component) {
        return PlaceholderImpl.translated(code, component);
    }

    /**
     * Returns the code this placeholder will replace
     *
     * @return the code of this placeholder
     */
    String code();

    /**
     * Returns the raw value of this placeholder, untranslated {@link #translation(String, Translation)}
     * and any raw value passed as input in any of the static methods
     *
     * @return the value of this placeholder
     */
    Object value();

}
