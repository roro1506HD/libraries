package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingBooleanSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOBooleanSupplier extends ThrowingBooleanSupplier<IOException> {

    @Override
    boolean getAsBoolean() throws IOException;

}
