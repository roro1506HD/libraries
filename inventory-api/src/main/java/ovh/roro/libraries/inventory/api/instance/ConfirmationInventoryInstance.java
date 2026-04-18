package ovh.roro.libraries.inventory.api.instance;

import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.context.ConfirmationContext;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;

@ApiStatus.OverrideOnly
public interface ConfirmationInventoryInstance<T, U extends InventoryPlayerHolder> extends InventoryInstance<ConfirmationContext<T, U>, U> {

    ItemBuilder buildPreviewItem(U player, T value);

    Material layoutMaterial();

    void handleConfirmation(U player, T value);

}
