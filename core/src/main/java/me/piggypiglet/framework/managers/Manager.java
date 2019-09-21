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

package me.piggypiglet.framework.managers;

import me.piggypiglet.framework.managers.objects.KeyFunction;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public abstract class Manager<S> {
    protected KeyTypeInfo keyTypes;

    /**
     * Configure acceptable key types
     * @param builder Instance of KeyTypeInfo builder
     * @return Built KeyTypeInfo
     */
    protected abstract KeyTypeInfo configure(KeyTypeInfo.Builder builder);

    /**
     * Handle initial population here
     */
    protected void populate() {};

    /**
     * Populate the manager.
     */
    public void setup() {
        keyTypes = configure(KeyTypeInfo.builder());
        populate();
    }

    /**
     * Handle inserting an item into underlying data structures
     * @param item Item to insert
     */
    protected abstract void insert(S item);

    /**
     * Handle deleting an item from underlying data structures
     * @param item Item to delete
     */
    protected abstract void delete(S item);

    /**
     * Retrieve values from any data structures in this manager
     * @return Collection of values
     */
    protected abstract Collection<S> retrieveAll();

    /**
     * Store an item to this manager
     * @param item Item
     */
    public void add(S item) {
        insert(item);
    }

    /**
     * Remove an item from this manager
     * @param item Item
     */
    public void remove(S item) {
        delete(item);
    }

    /**
     * Get everything stored in this manager.
     * @return Collection of values
     */
    public Collection<S> getAll() {
        return retrieveAll();
    }

    /**
     * Get a value based on configured key types
     * @param key Key value
     * @param <T> Key type
     * @return Value
     */
    @SuppressWarnings("unchecked")
    public <T> S get(T key) {
        for (KeyFunction<?> f : keyTypes.getKeys()) {
            Function<T, Object> function = (Function<T, Object>) f.getFunction();

            if (f.isClazz()) {
                if (f.getType() == key.getClass()) return (S) function.apply(key);
            } else {
                if (Arrays.asList(key.getClass().getInterfaces()).contains(f.getType())) return (S) function.apply(key);
            }
        }

        return null;
    }
}