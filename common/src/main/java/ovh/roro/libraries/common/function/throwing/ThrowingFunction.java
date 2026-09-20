package ovh.roro.libraries.common.function.throwing;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    R apply(T t) throws E;

}
