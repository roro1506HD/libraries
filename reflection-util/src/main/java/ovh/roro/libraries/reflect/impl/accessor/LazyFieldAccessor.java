package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.reflect.impl.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class LazyFieldAccessor extends AbstractFieldAccessor {

    private final Field field;

    private @Nullable MethodHandle getterHandle;
    private @Nullable MethodHandle setterHandle;

    public LazyFieldAccessor(Field field) {
        super(Modifier.isStatic(field.getModifiers()));

        this.field = field;
    }

    @Override
    protected MethodHandle getterHandle() {
        if (this.getterHandle == null) {
            this.getterHandle = this.createHandle(this.field, ReflectionHelper.INSTANCE::unreflectGetter);
        }

        return this.getterHandle;
    }

    @Override
    protected MethodHandle setterHandle() {
        if (this.setterHandle == null) {
            this.setterHandle = this.createHandle(this.field, ReflectionHelper.INSTANCE::unreflectSetter);
        }

        return this.setterHandle;
    }
}
