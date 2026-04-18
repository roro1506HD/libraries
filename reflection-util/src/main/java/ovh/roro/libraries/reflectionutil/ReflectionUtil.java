package ovh.roro.libraries.reflectionutil;

import org.jspecify.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    private static final MethodHandles.Lookup LOOKUP;
    private static final Unsafe UNSAFE;

    private ReflectionUtil() throws IllegalAccessException {
        throw new IllegalAccessException("This class cannot be instantiated");
    }

    static {
        MethodHandles.Lookup lookup;
        Unsafe unsafe;

        try {
            lookup = MethodHandles.lookup();
            unsafe = (Unsafe) ReflectionUtil.getField(Unsafe.class, "theUnsafe").get(null);
        } catch (Throwable ex) {
            throw new IllegalStateException("Couldn't get Unsafe", ex);
        }

        LOOKUP = lookup;
        UNSAFE = unsafe;
    }

    public static Class<?> getClass(String classPath) {
        try {
            return Class.forName(classPath);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find class", ex);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find field", ex);
        }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);

            return method;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find method", ex);
        }
    }

    public static MethodHandle getMethodHandle(String className, String methodName, Class<?>... params) {
        Class<?> clazz = ReflectionUtil.getClass(className);

        return ReflectionUtil.getMethodHandle(clazz, methodName, params);
    }

    public static MethodHandle getMethodHandle(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = ReflectionUtil.getMethod(clazz, methodName, params);

            return ReflectionUtil.LOOKUP.unreflect(method);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find MethodHandle");
        }
    }

    public static FieldAccessor getFieldAccessor(Class<?> clazz, String fieldName) {
        Field field = ReflectionUtil.getField(clazz, fieldName);

        return new FieldAccessor(clazz, field, ReflectionUtil.UNSAFE);
    }

    public static FieldAccessor getFieldAccessor(Class<?> clazz, Field field) {
        return new FieldAccessor(clazz, field, ReflectionUtil.UNSAFE);
    }

    public static FieldAccessor getFieldAccessor(String className, String fieldName) {
        Class<?> clazz = ReflectionUtil.getClass(className);

        return ReflectionUtil.getFieldAccessor(clazz, fieldName);
    }

    public static FieldAccessor getFieldAccessor(@Nullable Class<?> clazz, long fieldOffset, boolean isStatic) {
        return new FieldAccessor(clazz, ReflectionUtil.UNSAFE, isStatic, fieldOffset);
    }
}
