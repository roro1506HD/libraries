package ovh.roro.libraries.inventory.api.event.item.drop;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface ItemDropHandler<U extends InventoryPlayerHolder> {

    void onDrop(U player);

}
