package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingDoubleSupplier<E extends Throwable> {

    double getAsDouble() throws E;

}
