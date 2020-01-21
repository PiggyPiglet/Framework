/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

package me.piggypiglet.framework.utils.number;

import me.piggypiglet.framework.utils.lambda.CheckedFunction;

import java.lang.reflect.Constructor;

public enum Primitives {
    BOOLEAN(Boolean.class, c -> c.newInstance(false)),
    BYTE(Byte.class, c -> c.newInstance("0")),
    CHARACTER(Character.class, c -> c.newInstance('x')),
    SHORT(Short.class, c -> c.newInstance(0)),
    INTEGER(Integer.class, c -> c.newInstance(0)),
    LONG(Long.class, c -> c.newInstance(0L)),
    DOUBLE(Double.class, c -> c.newInstance(0.0d)),
    FLOAT(Float.class, c -> c.newInstance(0.0f)),
    UNKNOWN(null, null);

    private final Class clazz;
    private final CheckedFunction<Constructor, ?> defaultInstance;

    Primitives(final Class clazz, CheckedFunction<Constructor, ?> defaultInstance) {
        this.clazz = clazz;
        this.defaultInstance = defaultInstance;
    }

    /**
     * Initialise a default instance of a primitive wrapper.
     * <ul>
     *  <li>Boolean: false
     *  <li>Number: 0
     *  <li>Character: 0
     * </ul>
     * @return Wrapper instance with populated value field
     * @throws Exception Reflection exception
     */
    public Object getDefaultInstance() throws Exception {
        return defaultInstance.apply(clazz.getConstructors()[0]);
    }

    /**
     * Find a wrapper from a primitive class
     * @param clazz primitive class
     * @return Wrapper Primitives enum
     */
    public static Primitives fromClass(Class clazz) {
        for (Primitives primitive : values()) {
            if (primitive.clazz == NumberUtils.primitiveToWrapper(clazz)) {
                return primitive;
            }
        }

        return UNKNOWN;
    }
}
