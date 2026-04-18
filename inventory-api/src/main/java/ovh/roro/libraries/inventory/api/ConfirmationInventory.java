package ovh.roro.libraries.inventory.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.context.ConfirmationContext;
import ovh.roro.libraries.inventory.api.instance.ConfirmationInventoryInstance;

@ApiStatus.NonExtendable
public interface ConfirmationInventory<T, U extends InventoryPlayerHolder> extends Inventory<ConfirmationContext<T, U>, ConfirmationInventoryInstance<T, U>, U> {

    static <T, U extends InventoryPlayerHolder> ConfirmationInventory<T, U> of(ConfirmationInventoryInstance<T, U> inventoryInstance) {
        return InventoryManager.inventoryManager().createConfirmationInventory(inventoryInstance);
    }

    void openConfirmation(U player, T value);

}
