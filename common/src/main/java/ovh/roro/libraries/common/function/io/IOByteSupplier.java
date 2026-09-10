package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingByteSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOByteSupplier extends ThrowingByteSupplier<IOException> {

    @Override
    byte getAsByte() throws IOException;

}
