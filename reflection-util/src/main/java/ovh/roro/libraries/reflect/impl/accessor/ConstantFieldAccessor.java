package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@ApiStatus.Internal
public final class ConstantFieldAccessor extends AbstractFieldAccessor {

    private final MethodHandle getterHandle;
    private final MethodHandle setterHandle;

    public ConstantFieldAccessor(MethodHandles.Lookup lookup, Field field) {
        this.getterHandle = this.createHandle(field, lookup::unreflectGetter);
        this.setterHandle = this.createHandle(field, lookup::unreflectSetter);
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
