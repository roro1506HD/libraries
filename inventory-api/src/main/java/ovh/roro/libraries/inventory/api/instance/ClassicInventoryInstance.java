package ovh.roro.libraries.inventory.api.instance;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.inventory.api.InventoryContent;
import ovh.roro.libraries.inventory.api.InventoryPlayerHolder;
import ovh.roro.libraries.inventory.api.slot.SlotType;
import ovh.roro.libraries.language.api.Translation;

@ApiStatus.OverrideOnly
public interface ClassicInventoryInstance<T, U extends InventoryPlayerHolder> extends InventoryInstance<T, U> {

    Translation title(U player, @Nullable T value);

    int rows();

    default int maxStackSize() {
        return 64;
    }

    SlotType slotType(int index);

    void buildInventory(InventoryContent<T, U> content);

}
