package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface BooleanToObjFunction<T> {

    T apply(boolean value);

}
