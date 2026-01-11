package ovh.roro.libraries.componentutil;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the width data of a glyph
 *
 * @param normalWidth the normal width of the glyph
 * @param boldWidth   the bold width of the glyph
 */
public record GlyphData(
        float normalWidth,
        float boldWidth
) {

    /**
     * Represents the default bold offset of glyphs
     */
    public static final float DEFAULT_BOLD_OFFSET = 1.0F;

    /**
     * Returns a glyph data for the specified width with the default bold offset of {@code 1.0F}
     *
     * @param normalWidth the normal width of the glyph
     * @return the width data
     */
    public static @NotNull GlyphData withDefaultBoldOffset(float normalWidth) {
        return new GlyphData(normalWidth, normalWidth + GlyphData.DEFAULT_BOLD_OFFSET);
    }
}
