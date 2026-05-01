package ovh.roro.libraries.reflect.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

@ApiStatus.NonExtendable
public interface FieldAccessor {

    void setObject(@Nullable Object instance, @Nullable Object value);

    void setByte(@Nullable Object instance, byte value);

    void setShort(@Nullable Object instance, short value);

    void setInt(@Nullable Object instance, int value);

    void setLong(@Nullable Object instance, long value);

    void setFloat(@Nullable Object instance, float value);

    void setDouble(@Nullable Object instance, double value);

    void setChar(@Nullable Object instance, char value);

    void setBoolean(@Nullable Object instance, boolean value);

    <T> @Nullable T getObject(@Nullable Object instance);

    <T> T getObjectOrThrow(@Nullable Object instance);

    byte getByte(@Nullable Object instance);

    short getShort(@Nullable Object instance);

    int getInt(@Nullable Object instance);

    long getLong(@Nullable Object instance);

    float getFloat(@Nullable Object instance);

    double getDouble(@Nullable Object instance);

    char getChar(@Nullable Object instance);

    boolean getBoolean(@Nullable Object instance);

}
