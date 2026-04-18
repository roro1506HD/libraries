package ovh.roro.libraries.inventory.api;

import org.bukkit.entity.Player;
import ovh.roro.libraries.language.api.LanguagePlayerHolder;

public interface InventoryPlayerHolder extends LanguagePlayerHolder {

    Player bukkitPlayer();

}
