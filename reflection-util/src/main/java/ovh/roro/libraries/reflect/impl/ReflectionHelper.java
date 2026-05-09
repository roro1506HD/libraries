package ovh.roro.libraries.reflect.impl;

import org.jetbrains.annotations.ApiStatus;
import ovh.roro.libraries.reflect.api.FieldAccessor;
import ovh.roro.libraries.reflect.impl.accessor.ConstantFieldAccessor;
import ovh.roro.libraries.reflect.impl.accessor.FlagsFieldAccessor;
import ovh.roro.libraries.reflect.impl.accessor.LazyFieldAccessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@ApiStatus.Internal
public final class ReflectionHelper {

    public static ReflectionHelper INSTANCE = new ReflectionHelper();

    private final MethodHandles.Lookup lookup;
    private final FieldAccessor flagsAccessor;
    private final MethodHandle memberNameConstructor;
    private final MethodHandle directFieldGetter;
    private final MethodHandle referenceKindGetter;

    public ReflectionHelper() {
        try {
            // Get this special lookup that has a trusted status, which allows to modify private fields
            // Final fields are handled by the FlagsFieldAccessor & AbstractFieldAccessor
            Field field = this.getField(MethodHandles.Lookup.class, "IMPL_LOOKUP");
            this.lookup = (MethodHandles.Lookup) field.get(null);
        } catch (Exception ex) {
            Module module = this.getClass().getModule();
            String moduleName = module.isNamed() ? module.getName() : "ALL-UNNAMED";
            String argumentName = "--add-opens java.base/java.lang.invoke=" + moduleName;

            throw new RuntimeException("Could not retrieve IMPL_LOOKUP, make sure you have the following starting flag: " + argumentName, ex);
        }

        try {
            Class<?> memberNameClass = Class.forName("java.lang.invoke.MemberName");

            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(memberNameClass, this.lookup);
            Field field = this.getField(memberNameClass, "flags");
            // Use a custom field accessor for the flags since we require that accessor to be up before other accessors
            this.flagsAccessor = new FlagsFieldAccessor(lookup, field);

            Constructor<?> constructor = memberNameClass.getConstructor(Field.class, boolean.class);
            this.memberNameConstructor = this.lookup.unreflectConstructor(constructor);

            this.directFieldGetter = this.getMethodHandle(MethodHandles.Lookup.class, "getDirectField", byte.class, Class.class, memberNameClass);
            this.referenceKindGetter = this.getMethodHandle(memberNameClass, "getReferenceKind");
        } catch (Exception ex) {
            throw new RuntimeException("Could not create environment required to reflecting fields", ex);
        }
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

            try {
                field.setAccessible(true);
            } catch (InaccessibleObjectException ignored) {
            }

            return field;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find field", ex);
        }
    }

    public Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, params);

            try {
                method.setAccessible(true);
            } catch (InaccessibleObjectException ignored) {
            }

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

        return this.getFieldAccessor(field);
    }

    public FieldAccessor getFieldAccessorLazy(Class<?> clazz, String fieldName) {
        Field field = this.getField(clazz, fieldName);

        return this.getFieldAccessorLazy(field);
    }

    public FieldAccessor getFieldAccessor(String className, String fieldName) {
        Class<?> clazz = this.getClass(className);

        return this.getFieldAccessor(clazz, fieldName);
    }

    public FieldAccessor getFieldAccessorLazy(String className, String fieldName) {
        Class<?> clazz = this.getClass(className);

        return this.getFieldAccessorLazy(clazz, fieldName);
    }

    public FieldAccessor getFieldAccessor(Field field) {
        try {
            return new ConstantFieldAccessor(field);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find VarHandle", ex);
        }
    }

    public FieldAccessor getFieldAccessorLazy(Field field) {
        try {
            return new LazyFieldAccessor(field);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Could not find VarHandle", ex);
        }
    }

    public MethodHandle unreflectGetter(Field field) throws Throwable {
        return this.unreflectField(field, false);
    }

    public MethodHandle unreflectSetter(Field field) throws Throwable {
        return this.unreflectField(field, true);
    }

    private MethodHandle unreflectField(Field f, boolean isSetter) throws Throwable {
        Object field = this.memberNameConstructor.invoke(f, isSetter);
        this.flagsAccessor.setInt(field, this.flagsAccessor.getInt(field) & ~Modifier.FINAL); // Remove final flag
        Object referenceKind = this.referenceKindGetter.invoke(field);
        Object invoke = this.directFieldGetter.invoke(this.lookup, referenceKind, f.getDeclaringClass(), field);
        return (MethodHandle) invoke;
    }
}
