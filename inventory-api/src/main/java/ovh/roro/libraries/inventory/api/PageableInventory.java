package ovh.roro.libraries.inventory.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.context.PaginationContext;
import ovh.roro.libraries.inventory.api.instance.PageableInventoryInstance;

@ApiStatus.NonExtendable
public interface PageableInventory<T, U, V extends InventoryPlayerHolder> extends Inventory<PaginationContext<T, U, V>, PageableInventoryInstance<T, U, V>, V> {

    static <T, U, V extends InventoryPlayerHolder> PageableInventory<T, U, V> of(PageableInventoryInstance<T, U, V> inventoryInstance) {
        return InventoryManager.inventoryManager().createPageableInventory(inventoryInstance);
    }

    void openPageable(V player, @Nullable T value);

    void openPageable(V player, @Nullable T value, int page);

}
