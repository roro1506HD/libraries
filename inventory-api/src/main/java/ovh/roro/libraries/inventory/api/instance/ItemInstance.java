package ovh.roro.libraries.inventory.api.instance;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;

@ApiStatus.OverrideOnly
public interface ItemInstance<T, U extends InventoryPlayerHolder> {

    ItemBuilder buildItem(U player, @Nullable T value);

}
