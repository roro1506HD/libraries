package ovh.roro.libraries.componentutil.internal;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FunctionalInterface
public interface ComponentWidthFunction<T> {

    float apply(T component);

}
