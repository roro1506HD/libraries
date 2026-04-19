package ovh.roro.libraries.inventory.api.item;

import io.papermc.paper.datacomponent.item.PotDecorations;
import net.kyori.adventure.key.Key;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryManager;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.item.component.DataComponent;
import ovh.roro.libraries.language.api.Translation;

import java.util.Collection;
import java.util.List;

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
    ItemBuilder maxDamage(int maxDamage);

    int maxDamage();

    @Contract("_ -> this")
    ItemBuilder amount(int amount);

    int amount();

    @Contract("_ -> this")
    ItemBuilder maxStackSize(int maxStackSize);

    int maxStackSize();

    @Contract("_ -> this")
    ItemBuilder name(@Nullable Translation translation);

    @Nullable Translation name();

    @Contract("_ -> this")
    ItemBuilder description(Translation... translations);

    @Contract("_ -> this")
    ItemBuilder description(@Nullable List<Translation> translations);

    @Nullable List<Translation> description();

    @Contract("_, _ -> this")
    ItemBuilder addEnchant(Enchantment enchantment, int level);

    @Contract("_ -> this")
    ItemBuilder removeEnchant(Enchantment enchantment);

    int enchantLevel(Enchantment enchantment);

    boolean hasEnchant(Enchantment enchantment);

    @Contract("_ -> this")
    ItemBuilder hideTooltip(boolean hide);

    @Contract("_ -> this")
    ItemBuilder hideComponents(DataComponent... components);

    @Contract("_ -> this")
    ItemBuilder hideComponents(Key... componentKeys);

    @Contract("_ -> this")
    ItemBuilder hideComponents(Collection<?> components);

    @Contract(" -> this")
    ItemBuilder hideAllComponents();

    @Contract("_ -> this")
    ItemBuilder showComponents(DataComponent... components);

    @Contract("_ -> this")
    ItemBuilder showComponents(Key... componentKeys);

    @Contract("_ -> this")
    ItemBuilder showComponents(Collection<?> components);

    @Contract(" -> this")
    ItemBuilder showAllComponents();

    boolean isComponentHidden(DataComponent component);

    boolean isComponentHidden(Key componentKey);

    @Contract("_ -> this")
    ItemBuilder unbreakable(boolean unbreakable);

    boolean unbreakable();

    @Contract("_ -> this")
    ItemBuilder glowing(boolean glowing);

    @Contract(" -> this")
    ItemBuilder resetGlowing();

    boolean glowing();

    @Contract("_ -> this")
    ItemBuilder color(Material... dyes);

    @Contract("_ -> this")
    ItemBuilder color(DyeColor... dyes);

    @Contract("_ -> this")
    ItemBuilder color(Collection<?> dyes);

    @Contract("_ -> this")
    ItemBuilder mixColor(Material... dyes);

    @Contract("_ -> this")
    ItemBuilder mixColor(DyeColor... dyes);

    @Contract("_ -> this")
    ItemBuilder mixColor(Collection<?> dyes);

    @Contract("_ -> this")
    ItemBuilder color(int rgb);

    @Contract(" -> this")
    ItemBuilder removeColor();

    @Contract(" -> this")
    ItemBuilder resetColor();

    int color();

    boolean hasColor();

    @Contract("_ -> this")
    ItemBuilder bundleContents(ItemStack... contents);

    @Contract("_ -> this")
    ItemBuilder bundleContents(@Nullable List<ItemStack> contents);

    @Nullable List<ItemStack> bundleContents();

    @Contract("_ -> this")
    ItemBuilder potion(@Nullable PotionType potionType);

    @Nullable PotionType potion();

    @Contract("_ -> this")
    ItemBuilder customPotionEffects(@Nullable List<PotionEffect> effects);

    @Contract("_ -> this")
    ItemBuilder addCustomPotionEffects(PotionEffect... effects);

    @Contract("_ -> this")
    ItemBuilder addCustomPotionEffects(Collection<PotionEffect> effects);

    @Contract("_ -> this")
    ItemBuilder removeCustomPotionEffects(PotionEffect... effects);

    @Contract("_ -> this")
    ItemBuilder removeCustomPotionEffects(PotionEffectType... effects);

    @Contract("_ -> this")
    ItemBuilder removeCustomPotionEffects(Collection<?> effects);

    @Nullable List<PotionEffect> customPotionEffects();

    @Contract("_ -> this")
    ItemBuilder trim(@Nullable ArmorTrim trim);

    @Nullable ArmorTrim trim();

    @Contract("_ -> this")
    ItemBuilder fireworkExplosion(@Nullable FireworkEffect fireworkEffect);

    @Nullable FireworkEffect fireworkExplosion();

    @Contract("_ -> this")
    ItemBuilder bannerPatterns(@Nullable List<Pattern> patterns);

    @Contract("_ -> this")
    ItemBuilder addBannerPattern(Pattern pattern);

    @Contract("_ -> this")
    ItemBuilder removeBannerPattern(Pattern pattern);

    @Nullable List<Pattern> bannerPatterns();

    @Contract("_ -> this")
    ItemBuilder dyeColor(@Nullable DyeColor dyeColor);

    @Nullable DyeColor dyeColor();

    @Contract("_ -> this")
    ItemBuilder potDecorations(@Nullable PotDecorations decorations);

    @Nullable PotDecorations potDecorations();

    @Contract("_ -> this")
    ItemBuilder skull(InventoryPlayerHolder player);

    @Contract("_ -> this")
    ItemBuilder skull(Player player);

    @Contract("_, _ -> this")
    ItemBuilder skull(String texture, String signature);

    @Contract("_ -> this")
    ItemBuilder overrideModel(@Nullable Key key);

    @Contract(" -> new")
    ItemBuilder copy();

}
