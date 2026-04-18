package ovh.roro.libraries.inventory.api.slot;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.impl.slot.AttachedSlotImpl;
import ovh.roro.libraries.inventory.impl.slot.DynamicSlotImpl;
import ovh.roro.libraries.inventory.impl.slot.StaticSlotImpl;

@ApiStatus.NonExtendable
public interface SlotType {

    SlotType DYNAMIC = DynamicSlotImpl.TYPE;
    SlotType ATTACHED = AttachedSlotImpl.TYPE;
    SlotType STATIC = StaticSlotImpl.TYPE;

    @SuppressWarnings("rawtypes")
    Slot createSlot();

}
