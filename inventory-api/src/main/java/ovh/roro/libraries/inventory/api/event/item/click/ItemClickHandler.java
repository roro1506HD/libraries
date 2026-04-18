package ovh.roro.libraries.inventory.api.event.item.click;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;

@ApiStatus.OverrideOnly
public interface ItemClickHandler<T, U extends InventoryPlayerHolder> extends ItemLeftClickHandler<T, U>, ItemRightClickHandler<T, U> {

    @Override
    default void onLeftClick(U player, boolean isShiftClick, @Nullable T value) {
        this.onClick(player, isShiftClick, value);
    }

    @Override
    default void onRightClick(U player, boolean isShiftClick, @Nullable T value) {
        this.onClick(player, isShiftClick, value);
    }

    void onClick(U player, boolean isShiftClick, @Nullable T value);

}
