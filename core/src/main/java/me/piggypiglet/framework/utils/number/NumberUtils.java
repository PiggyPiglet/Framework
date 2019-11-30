package me.piggypiglet.framework.utils.number;

import java.util.HashMap;
import java.util.Map;

public final class NumberUtils {
    public static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
    }

    /**
     * Convert a primitive to it's wrapper, with a default value.<br>
     * Kudos to devs behind apache commons lang, they're the original author of this method.<br>
     * https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/ClassUtils.java
     * @param clazz primitive
     * @return Wrapper
     */
    public static Class<?> primitiveToWrapper(final Class<?> clazz) {
        Class<?> convertedClass = clazz;

        if (clazz != null && clazz.isPrimitive()) {
            convertedClass = PRIMITIVE_WRAPPER_MAP.get(clazz);
        }

        return convertedClass;
    }

    /**
     * Convert a wrapper into a primitive with a default value, likely either 0, false, or 'a'.
     * @param clazz Wrapper class
     * @return Primitive object
     * @throws Exception Unlikely, but possible
     */
    public static Object wrapperToPrimitive(final Class<?> clazz) throws Exception {
        return Primitives.fromClass(clazz).getDefaultInstance();
    }
}
