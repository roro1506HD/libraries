package ovh.roro.libraries.inventory.impl.classic;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.ClassicInventory;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.instance.ClassicInventoryInstance;
import ovh.roro.libraries.inventory.api.slot.SlotType;
import ovh.roro.libraries.inventory.impl.InventoryImpl;
import ovh.roro.libraries.inventory.impl.content.InventoryContentImpl;
import ovh.roro.libraries.language.api.Translation;

import java.util.List;

@ApiStatus.Internal
public class ClassicInventoryImpl<T, U extends InventoryPlayerHolder> extends InventoryImpl<T, ClassicInventoryInstance<T, U>, U> implements ClassicInventory<T, U> {

    private final InventoryManager inventoryManager;

    public ClassicInventoryImpl(InventoryManager inventoryManager, ClassicInventoryInstance<T, U> inventoryInstance) {
        super(inventoryInstance, inventory -> new InventoryContentImpl<>(inventoryManager, inventory));

        this.inventoryManager = inventoryManager;
    }

    @Override
    public ClassicInventoryInstance<T, U> instance() {
        return this.inventoryInstance;
    }

    @Override
    public List<U> viewers() {
        return this.inventoryManager.getInventoryViewers(this);
    }

    @Override
    public Translation title(U player, @Nullable T value) {
        return this.inventoryInstance.title(player, value);
    }

    @Override
    public int rows() {
        return this.inventoryInstance.rows();
    }

    @Override
    public int maxStackSize() {
        return this.inventoryInstance.maxStackSize();
    }

    @Override
    public SlotType slotType(int index) {
        return this.inventoryInstance.slotType(index);
    }

    @Override
    public void buildInventory() {
        this.inventoryInstance.buildInventory(this.inventoryContent);
    }

    @Override
    public void updateInventory(U player, @Nullable T value) {
    }

    @Override
    public void open(U player, @Nullable T value) {
        this.inventoryManager.openInventory(this, player, value);
    }
}
