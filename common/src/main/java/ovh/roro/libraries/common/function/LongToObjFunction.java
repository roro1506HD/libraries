package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface LongToObjFunction<T> {

    T apply(long value);

}
