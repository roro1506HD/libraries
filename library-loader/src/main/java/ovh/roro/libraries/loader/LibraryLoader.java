package ovh.roro.libraries.loader;

import org.jetbrains.annotations.ApiStatus;

/**
 * Utility used in other libraries to create unique instances for each plugin/application
 *
 * @param <T> the library to load
 */
@ApiStatus.Internal
public interface LibraryLoader<T> {

    /**
     * Returns if the current application is a plugin running inside a Paper server
     *
     * @return {@code true} if this is a plugin running inside a Paper server, otherwise {@code false}
     */
    static boolean isPaper() {
        try {
            Class.forName("io.papermc.paper.configuration.PaperConfigurations");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    /**
     * Returns the existing library instance for the plugin/application calling this method, or create a new
     * instance if none is found.
     * <p>
     * For plugin-specific loader, this method can be called inside libraries as long as a plugin must call
     * a method that leads to the method calling this one. This method will search through all calling classes
     * and only stop when finding a plugin class or when all classes were analyzed and no plugin was found, in
     * which case it will throw an IllegalStateException
     *
     * @return the library instance associated with the plugin/application calling this method
     */
    T getOrCreate();

    /**
     * Returns the existing library instance for the specific class, or create a new instance
     * if none is found.
     * <p>
     * For plugin-specific loader, the caller class doesn't have to be the main plugin's class, any class loaded by
     * the plugin's ClassLoader is enough
     *
     * @param callerClass any class of the plugin/application getting an instance of the library
     * @return the library instance associated with the caller class' plugin/application
     */
    T getOrCreate(Class<?> callerClass);

}
