package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingShortSupplier<E extends Throwable> {

    short getAsShort() throws E;

}
