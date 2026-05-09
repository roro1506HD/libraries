package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.reflect.impl.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class ConstantFieldAccessor extends AbstractFieldAccessor {

    private final MethodHandle getterHandle;
    private final MethodHandle setterHandle;

    public ConstantFieldAccessor(Field field) {
        super(Modifier.isStatic(field.getModifiers()));

        this.getterHandle = this.createHandle(field, ReflectionHelper.INSTANCE::unreflectGetter);
        this.setterHandle = this.createHandle(field, ReflectionHelper.INSTANCE::unreflectSetter);
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
