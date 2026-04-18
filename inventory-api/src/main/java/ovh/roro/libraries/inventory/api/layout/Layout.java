package ovh.roro.libraries.inventory.api.layout;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.impl.layout.CornersLayoutImpl;
import ovh.roro.libraries.inventory.impl.layout.OutlineLayoutImpl;

@ApiStatus.OverrideOnly
public interface Layout {

    Layout CORNERS = CornersLayoutImpl.INSTANCE;
    Layout OUTLINE = OutlineLayoutImpl.INSTANCE;

    int[] slots(int inventorySize);

}
