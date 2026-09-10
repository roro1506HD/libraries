package ovh.roro.libraries.loader.plugin;

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.loader.LibraryLoader;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Plugin-specific implementation of the library loader, only works in a paper server
 *
 * @param <T> the library to load
 */
@ApiStatus.Internal
public class PluginLibraryLoader<T> implements LibraryLoader<T> {

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private final Map<Class<?>, T> instanceByClass;

    private final Function<JavaPlugin, T> instanceCreator;

    /**
     * Creates a loader that is capable of creating multiple instances of a library for multiple plugins,
     * preventing state interference between plugins that use the same library
     *
     * @param instanceCreator the function called when creating an instance of the library for a specific plugin
     */
    public PluginLibraryLoader(Function<JavaPlugin, T> instanceCreator) {
        this.instanceByClass = new Object2ObjectArrayMap<>();

        this.instanceCreator = instanceCreator;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public T getOrCreate() {
        Optional<Class<?>> caller = PluginLibraryLoader.STACK_WALKER.walk(s -> {
            return s.<Class<?>>map(StackWalker.StackFrame::getDeclaringClass)
                    .filter(clazz -> clazz.getClassLoader() instanceof ConfiguredPluginClassLoader)
                    .findFirst();
        });

        return this.getOrCreate(caller.orElseThrow(() -> new IllegalStateException("Couldn't get caller class")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrCreate(Class<?> callerClass) {
        LibraryPlugin plugin = this.getPluginMainClassFromCallerClass(callerClass);
        T existingInstance = this.instanceByClass.get(plugin.mainClass());

        if (existingInstance != null) {
            return existingInstance;
        }

        // Plugin doesn't have an instance, create it
        T instance = this.instanceCreator.apply(plugin.plugin());

        this.instanceByClass.put(callerClass, instance);

        return instance;
    }

    @SuppressWarnings("UnstableApiUsage")
    private LibraryPlugin getPluginMainClassFromCallerClass(Class<?> callerClass) {
        ClassLoader classLoader = callerClass.getClassLoader();

        if (!(classLoader instanceof ConfiguredPluginClassLoader pluginClassLoader)) {
            throw new IllegalStateException("Caller class is not part of a JavaPlugin");
        }

        JavaPlugin plugin = pluginClassLoader.getPlugin();

        if (plugin == null) {
            throw new IllegalStateException("Couldn't find the plugin associated with the classloader (called too early)");
        }

        try {
            return new LibraryPlugin(
                    plugin,
                    Class.forName(plugin.getPluginMeta().getMainClass(), false, classLoader)
            );
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Couldn't find main class in plugin's classloader");
        }
    }
}
