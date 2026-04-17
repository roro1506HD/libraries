package ovh.roro.libraries.loader;

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Utility used in other libraries to create unique instances for each plugin
 *
 * @param <T> the library to load
 */
@ApiStatus.Internal
public class LibraryInstanceLoader<T> {

    private static final @NotNull StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private final @NotNull String libraryName;

    private final @NotNull Map<JavaPlugin, T> instanceByPlugin;
    private final @NotNull Map<Class<?>, T> instanceByClass;

    private final @NotNull Function<JavaPlugin, T> instanceCreator;

    /**
     * Creates a loader that is capable of creating multiple instances of a library for multiple plugins,
     * preventing state interference between plugins that use the same library
     *
     * @param libraryName the library name to log if an error occurs
     * @param instanceCreator the function called when creating an instance of the library for a specific plugin
     */
    public LibraryInstanceLoader(@NotNull String libraryName, @NotNull Function<JavaPlugin, T> instanceCreator) {
        this.libraryName = libraryName;

        this.instanceByPlugin = new Object2ObjectArrayMap<>();
        this.instanceByClass = new Object2ObjectArrayMap<>();

        this.instanceCreator = instanceCreator;
    }

    /**
     * Returns the existing library instance for the plugin calling this method, or create a new
     * instance if none is found. This method can be called inside libraries as long as a plugin must call
     * a method that leads to the method calling this one. This method will search through all calling classes
     * and only stop when finding a plugin class or when all classes were analyzed and no plugin was found, in
     * which case it will throw an IllegalStateException
     *
     * @return the library instance associated with the plugin calling this method
     */
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull T getOrCreate() {
        Optional<Class<?>> caller = LibraryInstanceLoader.STACK_WALKER.walk(s -> {
            return s.<Class<?>>map(StackWalker.StackFrame::getDeclaringClass)
                    .filter(clazz -> clazz.getClassLoader() instanceof ConfiguredPluginClassLoader)
                    .findFirst();
        });

        return this.getOrCreate(caller.orElseThrow(() -> new IllegalStateException("Couldn't get caller class")));
    }

    /**
     * Returns the existing library instance for the specific class, or create a new instance
     * if none is found. The caller class doesn't have to be the main plugin's class, any class loaded by
     * the plugin's ClassLoader is enough
     *
     * @param callerClass any class of the plugin getting an instance of the library
     * @return the library instance associated with the caller class' plugin
     */
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
