package ovh.roro.libraries.componentutil;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import org.jetbrains.annotations.NotNull;

/**
 * Class containing some useful glyphs for components.
 * These glyphs only works if players have the resource pack
 */
public final class ComponentGlyphs {

    /**
     * Glyph used for background edges.
     * Background edges are 1px wide and not full height
     */
    public static final @NotNull String BACKGROUND_EDGE = "\uE500\uE601";

    /**
     * Glyphs used for background depending on the needed width.
     * The key represents the width and the value is the glyph
     */
    public static final @NotNull Int2ObjectMap<String> BACKGROUND = new Int2ObjectLinkedOpenHashMap<>() {
        {
            this.put(128, "\uE508\uE601");
            this.put(64, "\uE507\uE601");
            this.put(32, "\uE506\uE601");
            this.put(16, "\uE505\uE601");
            this.put(8, "\uE504\uE601");
            this.put(4, "\uE503\uE601");
            this.put(2, "\uE502\uE601");
            this.put(1, "\uE501\uE601");
        }
    };

    /**
     * Glyphs used for space padding. Keys represent the width and value is the glyph
     */
    public static final @NotNull Int2ObjectSortedMap<String> SPACE = new Int2ObjectLinkedOpenHashMap<>() {
        {
            this.put(-128, "\uE608");
            this.put(-64, "\uE607");
            this.put(-32, "\uE606");
            this.put(-16, "\uE605");
            this.put(-8, "\uE604");
            this.put(-4, "\uE603");
            this.put(-2, "\uE602");
            this.put(-1, "\uE601");
            this.put(128, "\uE618");
            this.put(64, "\uE617");
            this.put(32, "\uE616");
            this.put(16, "\uE615");
            this.put(8, "\uE614");
            this.put(4, "\uE613");
            this.put(2, "\uE612");
            this.put(1, "\uE611");
        }
    };

    /**
     * @hidden
     */
    private ComponentGlyphs() {
        throw new UnsupportedOperationException();
    }
}
