package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingBooleanSupplier<E extends Throwable> {

    boolean getAsBoolean() throws E;

}
