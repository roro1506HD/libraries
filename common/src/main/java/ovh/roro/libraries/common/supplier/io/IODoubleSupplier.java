package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingDoubleSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IODoubleSupplier extends ThrowingDoubleSupplier<IOException> {

    @Override
    double getAsDouble() throws IOException;

}
