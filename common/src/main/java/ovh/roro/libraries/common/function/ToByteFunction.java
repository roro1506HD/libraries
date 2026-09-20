package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface ToByteFunction<T> {

    byte applyAsByte(T t);

}
