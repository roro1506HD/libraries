package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingDoubleSupplier<E extends Throwable> {

    double getAsDouble() throws E;

}
