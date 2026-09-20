package ovh.roro.libraries.common.supplier.throwing;

@FunctionalInterface
public interface ThrowingFloatSupplier<E extends Throwable> {

    float getAsFloat() throws E;

}
