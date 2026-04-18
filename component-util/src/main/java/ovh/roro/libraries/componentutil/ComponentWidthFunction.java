package ovh.roro.libraries.componentutil;

import org.jetbrains.annotations.ApiStatus;

/**
 * @hidden
 */
@ApiStatus.Internal
@FunctionalInterface
interface ComponentWidthFunction<T> {

    float apply(T component);

}
