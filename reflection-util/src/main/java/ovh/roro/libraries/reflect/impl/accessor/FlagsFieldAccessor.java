package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class FlagsFieldAccessor extends AbstractFieldAccessor {

    private final MethodHandle getterHandle;
    private final MethodHandle setterHandle;

    public FlagsFieldAccessor(MethodHandles.Lookup lookup, Field field) {
        super(Modifier.isStatic(field.getModifiers()));

        this.getterHandle = this.createHandle(field, lookup::unreflectGetter);
        this.setterHandle = this.createHandle(field, lookup::unreflectSetter);
    }

    @Override
    protected MethodHandle createHandle(Field field, MethodHandleSupplier supplier) {
        try {
            return supplier.get(field);
        } catch (Throwable ex) {
            throw new IllegalStateException("Could not find MethodHandle", ex);
        }
    }

    @Override
    protected MethodHandle getterHandle() {
        return this.getterHandle;
    }

    @Override
    protected MethodHandle setterHandle() {
        return this.setterHandle;
    }
}
