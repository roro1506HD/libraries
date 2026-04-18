package ovh.roro.libraries.inventory.api.context;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.instance.ConfirmationInventoryInstance;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface ConfirmationContext<T, U extends InventoryPlayerHolder> {

    Consumer<U> confirmationCallback();

    ConfirmationInventoryInstance<T, U> inventoryInstance();

    T inventoryValue();

}
