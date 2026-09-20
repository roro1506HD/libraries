package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingLongSupplier<E extends Throwable> {

    long getAsLong() throws E;

}
