package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingFloatSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOFloatSupplier extends ThrowingFloatSupplier<IOException> {

    @Override
    float getAsFloat() throws IOException;

}
