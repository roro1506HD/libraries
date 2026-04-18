package ovh.roro.libraries.inventory.api.instance;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;

@ApiStatus.OverrideOnly
public interface StaticItemInstance extends ItemInstance<Object, InventoryPlayerHolder> {

    @Override
    default ItemBuilder buildItem(InventoryPlayerHolder player, @Nullable Object value) {
        return this.buildItem();
    }

    ItemBuilder buildItem();

}
