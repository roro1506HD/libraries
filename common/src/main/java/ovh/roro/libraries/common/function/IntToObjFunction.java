package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface IntToObjFunction<T> {

    T apply(int value);

}
