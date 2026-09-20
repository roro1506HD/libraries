package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ToShortFunction<T> {

    short applyAsShort(T t);

}
