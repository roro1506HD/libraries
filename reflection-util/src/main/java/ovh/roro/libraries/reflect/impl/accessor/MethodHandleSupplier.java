package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

@ApiStatus.Internal
public interface MethodHandleSupplier {

    MethodHandle get(Field field) throws Throwable;

}
