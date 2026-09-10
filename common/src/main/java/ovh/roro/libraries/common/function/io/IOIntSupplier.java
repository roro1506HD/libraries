package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingIntSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOIntSupplier extends ThrowingIntSupplier<IOException> {

    @Override
    int getAsInt() throws IOException;

}
