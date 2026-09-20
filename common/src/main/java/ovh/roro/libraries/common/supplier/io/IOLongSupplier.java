package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingLongSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOLongSupplier extends ThrowingLongSupplier<IOException> {

    @Override
    long getAsLong() throws IOException;

}
