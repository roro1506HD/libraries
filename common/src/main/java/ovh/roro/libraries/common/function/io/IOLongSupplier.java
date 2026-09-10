package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingLongSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOLongSupplier extends ThrowingLongSupplier<IOException> {

    @Override
    long getAsLong() throws IOException;

}
