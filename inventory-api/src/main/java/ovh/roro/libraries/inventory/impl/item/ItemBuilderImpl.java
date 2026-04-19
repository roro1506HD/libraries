package ovh.roro.libraries.inventory.impl.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.papermc.paper.adventure.PaperAdventure;
import io.papermc.paper.datacomponent.item.PaperPotDecorations;
import io.papermc.paper.datacomponent.item.PotDecorations;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.MapItemColor;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.block.banner.CraftPatternType;
import org.bukkit.craftbukkit.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftMetaFirework;
import org.bukkit.craftbukkit.inventory.trim.CraftTrimMaterial;
import org.bukkit.craftbukkit.inventory.trim.CraftTrimPattern;
import org.bukkit.craftbukkit.potion.CraftPotionType;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.item.ItemBuilder;
import ovh.roro.libraries.inventory.api.item.component.DataComponent;
import ovh.roro.libraries.language.api.Translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApiStatus.Internal
public class ItemBuilderImpl implements ItemBuilder {

    private final ItemStack delegate;

    private @Nullable Translation name;
    private @Nullable List<Translation> description;

    public ItemBuilderImpl(ItemStack delegate) {
        this.delegate = delegate;
    }

    public ItemBuilderImpl(Material material, int amount) {
        this(new ItemStack(CraftMagicNumbers.getItem(material), amount));
    }

    private static DyeColor fromVanilla(net.minecraft.world.item.DyeColor dyeColor) {
        //noinspection UnstableApiUsage
        return Objects.requireNonNull(DyeColor.getByWoolData((byte) dyeColor.getId()), "Unknown dyeColor");
    }

    private static net.minecraft.world.item.DyeColor toVanilla(DyeColor dyeColor) {
        //noinspection UnstableApiUsage
        return net.minecraft.world.item.DyeColor.byId(dyeColor.getWoolData());
    }

    @Override
    public Material material() {
        return CraftMagicNumbers.getMaterial(this.delegate.getItem());
    }

    @Override
    public ItemBuilder damage(int damage) {
        this.delegate.setDamageValue(damage);
        return this;
    }

    @Override
    public int damage() {
        return this.delegate.getDamageValue();
    }

    @Override
    public ItemBuilder maxDamage(int maxDamage) {
        this.delegate.set(DataComponents.MAX_DAMAGE, maxDamage);
        return this;
    }

    @Override
    public int maxDamage() {
        return this.delegate.getMaxDamage();
    }

    @Override
    public ItemBuilder amount(int amount) {
        this.delegate.setCount(amount);
        return this;
    }

    @Override
    public int amount() {
        return this.delegate.getCount();
    }

    @Override
    public ItemBuilder maxStackSize(int maxStackSize) {
        this.delegate.set(DataComponents.MAX_STACK_SIZE, maxStackSize);
        return this;
    }

    @Override
    public int maxStackSize() {
        return this.delegate.getMaxStackSize();
    }

    @Override
    public ItemBuilder name(@Nullable Translation translation) {
        this.name = translation;
        return this;
    }

    @Override
    public @Nullable Translation name() {
        return this.name;
    }

    @Override
    public ItemBuilder description(Translation... translations) {
        return this.description(Arrays.asList(translations));
    }

    @Override
    public ItemBuilder description(@Nullable List<Translation> translations) {
        this.description = translations;
        return this;
    }

    @Override
    public @Nullable List<Translation> description() {
        return this.description;
    }

    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.delegate.enchant(CraftEnchantment.bukkitToMinecraftHolder(enchantment), level);
        return this;
    }

    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        EnchantmentHelper.updateEnchantments(this.delegate, mutable -> {
            mutable.set(CraftEnchantment.bukkitToMinecraftHolder(enchantment), 0); // Level 0 removes the enchantment
        });

        return this;
    }

    @Override
    public int enchantLevel(Enchantment enchantment) {
        return this.delegate.getEnchantments().getLevel(CraftEnchantment.bukkitToMinecraftHolder(enchantment));
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment) {
        return this.enchantLevel(enchantment) >= 0;
    }

    @Override
    public ItemBuilder hideTooltip(boolean hide) {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);

        if (hide) {
            if (display == null) {
                display = new TooltipDisplay(true, new LinkedHashSet<>());
            } else {
                display = new TooltipDisplay(true, display.hiddenComponents());
            }

            this.delegate.set(DataComponents.TOOLTIP_DISPLAY, display);
        } else {
            if (display != null && display.hideTooltip()) {
                if (display.hiddenComponents().isEmpty()) {
                    this.delegate.remove(DataComponents.TOOLTIP_DISPLAY);
                } else {
                    this.delegate.set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, display.hiddenComponents()));
                }
            }
        }

        return this;
    }

    @Override
    public ItemBuilder hideComponents(DataComponent... components) {
        Key[] componentKeys = new Key[components.length];

        for (int i = 0; i < components.length; i++) {
            componentKeys[i] = components[i].key();
        }

        return this.hideComponents(componentKeys);
    }

    @Override
    public ItemBuilder hideComponents(Key... componentKeys) {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);
        Set<DataComponentType<?>> vanillaTypes = new HashSet<>();

        for (Key componentKey : componentKeys) {
            DataComponentType<?> vanillaType = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(PaperAdventure.asVanilla(componentKey));

            if (vanillaType == null) {
                throw new IllegalArgumentException("Component key does not match any component: " + componentKey);
            }

            vanillaTypes.add(vanillaType);
        }

        if (display == null) {
            display = new TooltipDisplay(false, new LinkedHashSet<>());
            display.hiddenComponents().addAll(vanillaTypes);
        } else {
            display = new TooltipDisplay(display.hideTooltip(), new LinkedHashSet<>(display.hiddenComponents()));
            display.hiddenComponents().addAll(vanillaTypes);
        }

        this.delegate.set(DataComponents.TOOLTIP_DISPLAY, display);

        return this;
    }

    @Override
    public ItemBuilder hideComponents(Collection<?> components) {
        List<Key> componentKeys = new ArrayList<>();

        for (Object component : components) {
            if (component instanceof Key key) {
                componentKeys.add(key);
            } else if (component instanceof DataComponent dataComponent) {
                componentKeys.add(dataComponent.key());
            } else {
                throw new IllegalArgumentException("Cannot hide component for unknown type. Expected Key or DataComponent, found " + component.getClass().getCanonicalName());
            }
        }

        return this.hideComponents(componentKeys.toArray(new Key[0]));
    }

    @Override
    public ItemBuilder hideAllComponents() {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);

        if (display == null) {
            display = new TooltipDisplay(true, new LinkedHashSet<>());

            for (DataComponentType<?> componentType : BuiltInRegistries.DATA_COMPONENT_TYPE) {
                display.hiddenComponents().add(componentType);
            }
        } else {
            display = new TooltipDisplay(display.hideTooltip(), new LinkedHashSet<>());

            for (DataComponentType<?> componentType : BuiltInRegistries.DATA_COMPONENT_TYPE) {
                display.hiddenComponents().add(componentType);
            }
        }

        this.delegate.set(DataComponents.TOOLTIP_DISPLAY, display);

        return this;
    }

    @Override
    public ItemBuilder showComponents(DataComponent... components) {
        Key[] componentKeys = new Key[components.length];

        for (int i = 0; i < components.length; i++) {
            componentKeys[i] = components[i].key();
        }

        return this.showComponents(componentKeys);
    }

    @Override
    public ItemBuilder showComponents(Key... componentKeys) {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);

        if (display == null) {
            return this;
        }

        Set<DataComponentType<?>> vanillaTypes = new HashSet<>();

        for (Key componentKey : componentKeys) {
            DataComponentType<?> vanillaType = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(PaperAdventure.asVanilla(componentKey));

            if (vanillaType == null) {
                throw new IllegalArgumentException("Component key does not match any component: " + componentKey);
            }

            vanillaTypes.add(vanillaType);
        }

        display = new TooltipDisplay(display.hideTooltip(), new LinkedHashSet<>(display.hiddenComponents()));
        display.hiddenComponents().removeAll(vanillaTypes);

        if (display.hiddenComponents().isEmpty() && !display.hideTooltip()) {
            this.delegate.remove(DataComponents.TOOLTIP_DISPLAY);
        } else {
            this.delegate.set(DataComponents.TOOLTIP_DISPLAY, display);
        }

        return this;
    }

    @Override
    public ItemBuilder showComponents(Collection<?> components) {
        List<Key> componentKeys = new ArrayList<>();

        for (Object component : components) {
            if (component instanceof Key key) {
                componentKeys.add(key);
            } else if (component instanceof DataComponent dataComponent) {
                componentKeys.add(dataComponent.key());
            } else {
                throw new IllegalArgumentException("Cannot show component for unknown type. Expected Key or DataComponent, found " + component.getClass().getCanonicalName());
            }
        }

        return this.showComponents(componentKeys.toArray(new Key[0]));
    }

    @Override
    public ItemBuilder showAllComponents() {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);

        if (display != null) {
            if (display.hideTooltip()) {
                this.delegate.set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(true, new LinkedHashSet<>()));
            } else {
                this.delegate.remove(DataComponents.TOOLTIP_DISPLAY);
            }
        }

        return this;
    }

    @Override
    public boolean isComponentHidden(DataComponent component) {
        return this.isComponentHidden(component.key());
    }

    @Override
    public boolean isComponentHidden(Key componentKey) {
        TooltipDisplay display = this.delegate.get(DataComponents.TOOLTIP_DISPLAY);

        if (display == null) {
            return false;
        }

        DataComponentType<?> vanillaType = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(PaperAdventure.asVanilla(componentKey));

        if (vanillaType == null) {
            throw new IllegalArgumentException("Component key does not match any components: " + componentKey);
        }

        return display.hiddenComponents().contains(vanillaType);
    }

    @Override
    public ItemBuilder unbreakable(boolean unbreakable) {
        this.delegate.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);
        return this;
    }

    @Override
    public boolean unbreakable() {
        return this.delegate.has(DataComponents.UNBREAKABLE);
    }

    @Override
    public ItemBuilder glowing(boolean glowing) {
        this.delegate.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, glowing);
        return this;
    }

    @Override
    public ItemBuilder resetGlowing() {
        this.delegate.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        return this;
    }

    @Override
    public boolean glowing() {
        Boolean glint = this.delegate.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        if (glint != null) {
            return glint;
        }

        return this.delegate.getItem().isFoil(this.delegate);
    }

    @Override
    public ItemBuilder color(Material... dyes) {
        return this.color(this.parseDyes(dyes));
    }

    @Override
    public ItemBuilder color(DyeColor... dyes) {
        return this.color(dyes, 0, 0, 0);
    }

    @Override
    public ItemBuilder color(Collection<?> dyes) {
        return this.color(this.parseDyes(dyes));
    }

    @Override
    public ItemBuilder mixColor(Material... dyes) {
        return this.mixColor(this.parseDyes(dyes));
    }

    @Override
    public ItemBuilder mixColor(DyeColor... dyes) {
        int currentColor = this.color();
        int r = ARGB.red(currentColor);
        int g = ARGB.green(currentColor);
        int b = ARGB.blue(currentColor);

        return this.color(dyes, r, g, b);
    }

    @Override
    public ItemBuilder mixColor(Collection<?> dyes) {
        return this.mixColor(this.parseDyes(dyes));
    }

    private DyeColor[] parseDyes(Material[] dyes) {
        List<DyeColor> colors = new ArrayList<>();

        for (Material dye : dyes) {
            if (CraftMagicNumbers.getItem(dye) instanceof DyeItem dyeItem) {
                colors.add(ItemBuilderImpl.fromVanilla(dyeItem.getDyeColor()));
            } else {
                throw new IllegalArgumentException("Invalid dye item: " + dye.key());
            }
        }

        return colors.toArray(DyeColor[]::new);
    }

    private DyeColor[] parseDyes(Collection<?> dyes) {
        List<DyeColor> colors = new ArrayList<>();

        for (Object dye : dyes) {
            if (dye instanceof Material material) {
                if (CraftMagicNumbers.getItem(material) instanceof DyeItem dyeItem) {
                    colors.add(ItemBuilderImpl.fromVanilla(dyeItem.getDyeColor()));
                } else {
                    throw new IllegalArgumentException("Invalid dye item: " + material.key());
                }
            } else if (dye instanceof DyeColor dyeColor) {
                colors.add(dyeColor);
            } else {
                throw new IllegalArgumentException("Cannot parse dye for unknown type. Expected Material or DyeColor, found " + dye.getClass().getCanonicalName());
            }
        }

        return colors.toArray(DyeColor[]::new);
    }

    private ItemBuilder color(DyeColor[] dyes, int r, int g, int b) {
        int appliedDyes = 0;
        int highestChannelTotal = 0;

        if (r != 0 || g != 0 || b != 0) {
            appliedDyes = 1;
            highestChannelTotal = Math.max(r, Math.max(g, b));
        }

        for (DyeColor dye : dyes) {
            //noinspection UnstableApiUsage
            net.minecraft.world.item.DyeColor dyeColor = net.minecraft.world.item.DyeColor.byId(dye.getWoolData());
            int rgb = dyeColor.getTextureDiffuseColor();
            int dyeRed = ARGB.red(rgb);
            int dyeGreen = ARGB.green(rgb);
            int dyeBlue = ARGB.blue(rgb);

            r += dyeRed;
            g += dyeGreen;
            b += dyeBlue;
            highestChannelTotal += Math.max(dyeRed, Math.max(dyeGreen, dyeBlue));
            appliedDyes++;
        }

        r /= appliedDyes;
        g /= appliedDyes;
        b /= appliedDyes;

        float highestChannelMean = (float) highestChannelTotal / appliedDyes;
        float highestChannelNow = Math.max(r, Math.max(g, b));

        r = (int) (r * highestChannelMean / highestChannelNow);
        g = (int) (g * highestChannelMean / highestChannelNow);
        b = (int) (b * highestChannelMean / highestChannelNow);

        return this.color(ARGB.color(0, r, g, b));
    }

    @Override
    public ItemBuilder color(int rgb) {
        rgb &= 0xFFFFFF; // Get rid of alpha channel no matter what

        if (this.delegate.is(ItemTags.DYEABLE)) {
            this.delegate.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb));
        } else if (this.delegate.is(Items.FILLED_MAP)) {
            this.delegate.set(DataComponents.MAP_COLOR, new MapItemColor(rgb));
        } else {
            PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);
            if (potionContents != null) {
                this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potionContents.potion(), Optional.of(rgb), potionContents.customEffects(), potionContents.customName()));
            } else {
                this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.of(rgb), List.of(), Optional.empty()));
            }
        }

        return this;
    }

    @Override
    public ItemBuilder removeColor() {
        if (this.delegate.is(ItemTags.DYEABLE)) {
            this.delegate.remove(DataComponents.DYED_COLOR);
        } else if (this.delegate.is(Items.FILLED_MAP)) {
            this.delegate.remove(DataComponents.MAP_COLOR);
        } else {
            PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);
            if (potionContents != null) {
                if (potionContents.potion().isEmpty() && potionContents.customEffects().isEmpty() && potionContents.customName().isEmpty()) {
                    this.delegate.remove(DataComponents.POTION_CONTENTS);
                } else {
                    this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potionContents.potion(), Optional.empty(), potionContents.customEffects(), potionContents.customName()));
                }
            }
        }

        return this;
    }

    @Override
    public ItemBuilder resetColor() {
        if (this.delegate.is(Items.FILLED_MAP)) {
            this.delegate.set(DataComponents.MAP_COLOR, MapItemColor.DEFAULT);
        }

        return this.removeColor();
    }

    @Override
    public int color() {
        if (this.delegate.is(ItemTags.DYEABLE)) {
            DyedItemColor dyedItemColor = this.delegate.get(DataComponents.DYED_COLOR);
            return dyedItemColor == null ? 0 : dyedItemColor.rgb();
        } else if (this.delegate.is(Items.FILLED_MAP)) {
            MapItemColor mapItemColor = this.delegate.get(DataComponents.MAP_COLOR);
            return mapItemColor == null ? 0 : mapItemColor.rgb();
        }

        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) {
            return potionContents.customColor().orElse(0);
        }

        return 0;
    }

    @Override
    public boolean hasColor() {
        if (this.delegate.is(ItemTags.DYEABLE)) {
            return this.delegate.has(DataComponents.DYED_COLOR);
        } else if (this.delegate.is(Items.FILLED_MAP)) {
            MapItemColor mapItemColor = this.delegate.get(DataComponents.MAP_COLOR);
            return mapItemColor != null && mapItemColor != MapItemColor.DEFAULT;
        }

        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) {
            return potionContents.customColor().isPresent();
        }

        return false;
    }

    @Override
    public ItemBuilder bundleContents(org.bukkit.inventory.ItemStack... contents) {
        return this.bundleContents(Arrays.asList(contents));
    }

    @Override
    public ItemBuilder bundleContents(@Nullable List<org.bukkit.inventory.ItemStack> contents) {
        if (contents == null) {
            this.delegate.remove(DataComponents.BUNDLE_CONTENTS);
        } else {
            this.delegate.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(CraftItemStack.asNMSCopy(contents)));
        }

        return this;
    }

    @Override
    public @Nullable List<org.bukkit.inventory.ItemStack> bundleContents() {
        BundleContents bundleContents = this.delegate.get(DataComponents.BUNDLE_CONTENTS);
        if (bundleContents != null) {
            List<org.bukkit.inventory.ItemStack> contents = new ArrayList<>(bundleContents.size());

            for (ItemStack stack : bundleContents.items()) {
                contents.add(CraftItemStack.asCraftMirror(stack));
            }

            return contents;
        }

        return null;
    }

    @Override
    public ItemBuilder potion(@Nullable PotionType potionType) {
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);

        if (potionType == null) {
            if (potionContents != null) {
                if (potionContents.customColor().isEmpty() && potionContents.customEffects().isEmpty() && potionContents.customName().isEmpty()) {
                    this.delegate.remove(DataComponents.POTION_CONTENTS);
                } else {
                    this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), potionContents.customColor(), potionContents.customEffects(), potionContents.customName()));
                }
            }
        } else {
            Optional<Holder<Potion>> potion = Optional.of(CraftPotionType.bukkitToMinecraftHolder(potionType));
            if (potionContents != null) {
                this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potion, potionContents.customColor(), potionContents.customEffects(), potionContents.customName()));
            } else {
                this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potion, Optional.empty(), List.of(), Optional.empty()));
            }
        }

        return this;
    }

    @Override
    public @Nullable PotionType potion() {
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) {
            return potionContents.potion()
                    .map(CraftPotionType::minecraftHolderToBukkit)
                    .orElse(null);
        }

        return null;
    }

    @Override
    public ItemBuilder customPotionEffects(@Nullable List<PotionEffect> effects) {
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);

        if (effects == null || effects.isEmpty()) {
            if (potionContents != null) {
                if (potionContents.potion().isEmpty() && potionContents.customColor().isEmpty() && potionContents.customName().isEmpty()) {
                    this.delegate.remove(DataComponents.POTION_CONTENTS);
                } else {
                    this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potionContents.potion(), potionContents.customColor(), List.of(), potionContents.customName()));
                }
            }
        } else {
            List<MobEffectInstance> mobEffects = new ArrayList<>(effects.size());

            for (PotionEffect effect : effects) {
                mobEffects.add(CraftPotionUtil.fromBukkit(effect));
            }

            this.customPotionEffectsInternal(mobEffects);
        }

        return this;
    }

    private ItemBuilder customPotionEffectsInternal(List<MobEffectInstance> effects) {
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);

        if (potionContents != null) {
            this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(potionContents.potion(), potionContents.customColor(), effects, potionContents.customName()));
        } else {
            this.delegate.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), effects, Optional.empty()));
        }

        return this;
    }

    @Override
    public ItemBuilder addCustomPotionEffects(PotionEffect... effects) {
        return this.addCustomPotionEffects(Arrays.asList(effects));
    }

    @Override
    public ItemBuilder addCustomPotionEffects(Collection<PotionEffect> effects) {
        if (effects.isEmpty()) {
            return this;
        }

        List<MobEffectInstance> mobEffects = new ArrayList<>(effects.size());
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);

        if (potionContents != null) {
            mobEffects.addAll(potionContents.customEffects());
        }

        for (PotionEffect effect : effects) {
            mobEffects.add(CraftPotionUtil.fromBukkit(effect));
        }

        return this.customPotionEffectsInternal(mobEffects);
    }

    @Override
    public ItemBuilder removeCustomPotionEffects(PotionEffect... effects) {
        return this.removeCustomPotionEffects(Arrays.asList(effects));
    }

    @Override
    public ItemBuilder removeCustomPotionEffects(PotionEffectType... effects) {
        return this.removeCustomPotionEffects(Arrays.asList(effects));
    }

    @Override
    public ItemBuilder removeCustomPotionEffects(Collection<?> effects) {
        if (effects.isEmpty()) {
            return this;
        }

        List<PotionEffect> potionEffects = this.customPotionEffects();
        if (potionEffects == null || potionEffects.isEmpty()) {
            return this;
        }

        for (Object effect : effects) {
            if (effect instanceof PotionEffect potionEffect) {
                potionEffects.remove(potionEffect);
            } else if (effect instanceof PotionEffectType potionEffectType) {
                potionEffects.removeIf(potionEffect -> potionEffect.getType() == potionEffectType);
            } else {
                throw new IllegalArgumentException("Cannot remove custom potion effects for unknown type. Expected PotionEffect or PotionEffectType, found " + effect.getClass().getCanonicalName());
            }
        }

        return this.customPotionEffects(potionEffects);
    }

    @Override
    public @Nullable List<PotionEffect> customPotionEffects() {
        PotionContents potionContents = this.delegate.get(DataComponents.POTION_CONTENTS);

        if (potionContents == null) {
            return null;
        }

        List<PotionEffect> potionEffects = new ArrayList<>(potionContents.customEffects().size());
        for (MobEffectInstance effect : potionContents.customEffects()) {
            potionEffects.add(CraftPotionUtil.toBukkit(effect));
        }

        return potionEffects;
    }

    @Override
    public ItemBuilder trim(@Nullable ArmorTrim trim) {
        if (trim == null) {
            this.delegate.remove(DataComponents.TRIM);
        } else {
            this.delegate.set(DataComponents.TRIM, new net.minecraft.world.item.equipment.trim.ArmorTrim(
                    CraftTrimMaterial.bukkitToMinecraftHolder(trim.getMaterial()),
                    CraftTrimPattern.bukkitToMinecraftHolder(trim.getPattern())
            ));
        }

        return this;
    }

    @Override
    public @Nullable ArmorTrim trim() {
        net.minecraft.world.item.equipment.trim.ArmorTrim trim = this.delegate.get(DataComponents.TRIM);
        if (trim != null) {
            return new ArmorTrim(
                    CraftTrimMaterial.minecraftHolderToBukkit(trim.material()),
                    CraftTrimPattern.minecraftHolderToBukkit(trim.pattern())
            );
        }

        return null;
    }

    @Override
    public ItemBuilder fireworkExplosion(@Nullable FireworkEffect fireworkEffect) {
        if (fireworkEffect == null) {
            this.delegate.remove(DataComponents.FIREWORK_EXPLOSION);
        } else {
            this.delegate.set(DataComponents.FIREWORK_EXPLOSION, CraftMetaFirework.getExplosion(fireworkEffect));
        }

        return this;
    }

    @Override
    public @Nullable FireworkEffect fireworkExplosion() {
        FireworkExplosion fireworkExplosion = this.delegate.get(DataComponents.FIREWORK_EXPLOSION);
        if (fireworkExplosion != null) {
            return CraftMetaFirework.getEffect(fireworkExplosion);
        }

        return null;
    }

    @Override
    public ItemBuilder bannerPatterns(@Nullable List<Pattern> patterns) {
        if (patterns == null) {
            this.delegate.remove(DataComponents.BANNER_PATTERNS);
        } else {
            List<BannerPatternLayers.Layer> layers = new ArrayList<>();

            for (Pattern pattern : patterns) {
                layers.add(new BannerPatternLayers.Layer(
                        CraftPatternType.bukkitToMinecraftHolder(pattern.getPattern()),
                        ItemBuilderImpl.toVanilla(pattern.getColor())
                ));
            }

            this.delegate.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers(layers));
        }

        return this;
    }

    @Override
    public ItemBuilder addBannerPattern(Pattern pattern) {
        List<Pattern> patterns = Objects.requireNonNullElseGet(this.bannerPatterns(), ArrayList::new);
        patterns.add(pattern);
        return this.bannerPatterns(patterns);
    }

    @Override
    public ItemBuilder removeBannerPattern(Pattern pattern) {
        List<Pattern> patterns = this.bannerPatterns();
        if (patterns != null) {
            patterns.remove(pattern);
            this.bannerPatterns(patterns);
        }

        return this;
    }

    @Override
    public @Nullable List<Pattern> bannerPatterns() {
        BannerPatternLayers layers = this.delegate.get(DataComponents.BANNER_PATTERNS);
        if (layers != null) {
            List<Pattern> patterns = new ArrayList<>();

            for (BannerPatternLayers.Layer layer : layers.layers()) {
                patterns.add(new Pattern(
                        ItemBuilderImpl.fromVanilla(layer.color()),
                        CraftPatternType.minecraftHolderToBukkit(layer.pattern())
                ));
            }

            return patterns;
        }

        return null;
    }

    @Override
    public ItemBuilder dyeColor(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            this.delegate.remove(DataComponents.BASE_COLOR);
        } else {
            this.delegate.set(DataComponents.BASE_COLOR, ItemBuilderImpl.toVanilla(dyeColor));
        }

        return this;
    }

    @Override
    public @Nullable DyeColor dyeColor() {
        net.minecraft.world.item.DyeColor dyeColor = this.delegate.get(DataComponents.BASE_COLOR);
        if (dyeColor != null) {
            return ItemBuilderImpl.fromVanilla(dyeColor);
        }

        return null;
    }

    @Override
    public ItemBuilder potDecorations(@Nullable PotDecorations decorations) {
        if (decorations == null) {
            this.delegate.remove(DataComponents.POT_DECORATIONS);
        } else {
            this.delegate.set(DataComponents.POT_DECORATIONS, ((PaperPotDecorations) decorations).impl());
        }

        return this;
    }

    @Override
    public @Nullable PotDecorations potDecorations() {
        net.minecraft.world.level.block.entity.PotDecorations potDecorations = this.delegate.get(DataComponents.POT_DECORATIONS);
        if (potDecorations != null) {
            return new PaperPotDecorations(potDecorations);
        }

        return null;
    }

    @Override
    public ItemBuilder skull(InventoryPlayerHolder player) {
        return this.skull(player.bukkitPlayer());
    }

    @Override
    public ItemBuilder skull(Player player) {
        GameProfile gameProfile = ((CraftPlayer) player).getProfile();

        for (Property property : gameProfile.properties().get("textures")) {
            return this.skull(property.value(), Objects.requireNonNull(property.signature()));
        }

        return this;
    }

    @Override
    public ItemBuilder skull(String texture, String signature) {
        Preconditions.checkState(this.delegate.getItem() == Items.PLAYER_HEAD, "ItemBuilder#skull can only be used on skulls");

        GameProfile gameProfile = new GameProfile(
                UUID.randomUUID(),
                "",
                new PropertyMap(ImmutableMultimap.of(
                        "textures",
                        new Property("textures", texture, signature)
                ))
        );

        this.delegate.set(DataComponents.PROFILE, ResolvableProfile.createResolved(gameProfile));

        return this;
    }

    @Override
    public ItemBuilder overrideModel(@Nullable Key key) {
        if (key == null) {
            this.delegate.set(DataComponents.ITEM_MODEL, this.delegate.getItem().components().get(DataComponents.ITEM_MODEL));
        } else {
            this.delegate.set(DataComponents.ITEM_MODEL, PaperAdventure.asVanilla(key));
        }

        return this;
    }

    @Override
    public ItemBuilder copy() {
        ItemBuilderImpl builder = new ItemBuilderImpl(this.delegate.copy());

        builder.name = this.name;
        builder.description = this.description;

        return builder;
    }

    public ItemStack delegate() {
        return this.delegate;
    }
}
