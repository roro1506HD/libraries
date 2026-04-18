package ovh.roro.libraries.inventory.impl.slot;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.slot.Slot;
import ovh.roro.libraries.inventory.api.slot.SlotType;

import java.util.function.Supplier;

@ApiStatus.Internal
@SuppressWarnings("rawtypes")
public class SlotTypeImpl implements SlotType {

    private final Supplier<Slot> supplier;

    public SlotTypeImpl(Supplier<Slot> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Slot createSlot() {
        return this.supplier.get();
    }
}
