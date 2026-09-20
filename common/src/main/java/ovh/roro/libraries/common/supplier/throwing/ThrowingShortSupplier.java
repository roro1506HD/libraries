package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingShortSupplier<E extends Throwable> {

    short getAsShort() throws E;

}
