package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class LazyFieldAccessor extends AbstractFieldAccessor {

    private final MethodHandles.Lookup lookup;
    private final Field field;

    private @Nullable VarHandle varHandle;

    public LazyFieldAccessor(MethodHandles.Lookup lookup, Field field) {
        this.lookup = lookup;
        this.field = field;
    }

    @Override
    protected VarHandle varHandle() {
        if (this.varHandle == null) {
            try {
                if (Modifier.isStatic(this.field.getModifiers())) {
                    this.varHandle = this.lookup.findStaticVarHandle(this.field.getDeclaringClass(), this.field.getName(), this.field.getType());
                } else {
                    this.varHandle = this.lookup.findVarHandle(this.field.getDeclaringClass(), this.field.getName(), this.field.getType());
                }
            } catch (Exception ex) {
                throw new IllegalStateException("Could not find VarHandle", ex);
            }
        }

        return this.varHandle;
    }
}
