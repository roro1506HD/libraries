package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ByteToObjFunction<T> {

    T apply(byte value);

}
