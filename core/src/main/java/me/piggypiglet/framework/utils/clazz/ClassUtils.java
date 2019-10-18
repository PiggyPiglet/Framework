/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.utils.clazz;

import java.util.HashMap;
import java.util.Map;

public final class ClassUtils {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

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
     * Get a generic on an interface a class is imlementing generic, a bit iffy, might not work in all scenarios
     * @param clazz Class to get the generic from
     * @param <T> Type
     * @return Class of the generic type, with the type as the classes generic
     * @throws GenericException when it can't find the generic on the class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getImplementedGeneric(Class clazz) throws GenericException {
        try {
            return (Class<T>) Class.forName(clazz.getGenericInterfaces()[0].getTypeName().split("<")[1].replace(">", ""));
        } catch (Exception e) {
            throw new GenericException("Can't find generic on " + clazz.getName());
        }
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