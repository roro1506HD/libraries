package ovh.roro.libraries.inventory.impl.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.event.item.click.ItemLeftClickHandler;
import ovh.roro.libraries.inventory.api.event.item.click.ItemRightClickHandler;
import ovh.roro.libraries.inventory.api.event.item.drop.ItemDropHandler;
import ovh.roro.libraries.inventory.api.event.item.drop.ItemInventoryDropHandler;
import ovh.roro.libraries.inventory.api.event.item.interact.ItemInteractLeftClickHandler;
import ovh.roro.libraries.inventory.api.event.item.interact.ItemInteractRightClickHandler;
import ovh.roro.libraries.inventory.api.instance.ItemInstance;
import ovh.roro.libraries.inventory.api.item.Item;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;

import java.util.Objects;
import java.util.Optional;

@ApiStatus.Internal
public class ItemImpl<T, U extends InventoryPlayerHolder> implements Item<T, U> {

    private final InventoryManager inventoryManager;

    private final ItemInstance<T, U> itemInstance;
    private final int id;

    private final @Nullable ItemDropHandler<U> dropHandler;
    private final @Nullable ItemInventoryDropHandler<T, U> inventoryDropHandler;

    private final @Nullable ItemLeftClickHandler<T, U> leftClickHandler;
    private final @Nullable ItemRightClickHandler<T, U> rightClickHandler;

    private final @Nullable ItemInteractLeftClickHandler<U> interactLeftClickHandler;
    private final @Nullable ItemInteractRightClickHandler<U> interactRightClickHandler;

    @SuppressWarnings("unchecked")
    public ItemImpl(InventoryManager inventoryManager, ItemInstance<T, U> itemInstance, int id) {
        this.inventoryManager = inventoryManager;

        this.itemInstance = itemInstance;
        this.id = id;

        this.dropHandler = this.getOptionalHandler(ItemDropHandler.class);
        this.inventoryDropHandler = this.getOptionalHandler(ItemInventoryDropHandler.class);

        this.leftClickHandler = this.getOptionalHandler(ItemLeftClickHandler.class);
        this.rightClickHandler = this.getOptionalHandler(ItemRightClickHandler.class);

        this.interactLeftClickHandler = this.getOptionalHandler(ItemInteractLeftClickHandler.class);
        this.interactRightClickHandler = this.getOptionalHandler(ItemInteractRightClickHandler.class);
    }

    private <V> @Nullable V getOptionalHandler(Class<V> clazz) {
        if (clazz.isInstance(this.itemInstance)) {
            return clazz.cast(this.itemInstance);
        }

        return null;
    }

    public ItemBuilder buildItem(U player, @Nullable T value) {
        return this.itemInstance.buildItem(player, value);
    }

    @Override
    public ItemInstance<T, U> instance() {
        return this.itemInstance;
    }

    @Override
    public ItemStack asBukkitItem(U player, @Nullable T value) {
        return CraftItemStack.asCraftMirror(this.asMinecraftItem(player, value));
    }

    @Override
    public net.minecraft.world.item.ItemStack asMinecraftItem(U player, @Nullable T value) {
        return this.inventoryManager.toMinecraftStack(this, player, value);
    }

    @Override
    public boolean isSimilar(ItemStack itemStack) {
        return this.isSimilar(CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public boolean isSimilar(net.minecraft.world.item.ItemStack itemStack) {
        if (!itemStack.has(DataComponents.CUSTOM_DATA)) {
            return false;
        }

        CompoundTag tag = Objects.requireNonNull(itemStack.get(DataComponents.CUSTOM_DATA)).copyTag();

        return tag.getInt("inventory_api_item").filter(id -> id == this.id).isPresent();
    }

    public int id() {
        return this.id;
    }

    public Optional<ItemDropHandler<U>> dropHandler() {
        return Optional.ofNullable(this.dropHandler);
    }

    public Optional<ItemInventoryDropHandler<T, U>> inventoryDropHandler() {
        return Optional.ofNullable(this.inventoryDropHandler);
    }

    public Optional<ItemLeftClickHandler<T, U>> leftClickHandler() {
        return Optional.ofNullable(this.leftClickHandler);
    }

    public Optional<ItemRightClickHandler<T, U>> rightClickHandler() {
        return Optional.ofNullable(this.rightClickHandler);
    }

    public Optional<ItemInteractLeftClickHandler<U>> interactLeftClickHandler() {
        return Optional.ofNullable(this.interactLeftClickHandler);
    }

    public Optional<ItemInteractRightClickHandler<U>> interactRightClickHandler() {
        return Optional.ofNullable(this.interactRightClickHandler);
    }
}
