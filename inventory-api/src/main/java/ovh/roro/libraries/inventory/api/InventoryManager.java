package ovh.roro.libraries.inventory.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.instance.ClassicInventoryInstance;
import ovh.roro.libraries.inventory.api.instance.ConfirmationInventoryInstance;
import ovh.roro.libraries.inventory.api.instance.InventoryInstance;
import ovh.roro.libraries.inventory.api.instance.ItemInstance;
import ovh.roro.libraries.inventory.api.instance.PageableInventoryInstance;
import ovh.roro.libraries.inventory.api.instance.StaticItemInstance;
import ovh.roro.libraries.inventory.api.item.Item;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;
import ovh.roro.libraries.inventory.api.item.StaticItem;
import ovh.roro.libraries.inventory.api.item.defaults.DefaultItemFactory;
import ovh.roro.libraries.inventory.impl.InventoryManagerImpl;
import ovh.roro.libraries.language.api.Language;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@ApiStatus.NonExtendable
@SuppressWarnings("rawtypes")
public interface InventoryManager {

    static InventoryManager inventoryManager() {
        return InventoryManagerImpl.LOADER.getOrCreate();
    }

    void register(Function<UUID, InventoryPlayerHolder> playerMapper);

    <T, U extends InventoryInstance<T, V>, V extends InventoryPlayerHolder> void openInventory(Inventory<T, U, V> inventory, V player, @Nullable T value);

    void openPreviousInventory(InventoryPlayerHolder player);

    void openPreviousInventory(InventoryPlayerHolder player, int inventoriesToSkip);

    boolean hasPreviousInventory(InventoryPlayerHolder player);

    void updateInventory(InventoryPlayerHolder player);

    void softCloseInventory(InventoryPlayerHolder player);

    boolean isRegisteredInventory(org.bukkit.inventory.Inventory inventory);

    <T extends InventoryPlayerHolder> List<T> getInventoryViewers(Inventory<?, ?, T> inventory);

    Optional<Item> parseItem(@Nullable ItemStack itemStack);

    Optional<Item> parseItem(net.minecraft.world.item.@Nullable ItemStack itemStack);

    <T, U extends InventoryPlayerHolder> ClassicInventory<T, U> createInventory(ClassicInventoryInstance<T, U> inventoryInstance);

    <T, U, V extends InventoryPlayerHolder> PageableInventory<T, U, V> createPageableInventory(PageableInventoryInstance<T, U, V> inventoryInstance);

    <T, U extends InventoryPlayerHolder> ConfirmationInventory<T, U> createConfirmationInventory(ConfirmationInventoryInstance<T, U> inventoryInstance);

    <T, U extends InventoryPlayerHolder> Item<T, U> createItem(ItemInstance<T, U> itemInstance);

    <T extends InventoryPlayerHolder> StaticItem createStaticItem(StaticItemInstance itemInstance);

    ItemBuilder createItemBuilder(Material material);

    ItemBuilder createItemBuilder(Material material, int amount);

    ItemBuilder fromLegacy(ItemStack itemStack);

    ItemBuilder fromLegacy(net.minecraft.world.item.ItemStack itemStack);

    <T, U extends InventoryPlayerHolder> net.minecraft.world.item.ItemStack toMinecraftStack(Item<T, U> item, U player, @Nullable T value);

    net.minecraft.world.item.ItemStack toMinecraftStack(ItemBuilder builder, Language language);

    <T, U extends InventoryPlayerHolder> ItemStack toBukkitStack(Item<T, U> item, U player, @Nullable T value);

    ItemStack toBukkitStack(ItemBuilder builder, Language language);

    DefaultItemFactory defaultItemFactory();

}
