package ovh.roro.libraries.inventory.api.event.item.drop;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface ItemInventoryDropHandler<T, U extends InventoryPlayerHolder> {

    void onDrop(U player, @Nullable T value);

}
