package ovh.roro.libraries.reflect.api;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.reflect.impl.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@ApiStatus.NonExtendable
public interface ReflectionUtil {

    static Class<?> getClass(String classPath) {
        return ReflectionHelper.INSTANCE.getClass(classPath);
    }

    static Field getField(Class<?> clazz, String fieldName) {
        return ReflectionHelper.INSTANCE.getField(clazz, fieldName);
    }

    static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        return ReflectionHelper.INSTANCE.getMethod(clazz, methodName, params);
    }

    static MethodHandle getMethodHandle(String className, String methodName, Class<?>... params) {
        return ReflectionHelper.INSTANCE.getMethodHandle(className, methodName, params);
    }

    static MethodHandle getMethodHandle(Class<?> clazz, String methodName, Class<?>... params) {
        return ReflectionHelper.INSTANCE.getMethodHandle(clazz, methodName, params);
    }

    static FieldAccessor getFieldAccessor(Class<?> clazz, String fieldName) {
        return ReflectionHelper.INSTANCE.getFieldAccessor(clazz, fieldName);
    }

    static FieldAccessor getFieldAccessorLazy(Class<?> clazz, String fieldName) {
        return ReflectionHelper.INSTANCE.getFieldAccessorLazy(clazz, fieldName);
    }

    static FieldAccessor getFieldAccessor(String className, String fieldName) {
        return ReflectionHelper.INSTANCE.getFieldAccessor(className, fieldName);
    }

    static FieldAccessor getFieldAccessorLazy(String className, String fieldName) {
        return ReflectionHelper.INSTANCE.getFieldAccessorLazy(className, fieldName);
    }

    static FieldAccessor getFieldAccessor(Class<?> clazz, Field field) {
        return ReflectionHelper.INSTANCE.getFieldAccessor(clazz, field);
    }

    static FieldAccessor getFieldAccessorLazy(Class<?> clazz, Field field) {
        return ReflectionHelper.INSTANCE.getFieldAccessorLazy(clazz, field);
    }
}
