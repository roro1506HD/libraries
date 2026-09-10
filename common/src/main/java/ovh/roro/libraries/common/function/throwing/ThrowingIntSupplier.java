package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingIntSupplier<E extends Throwable> {

    int getAsInt() throws E;

}
