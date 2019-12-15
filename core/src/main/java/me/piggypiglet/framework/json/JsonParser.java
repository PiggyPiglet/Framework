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

package me.piggypiglet.framework.json;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JsonParser class, used for parsing json, as the name suggests. Use . as a path separator when receiving nested objects, for example
 * {
 *     "test": {
 *         "key": "value"
 *     }
 * }
 * In this json, key could be received as JsonParser#getString("test.key");
 */
@SuppressWarnings("unused")
public final class JsonParser {
    private Map<String, Object> items;

    /**
     * Create a JsonParser instance from raw json data as a string
     * @param json JSON String
     */
    @SuppressWarnings("unchecked")
    public JsonParser(String json) {
        items = new Gson().fromJson(json, LinkedTreeMap.class);
    }

    /**
     * Load JsonParser with already-decoded json data.
     * @param items Map of json data
     */
    public JsonParser(Map<String, Object> items) {
        this.items = items;
    }

    /**
     * Get an object at a specific path
     * @param path Path to find the object at
     * @return Object
     */
    public Object get(String path) {
        return Maps.recursiveGet(items, path);
    }

    /**
     * Get a section and turn it into a JsonParser instance.
     * @param path Path the json section can be found at
     * @return JsonParser
     */
    @SuppressWarnings("unchecked")
    public JsonParser getJsonSection(String path) {
        Object object = get(path);

        if (isConfigSection(object)) {
            return new JsonParser((Map<String, Object>) object);
        }

        return null;
    }

    /**
     * Get a string at a specific path
     * @param path Path of the string
     * @return String
     */
    public String getString(String path) {
        Object object = get(path);

        if (object instanceof String) {
            return (String) object;
        }

        return null;
    }

    /**
     * Get an Integer at a specific path
     * @param path Path of the integer
     * @return Integer
     */
    public Integer getInt(String path) {
        Double d = getDouble(path);
        return d == null ? null : d.intValue();
    }

    /**
     * Get a long at a specific path
     * @param path Path of the Long
     * @return Long
     */
    public Long getLong(String path) {
        Double d = getDouble(path);
        return d == null ? null : d.longValue();
    }

    /**
     * Get a Double at a specific path
     * @param path Path of the Double
     * @return Double
     */
    public Double getDouble(String path) {
        Object object = get(path);

        if (object instanceof Double) {
            return (double) object;
        }

        return null;
    }

    /**
     * Get a boolean at a specific path
     * @param path Path of the boolean
     * @return Boolean
     */
    public Boolean getBoolean(String path) {
        Object object = get(path);

        if (object instanceof Boolean) {
            return (boolean) object;
        }

        return null;
    }

    /**
     * Get a list of strings at a specific path
     * @param path Path of the list
     * @return List of Strings
     */
    public List<String> getStringList(String path) {
        return getList(path);
    }

    /**
     * Get a list of sections, parsed as JsonParsers.
     * @param path Path of the list
     * @return List of JsonParsers
     */
    @SuppressWarnings("unchecked")
    public List<JsonParser> getJsonList(String path) {
        Object object = get(path);

        if (object instanceof List<?>) {
            List<JsonParser> jsons = new ArrayList<>();

            for (Object obj : (List<?>) object) {
                if (isConfigSection(obj)) {
                    jsons.add(new JsonParser((Map<String, Object>) obj));
                }
            }

            if (jsons.size() >= 1) {
                return jsons;
            }
        }

        return new ArrayList<>();
    }

    /**
     * Get a list without knowing the type of the content
     * @param path Path of the list
     * @return List
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String path) {
        Object object = get(path);

        if (object instanceof List) {
            return (List<T>) object;
        }

        return new ArrayList<>();
    }

    public Map<String, Object> getAll() {
        return items;
    }

    private boolean isConfigSection(Object object) {
        if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;

            if (map.size() >= 1) {
                return map.keySet().toArray()[0] instanceof String && map.values().toArray()[0] != null;
            }
        }

        return false;
    }
}