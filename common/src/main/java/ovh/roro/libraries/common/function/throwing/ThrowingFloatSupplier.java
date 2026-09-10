package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingFloatSupplier<E extends Throwable> {

    float getAsFloat() throws E;

}
