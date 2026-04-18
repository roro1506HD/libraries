package ovh.roro.libraries.inventory.impl;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.event.InventoryCloseHandler;
import ovh.roro.libraries.inventory.api.event.InventoryOpenHandler;
import ovh.roro.libraries.inventory.api.instance.InventoryInstance;
import ovh.roro.libraries.inventory.api.slot.SlotType;
import ovh.roro.libraries.inventory.impl.content.InventoryContentImpl;
import ovh.roro.libraries.language.api.Translation;

import java.util.Optional;
import java.util.function.Function;

@ApiStatus.Internal
public abstract class InventoryImpl<T, U extends InventoryInstance<T, V>, V extends InventoryPlayerHolder> {

    protected final U inventoryInstance;
    protected final InventoryContentImpl<T, V> inventoryContent;

    protected final @Nullable InventoryOpenHandler<T, V> openHandler;
    protected final @Nullable InventoryCloseHandler<T, V> closeHandler;

    private boolean built;

    @SuppressWarnings("unchecked")
    public InventoryImpl(U inventoryInstance, Function<InventoryImpl<T, U, V>, InventoryContentImpl<T, V>> inventoryContentMapper) {
        this.inventoryInstance = inventoryInstance;
        this.inventoryContent = inventoryContentMapper.apply(this);

        this.openHandler = this.handler(InventoryOpenHandler.class);
        this.closeHandler = this.handler(InventoryCloseHandler.class);
    }

    @Nullable
    private <W> W handler(Class<W> clazz) {
        if (clazz.isInstance(this.inventoryInstance)) {
            return clazz.cast(this.inventoryInstance);
        }

        return null;
    }

    public void ensureIsBuilt() {
        if (!this.built) {
            this.built = true;

            this.buildInventory();
        }
    }

    public abstract Translation title(V player, @Nullable T value);

    public abstract int rows();

    public abstract int maxStackSize();

    public abstract SlotType slotType(int index);

    public abstract void buildInventory();

    public abstract void updateInventory(V player, @Nullable T value);

    public InventoryContentImpl<T, V> inventoryContent() {
        return this.inventoryContent;
    }

    public Optional<InventoryOpenHandler<T, V>> openHandler() {
        return Optional.ofNullable(this.openHandler);
    }

    public Optional<InventoryCloseHandler<T, V>> closeHandler() {
        return Optional.ofNullable(this.closeHandler);
    }
}
