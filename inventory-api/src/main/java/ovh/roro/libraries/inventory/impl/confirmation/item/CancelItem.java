package ovh.roro.libraries.inventory.impl.confirmation.item;

import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.context.ConfirmationContext;
import ovh.roro.libraries.inventory.api.event.item.click.ItemClickHandler;
import ovh.roro.libraries.inventory.api.instance.ItemInstance;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.Internal
@SuppressWarnings("rawtypes")
public class CancelItem implements ItemInstance<ConfirmationContext, InventoryPlayerHolder>, ItemClickHandler<ConfirmationContext, InventoryPlayerHolder> {

    private final InventoryManager inventoryManager;

    public CancelItem(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public ItemBuilder buildItem(InventoryPlayerHolder player, @Nullable ConfirmationContext value) {
        return this.inventoryManager.createItemBuilder(Material.BARRIER)
                .name(Translation.translation("inventory.api.item.confirmation.cancel.name"));
    }

    @Override
    public void onClick(InventoryPlayerHolder player, boolean isShiftClick, @Nullable ConfirmationContext value) {
        this.inventoryManager.openPreviousInventory(player);
    }
}
