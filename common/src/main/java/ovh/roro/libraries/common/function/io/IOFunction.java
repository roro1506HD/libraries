package ovh.roro.libraries.common.function.io;

import ovh.roro.libraries.common.function.throwing.ThrowingFunction;

import java.io.IOException;

@FunctionalInterface
public interface IOFunction<T, R> extends ThrowingFunction<T, R, IOException> {

    @Override
    R apply(T t) throws IOException;

}
