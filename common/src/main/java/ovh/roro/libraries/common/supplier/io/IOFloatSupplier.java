package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingFloatSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOFloatSupplier extends ThrowingFloatSupplier<IOException> {

    @Override
    float getAsFloat() throws IOException;

}
