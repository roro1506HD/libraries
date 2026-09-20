package ovh.roro.libraries.common.supplier.io;

import ovh.roro.libraries.common.supplier.throwing.ThrowingIntSupplier;

import java.io.IOException;

@FunctionalInterface
public interface IOIntSupplier extends ThrowingIntSupplier<IOException> {

    @Override
    int getAsInt() throws IOException;

}
