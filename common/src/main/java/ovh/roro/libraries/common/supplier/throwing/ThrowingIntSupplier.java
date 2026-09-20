package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingIntSupplier<E extends Throwable> {

    int getAsInt() throws E;

}
