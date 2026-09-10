package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingByteSupplier<E extends Throwable> {

    byte getAsByte() throws E;

}
