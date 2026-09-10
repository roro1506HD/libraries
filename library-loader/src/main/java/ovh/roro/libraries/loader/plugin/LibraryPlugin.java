package ovh.roro.libraries.loader.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record LibraryPlugin(
        JavaPlugin plugin,
        Class<?> mainClass
) {
}
