package ovh.roro.libraries.inventory.impl.context;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.context.ConfirmationContext;
import ovh.roro.libraries.inventory.api.instance.ConfirmationInventoryInstance;

import java.util.function.Consumer;

@ApiStatus.Internal
public record ConfirmationContextImpl<T, U extends InventoryPlayerHolder>(
        ConfirmationInventoryInstance<T, U> inventoryInstance,
        T inventoryValue
) implements ConfirmationContext<T, U> {

    @Override
    public Consumer<U> confirmationCallback() {
        return player -> this.inventoryInstance.handleConfirmation(player, this.inventoryValue);
    }
}
