package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOSupplier<T> extends ThrowingSupplier<T, IOException> {

    @Override
    T get() throws IOException;

}
