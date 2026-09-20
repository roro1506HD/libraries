package ovh.roro.libraries.common.function;

@FunctionalInterface
public interface DoubleToObjFunction<T> {

    T apply(double value);

}
