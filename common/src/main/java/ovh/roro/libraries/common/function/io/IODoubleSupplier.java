package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingDoubleSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IODoubleSupplier extends ThrowingDoubleSupplier<IOException> {

    @Override
    double getAsDouble() throws IOException;

}
