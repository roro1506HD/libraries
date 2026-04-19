package ovh.roro.libraries.inventory.impl.item.component;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.item.component.DataComponent;

@ApiStatus.Internal
public record DataComponentImpl(
        Key key
) implements DataComponent {
}
