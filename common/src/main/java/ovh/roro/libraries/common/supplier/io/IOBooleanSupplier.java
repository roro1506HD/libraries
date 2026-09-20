package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingBooleanSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOBooleanSupplier extends ThrowingBooleanSupplier<IOException> {

    @Override
    boolean getAsBoolean() throws IOException;

}
