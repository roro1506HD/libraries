package ovh.roro.libraries.inventory.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.instance.ItemInstance;
import ovh.roro.libraries.inventory.api.item.Item;
import ovh.roro.libraries.inventory.api.layout.Layout;
import ovh.roro.libraries.inventory.api.slot.Slot;

import java.util.function.BiFunction;

@ApiStatus.NonExtendable
@SuppressWarnings("rawtypes")
public interface InventoryContent<T, U extends InventoryPlayerHolder> {

    Slot slot(int index);

    Slot slot(int x, int y);

    void item(int index, @Nullable Item item);

    void item(int index, @Nullable ItemInstance item);

    void item(int x, int y, @Nullable Item item);

    void item(int x, int y, @Nullable ItemInstance item);

    <V> void attachment(int index, BiFunction<U, @Nullable T, @Nullable V> valueMapper);

    <V> void attachment(int x, int y, BiFunction<U, @Nullable T, @Nullable V> valueMapper);

    void update(int index);

    void update(int x, int y);

    void layout(Layout layout, Item item);

}
