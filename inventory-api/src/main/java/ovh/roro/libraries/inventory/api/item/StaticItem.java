package ovh.roro.libraries.inventory.api.item;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.instance.StaticItemInstance;

@ApiStatus.NonExtendable
public interface StaticItem extends Item<Object, InventoryPlayerHolder> {

    static StaticItem of(ItemBuilder itemBuilder) {
        return InventoryManager.inventoryManager().createStaticItem(() -> itemBuilder);
    }

    static StaticItem of(StaticItemInstance itemInstance) {
        return InventoryManager.inventoryManager().createStaticItem(itemInstance);
    }
}
