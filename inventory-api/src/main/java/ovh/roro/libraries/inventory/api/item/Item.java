package ovh.roro.libraries.inventory.api.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.instance.ItemInstance;

@ApiStatus.NonExtendable
public interface Item<T, U extends InventoryPlayerHolder> {

    static <T, U extends InventoryPlayerHolder> Item<T, U> of(ItemInstance<T, U> itemInstance) {
        return InventoryManager.inventoryManager().createItem(itemInstance);
    }

    ItemInstance<T, U> instance();

    ItemStack asBukkitItem(U player, @Nullable T value);

    net.minecraft.world.item.ItemStack asMinecraftItem(U player, @Nullable T value);

    boolean isSimilar(ItemStack itemStack);

    boolean isSimilar(net.minecraft.world.item.ItemStack itemStack);

}
