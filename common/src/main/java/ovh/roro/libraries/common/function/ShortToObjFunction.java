package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ShortToObjFunction<T> {

    T apply(short value);

}
