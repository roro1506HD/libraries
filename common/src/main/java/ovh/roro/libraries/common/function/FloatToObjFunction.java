package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface FloatToObjFunction<T> {

    T apply(float value);

}
