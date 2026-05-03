package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@ApiStatus.Internal
public final class LazyFieldAccessor extends AbstractFieldAccessor {

    private final MethodHandles.Lookup lookup;
    private final Field field;

    private @Nullable MethodHandle getterHandle;
    private @Nullable MethodHandle setterHandle;

    public LazyFieldAccessor(MethodHandles.Lookup lookup, Field field) {
        this.lookup = lookup;
        this.field = field;
    }

    @Override
    protected MethodHandle getterHandle() {
        if (this.getterHandle == null) {
            this.getterHandle = this.createHandle(this.field, this.lookup::unreflectGetter);
        }

        return this.getterHandle;
    }

    @Override
    protected MethodHandle setterHandle() {
        if (this.setterHandle == null) {
            this.setterHandle = this.createHandle(this.field, this.lookup::unreflectSetter);
        }

        return this.setterHandle;
    }
}
