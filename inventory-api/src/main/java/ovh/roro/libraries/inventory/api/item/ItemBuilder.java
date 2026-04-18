package ovh.roro.libraries.inventory.api.item;

import io.papermc.paper.datacomponent.DataComponentType;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.NonExtendable
public interface ItemBuilder {

    static ItemBuilder of(Material material) {
        return InventoryManager.inventoryManager().createItemBuilder(material);
    }

    static ItemBuilder fromLegacy(ItemStack itemStack) {
        return InventoryManager.inventoryManager().fromLegacy(itemStack);
    }

    Material material();

    @Contract("_ -> this")
    ItemBuilder damage(int damage);

    int damage();

    @Contract("_ -> this")
    ItemBuilder amount(int amount);

    int amount();

    @Contract("_ -> this")
    ItemBuilder name(@Nullable Translation translation);

    @Nullable Translation name();

    @Contract("_ -> this")
    ItemBuilder description(Translation @Nullable ... translations);

    @Nullable Translation[] description();

    @Contract("_, _ -> this")
    ItemBuilder enchant(Enchantment enchantment, int level);

    int enchant(Enchantment enchantment);

    @Contract("_ -> this")
    ItemBuilder removeEnchant(Enchantment enchantment);

    @Contract("_ -> this")
    ItemBuilder hideTooltip(boolean hide);

    @Contract("_ -> this")
    ItemBuilder hideComponents(DataComponentType... componentTypes);

    @Contract("_ -> this")
    ItemBuilder hideComponents(Key... componentKeys);

    @Contract(" -> this")
    ItemBuilder hideAllComponents();

    @Contract("_ -> this")
    ItemBuilder showComponents(DataComponentType... componentTypes);

    @Contract("_ -> this")
    ItemBuilder showComponents(Key... componentKeys);

    @Contract(" -> this")
    ItemBuilder showAllComponents();

    boolean isComponentHidden(DataComponentType componentType);

    boolean isComponentHidden(Key componentKey);

    @Contract("_ -> this")
    ItemBuilder unbreakable(boolean unbreakable);

    boolean unbreakable();

    @Contract("_ -> this")
    ItemBuilder glowing(boolean glowing);

    boolean glowing();

    @Contract("_ -> this")
    ItemBuilder skull(Player player);

    @Contract("_, _ -> this")
    ItemBuilder skull(String texture, String signature);

    @Contract("_ -> this")
    ItemBuilder overrideModel(@Nullable Key key);

    ItemBuilder clone();

}
