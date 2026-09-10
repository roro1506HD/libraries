package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingLongSupplier<E extends Throwable> {

    long getAsLong() throws E;

}
