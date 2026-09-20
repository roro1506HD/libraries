package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {

    boolean applyAsBoolean(T t);

}
