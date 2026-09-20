package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingByteSupplier<E extends Throwable> {

    byte getAsByte() throws E;

}
