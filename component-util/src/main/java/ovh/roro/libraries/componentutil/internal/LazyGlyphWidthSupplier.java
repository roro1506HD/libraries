package ovh.roro.libraries.componentutil.internal;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.componentutil.ComponentUtil;

@ApiStatus.Internal
public class LazyGlyphWidthSupplier {

    private final Component component;

    public LazyGlyphWidthSupplier(Component component) {
        this.component = component;
    }

    private boolean initialized;
    private float width;

    public float getWidth() {
        if (!this.initialized) {
            this.initialized = true;
            this.width = ComponentUtil.widthVanilla(this.component);
        }

        return this.width;
    }
}
