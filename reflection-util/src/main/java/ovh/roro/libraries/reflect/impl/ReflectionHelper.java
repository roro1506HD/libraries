package ovh.roro.libraries.reflect.impl;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.reflect.api.FieldAccessor;
import ovh.roro.libraries.reflect.impl.accessor.ConstantFieldAccessor;
import ovh.roro.libraries.reflect.impl.accessor.LazyFieldAccessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class ReflectionHelper {

    public static ReflectionHelper INSTANCE = new ReflectionHelper();

    private final MethodHandles.Lookup lookup;

    public ReflectionHelper() {
        this.lookup = MethodHandles.lookup();
    }

    public Class<?> getClass(String classPath) {
        try {
            return Class.forName(classPath);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find class", ex);
        }
    }

    public Field getField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find field", ex);
        }
    }

    public Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, params);
            method.setAccessible(true);

            return method;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find method", ex);
        }
    }

    public MethodHandle getMethodHandle(String className, String methodName, Class<?>... params) {
        Class<?> clazz = this.getClass(className);

        return this.getMethodHandle(clazz, methodName, params);
    }

    public MethodHandle getMethodHandle(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = this.getMethod(clazz, methodName, params);

            return this.lookup.unreflect(method);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find MethodHandle", ex);
        }
    }

    public FieldAccessor getFieldAccessor(Class<?> clazz, String fieldName) {
        Field field = this.getField(clazz, fieldName);

        return this.getFieldAccessor(clazz, field);
    }

    public FieldAccessor getFieldAccessorLazy(Class<?> clazz, String fieldName) {
        Field field = this.getField(clazz, fieldName);

        return this.getFieldAccessorLazy(clazz, field);
    }

    public FieldAccessor getFieldAccessor(String className, String fieldName) {
        Class<?> clazz = this.getClass(className);

        return this.getFieldAccessor(clazz, fieldName);
    }

    public FieldAccessor getFieldAccessorLazy(String className, String fieldName) {
        Class<?> clazz = this.getClass(className);

        return this.getFieldAccessorLazy(clazz, fieldName);
    }

    public FieldAccessor getFieldAccessor(Class<?> clazz, Field field) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, this.lookup);

            VarHandle varHandle;
            if (Modifier.isStatic(field.getModifiers())) {
                varHandle = lookup.findStaticVarHandle(field.getDeclaringClass(), field.getName(), field.getType());
            } else {
                varHandle = lookup.findVarHandle(field.getDeclaringClass(), field.getName(), field.getType());
            }

            return new ConstantFieldAccessor(varHandle);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find VarHandle", ex);
        }
    }

    public FieldAccessor getFieldAccessorLazy(Class<?> clazz, Field field) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, this.lookup);

            return new LazyFieldAccessor(lookup, field);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find VarHandle", ex);
        }
    }
}
