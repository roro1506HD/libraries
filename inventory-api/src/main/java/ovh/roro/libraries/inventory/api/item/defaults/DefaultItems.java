package ovh.roro.libraries.inventory.api.item.defaults;

import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.item.Item;
import ovh.roro.libraries.inventory.api.item.StaticItem;

@ApiStatus.NonExtendable
public interface DefaultItems {

    static StaticItem separator(Material material) {
        return InventoryManager.inventoryManager().defaultItemFactory().separator(material);
    }

    static StaticItem separator(Key key) {
        return InventoryManager.inventoryManager().defaultItemFactory().separator(key);
    }

    static Item<Object, ?> back() {
        return InventoryManager.inventoryManager().defaultItemFactory().back();
    }
}
