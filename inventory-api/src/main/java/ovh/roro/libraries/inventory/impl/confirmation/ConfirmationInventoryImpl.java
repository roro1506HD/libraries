package ovh.roro.libraries.inventory.impl.confirmation;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.ConfirmationInventory;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.context.ConfirmationContext;
import ovh.roro.libraries.inventory.api.instance.ConfirmationInventoryInstance;
import ovh.roro.libraries.inventory.api.layout.Layout;
import ovh.roro.libraries.inventory.api.slot.SlotType;
import ovh.roro.libraries.inventory.impl.InventoryImpl;
import ovh.roro.libraries.inventory.impl.confirmation.item.CancelItem;
import ovh.roro.libraries.inventory.impl.confirmation.item.ConfirmItem;
import ovh.roro.libraries.inventory.impl.confirmation.item.PreviewItem;
import ovh.roro.libraries.inventory.impl.content.InventoryContentImpl;
import ovh.roro.libraries.inventory.impl.context.ConfirmationContextImpl;
import ovh.roro.libraries.language.api.Translation;

import java.util.List;

@ApiStatus.Internal
public class ConfirmationInventoryImpl<T, U extends InventoryPlayerHolder> extends InventoryImpl<ConfirmationContext<T, U>, ConfirmationInventoryInstance<T, U>, U> implements ConfirmationInventory<T, U> {

    private final InventoryManager inventoryManager;

    public ConfirmationInventoryImpl(InventoryManager inventoryManager, ConfirmationInventoryInstance<T, U> inventoryInstance) {
        super(inventoryInstance, inventory -> new InventoryContentImpl<>(inventoryManager, inventory));

        this.inventoryManager = inventoryManager;
    }

    @Override
    public ConfirmationInventoryInstance<T, U> instance() {
        return this.inventoryInstance;
    }

    @Override
    public List<U> viewers() {
        return this.inventoryManager.getInventoryViewers(this);
    }

    @Override
    public Translation title(U player, @Nullable ConfirmationContext<T, U> value) {
        return Translation.translation("inventory.api.inventory.confirmation.title");
    }

    @Override
    public int rows() {
        return 5;
    }

    @Override
    public int maxStackSize() {
        return 64;
    }

    @Override
    public SlotType slotType(int index) {
        return SlotType.DYNAMIC;
    }

    @Override
    public void buildInventory() {
        this.inventoryContent.item(3, 3, new ConfirmItem(this.inventoryManager));
        this.inventoryContent.item(5, 3, new CancelItem(this.inventoryManager));
        this.inventoryContent.item(4, 1, new PreviewItem());

        this.inventoryContent.layout(Layout.OUTLINE, this.inventoryManager.defaultItemFactory().separator(this.inventoryInstance.layoutMaterial()));
    }

    @Override
    public void updateInventory(U player, @Nullable ConfirmationContext<T, U> value) {
    }

    @Override
    public void openConfirmation(U player, T value) {
        this.inventoryManager.openInventory(this, player, new ConfirmationContextImpl<>(this.inventoryInstance, value));
    }
}
