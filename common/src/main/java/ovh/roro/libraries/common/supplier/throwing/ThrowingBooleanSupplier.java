package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingBooleanSupplier<E extends Throwable> {

    boolean getAsBoolean() throws E;

}
