package ovh.roro.libraries.inventory.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.instance.ClassicInventoryInstance;

@ApiStatus.NonExtendable
public interface ClassicInventory<T, U extends InventoryPlayerHolder> extends Inventory<T, ClassicInventoryInstance<T, U>, U> {

    static <T, U extends InventoryPlayerHolder> ClassicInventory<T, U> of(ClassicInventoryInstance<T, U> inventoryInstance) {
        return InventoryManager.inventoryManager().createInventory(inventoryInstance);
    }

    void open(U player, @Nullable T value);

}
