package ovh.roro.libraries.common.supplier.throwing;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends IOException> {

    T get() throws E;

}
