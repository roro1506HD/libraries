package ovh.roro.libraries.loader.standalone;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.loader.LibraryLoader;

import java.util.function.Supplier;

/**
 * Standalone implementation of the library loader, in this context only one instance of the library is created
 *
 * @param <T> the library to load
 */
@ApiStatus.Internal
public class StandaloneLibraryLoader<T> implements LibraryLoader<T> {

    private final T instance;

    /**
     * Creates a loader that will return the same instance of the library throughout the application's lifecycle
     *
     * @param instanceCreator the supplier called when creating the instance of the library
     */
    public StandaloneLibraryLoader(Supplier<T> instanceCreator) {
        this.instance = instanceCreator.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrCreate() {
        return this.instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrCreate(Class<?> callerClass) {
        return this.instance;
    }
}
