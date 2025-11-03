package ovh.roro.libraries.loader;

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class LibraryInstanceLoader<T> {

    private static final @NotNull StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private final @NotNull String libraryName;

    private final @NotNull Map<JavaPlugin, T> instanceByPlugin;
    private final @NotNull Map<Class<?>, T> instanceByClass;

    private final @NotNull Function<JavaPlugin, T> instanceCreator;

    public LibraryInstanceLoader(@NotNull String libraryName, @NotNull Function<JavaPlugin, T> instanceCreator) {
        this.libraryName = libraryName;

        this.instanceByPlugin = new Object2ObjectArrayMap<>();
        this.instanceByClass = new Object2ObjectArrayMap<>();

        this.instanceCreator = instanceCreator;
    }

    @SuppressWarnings("UnstableApiUsage")
    public @NotNull T getOrCreate() {
        Optional<Class<?>> caller = LibraryInstanceLoader.STACK_WALKER.walk(s -> {
            return s.<Class<?>>map(StackWalker.StackFrame::getDeclaringClass)
                    .filter(clazz -> clazz.getClassLoader() instanceof ConfiguredPluginClassLoader)
                    .findFirst();
        });

        return this.getOrCreate(caller.orElseThrow(() -> new IllegalStateException("Couldn't get caller class")));
    }

    public @NotNull T getOrCreate(@NotNull Class<?> callerClass) {
        T existingInstance = this.instanceByClass.get(callerClass);

        if (existingInstance != null) {
            return existingInstance;
        }

        JavaPlugin plugin = this.getPluginFromClass(callerClass);

        existingInstance = this.instanceByPlugin.get(plugin);

        // Plugin has an instance but class don't
        if (existingInstance != null) {
            this.instanceByClass.put(callerClass, existingInstance);

            return existingInstance;
        }

        // Neither plugin nor class has an instance, create it
        T instance = this.instanceCreator.apply(plugin);

        this.instanceByPlugin.put(plugin, instance);
        this.instanceByClass.put(callerClass, instance);

        return instance;
    }

    @SuppressWarnings("UnstableApiUsage")
    private @NotNull JavaPlugin getPluginFromClass(@NotNull Class<?> callerClass) {
        ClassLoader classLoader = callerClass.getClassLoader();

        if (!(classLoader instanceof ConfiguredPluginClassLoader pluginClassLoader)) {
            throw new IllegalStateException(callerClass.getName() + " tried to get its " + this.libraryName + " but is not in any JavaPlugin classloader");
        }

        JavaPlugin plugin = pluginClassLoader.getPlugin();

        if (plugin == null) {
            throw new IllegalStateException(callerClass.getName() + " tried to get its " + this.libraryName + " too early");
        }

        return plugin;
    }
}
