package ovh.roro.libraries.inventory.api.event;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface InventoryOpenHandler<T, U extends InventoryPlayerHolder> {

    void onOpen(U player, @Nullable T value);

}
