package ovh.roro.libraries.common.function.throwing;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends IOException> {

    T get() throws E;

}
