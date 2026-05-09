package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.reflect.api.FieldAccessor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractFieldAccessor implements FieldAccessor {

    private final boolean isStatic;

    public AbstractFieldAccessor(boolean isStatic) {
        this.isStatic = isStatic;
    }

    protected abstract MethodHandle getterHandle();

    protected abstract MethodHandle setterHandle();

    protected MethodHandle createHandle(Field field, MethodHandleSupplier supplier) {
        try {
            return supplier.get(field);
        } catch (Throwable ex) {
            throw new IllegalStateException("Could not find MethodHandle", ex);
        }
    }

    private void setInternal(@Nullable Object instance, @Nullable Object value) {
        try {
            this.setterHandle().invoke(instance, value);
        } catch (Throwable ex) {
            throw new RuntimeException("Could not set field", ex);
        }
    }

    private Object getInternal(@Nullable Object instance) {
        try {
            if (this.isStatic) {
                return this.getterHandle().invoke();
            } else {
                return this.getterHandle().invoke(instance);
            }
        } catch (Throwable ex) {
            throw new RuntimeException("Could not get field", ex);
        }
    }

    @Override
    public void setObject(@Nullable Object instance, @Nullable Object value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setByte(@Nullable Object instance, byte value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setShort(@Nullable Object instance, short value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setInt(@Nullable Object instance, int value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setLong(@Nullable Object instance, long value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setFloat(@Nullable Object instance, float value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setDouble(@Nullable Object instance, double value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setChar(@Nullable Object instance, char value) {
        this.setInternal(instance, value);
    }

    @Override
    public void setBoolean(@Nullable Object instance, boolean value) {
        this.setInternal(instance, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T getObject(@Nullable Object instance) {
        return (T) this.getInternal(instance);
    }

    @Override
    public <T> T getObjectOrThrow(@Nullable Object instance) {
        return Objects.requireNonNull(this.getObject(instance));
    }

    @Override
    public byte getByte(@Nullable Object instance) {
        return (byte) this.getInternal(instance);
    }

    @Override
    public short getShort(@Nullable Object instance) {
        return (short) this.getInternal(instance);
    }

    @Override
    public int getInt(@Nullable Object instance) {
        return (int) this.getInternal(instance);
    }

    @Override
    public long getLong(@Nullable Object instance) {
        return (long) this.getInternal(instance);
    }

    @Override
    public float getFloat(@Nullable Object instance) {
        return (float) this.getInternal(instance);
    }

    @Override
    public double getDouble(@Nullable Object instance) {
        return (double) this.getInternal(instance);
    }

    @Override
    public char getChar(@Nullable Object instance) {
        return (char) this.getInternal(instance);
    }

    @Override
    public boolean getBoolean(@Nullable Object instance) {
        return (boolean) this.getInternal(instance);
    }
}
