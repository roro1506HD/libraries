package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingShortSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOShortSupplier extends ThrowingShortSupplier<IOException> {

    @Override
    short getAsShort() throws IOException;

}
