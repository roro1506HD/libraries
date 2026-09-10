package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingShortSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOShortSupplier extends ThrowingShortSupplier<IOException> {

    @Override
    short getAsShort() throws IOException;

}
