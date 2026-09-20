package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ToFloatFunction<T> {

    float applyAsFloat(T t);

}
