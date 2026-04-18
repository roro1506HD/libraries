package ovh.roro.libraries.inventory.api.item.defaults;

import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.inventory.api.item.Item;
import ovh.roro.libraries.inventory.api.item.StaticItem;

@ApiStatus.NonExtendable
@ApiStatus.Internal
public interface DefaultItemFactory {

    StaticItem separator(Material material);

    StaticItem separator(Key key);

    Item<Object, ?> back();

}
