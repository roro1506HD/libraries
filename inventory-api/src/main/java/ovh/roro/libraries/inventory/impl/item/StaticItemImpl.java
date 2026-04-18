package ovh.roro.libraries.inventory.impl.item;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.instance.StaticItemInstance;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;
import ovh.roro.libraries.inventory.api.item.StaticItem;

@ApiStatus.Internal
public class StaticItemImpl extends ItemImpl<Object, InventoryPlayerHolder> implements StaticItem {

    private final ItemBuilder cachedBuilder;

    @SuppressWarnings("ConstantConditions")
    public StaticItemImpl(InventoryManager inventoryManager, StaticItemInstance itemInstance, int id) {
        super(inventoryManager, itemInstance, id);

        this.cachedBuilder = itemInstance.buildItem(null, null);
    }

    @Override
    public ItemBuilder buildItem(InventoryPlayerHolder player, @Nullable Object value) {
        return this.cachedBuilder;
    }
}
