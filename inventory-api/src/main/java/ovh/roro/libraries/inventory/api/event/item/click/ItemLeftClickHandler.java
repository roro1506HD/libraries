package ovh.roro.libraries.inventory.api.event.item.click;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface ItemLeftClickHandler<T, U extends InventoryPlayerHolder> {

    void onLeftClick(U player, boolean isShiftClick, @Nullable T value);

}
