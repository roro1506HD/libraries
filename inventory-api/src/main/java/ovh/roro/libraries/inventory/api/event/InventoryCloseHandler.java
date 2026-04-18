package ovh.roro.libraries.inventory.api.event;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface InventoryCloseHandler<T, U extends InventoryPlayerHolder> {

    void onClose(U player, @Nullable T value);

}
