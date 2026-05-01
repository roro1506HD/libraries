package ovh.roro.libraries.reflect.impl.accessor;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import ovh.roro.libraries.reflect.api.FieldAccessor;

import java.lang.invoke.VarHandle;
import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractFieldAccessor implements FieldAccessor {

    protected abstract VarHandle varHandle();

    @Override
    public void setObject(@Nullable Object instance, @Nullable Object value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setByte(@Nullable Object instance, byte value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setShort(@Nullable Object instance, short value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setInt(@Nullable Object instance, int value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setLong(@Nullable Object instance, long value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setFloat(@Nullable Object instance, float value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setDouble(@Nullable Object instance, double value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setChar(@Nullable Object instance, char value) {
        this.varHandle().set(instance, value);
    }

    @Override
    public void setBoolean(@Nullable Object instance, boolean value) {
        this.varHandle().set(instance, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T getObject(@Nullable Object instance) {
        return (T) this.varHandle().get(instance);
    }

    @Override
    public <T> T getObjectOrThrow(@Nullable Object instance) {
        return Objects.requireNonNull(this.getObject(instance));
    }

    @Override
    public byte getByte(@Nullable Object instance) {
        return (byte) this.varHandle().get(instance);
    }

    @Override
    public short getShort(@Nullable Object instance) {
        return (short) this.varHandle().get(instance);
    }

    @Override
    public int getInt(@Nullable Object instance) {
        return (int) this.varHandle().get(instance);
    }

    @Override
    public long getLong(@Nullable Object instance) {
        return (long) this.varHandle().get(instance);
    }

    @Override
    public float getFloat(@Nullable Object instance) {
        return (float) this.varHandle().get(instance);
    }

    @Override
    public double getDouble(@Nullable Object instance) {
        return (double) this.varHandle().get(instance);
    }

    @Override
    public char getChar(@Nullable Object instance) {
        return (char) this.varHandle().get(instance);
    }

    @Override
    public boolean getBoolean(@Nullable Object instance) {
        return (boolean) this.varHandle().get(instance);
    }
}
