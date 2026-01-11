package ovh.roro.libraries.componentutil;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @hidden
 */
@ApiStatus.Internal
@FunctionalInterface
interface ComponentWidthFunction<T> {

    float apply(@NotNull T component);

}
