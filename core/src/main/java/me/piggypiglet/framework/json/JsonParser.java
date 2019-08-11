package me.piggypiglet.framework.json;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@SuppressWarnings("unused")
public final class JsonParser {
    private Map<String, Object> items;

    @SuppressWarnings("unchecked")
    public JsonParser(String json) {
        items = new Gson().fromJson(json, LinkedTreeMap.class);
    }

    private JsonParser(Map<String, Object> items) {
        this.items = items;
    }

    public Object get(String path) {
        Object object = items.getOrDefault(path, null);

        if (path.contains(".") && !path.startsWith(".") && !path.endsWith(".")) {
            String[] areas = path.split("\\.");
            object = items.getOrDefault(areas[0], null);

            if (areas.length >= 2 && object != null) {
                object = getBuriedObject(areas);
            }
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    public JsonParser getJsonSection(String path) {
        Object object = get(path);

        if (isConfigSection(object)) {
            return new JsonParser((Map<String, Object>) object);
        }

        return null;
    }

    public String getString(String path) {
        Object object = get(path);

        if (object instanceof String) {
            return (String) object;
        }

        return null;
    }

    public Integer getInt(String path) {
        Double d = getDouble(path);
        return d == null ? null : d.intValue();
    }

    public Long getLong(String path) {
        Double d = getDouble(path);
        return d == null ? null : d.longValue();
    }

    public Double getDouble(String path) {
        Object object = get(path);

        if (object instanceof Double) {
            return (double) object;
        }

        return null;
    }

    public Boolean getBoolean(String path) {
        Object object = get(path);

        if (object instanceof Boolean) {
            return (boolean) object;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public final List<String> getStringList(String path) {
        Object object = get(path);

        if (object instanceof List<?>) {
            for (Object obj : (List<?>) object) {
                if (obj instanceof String) {
                    return (List<String>) object;
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public final List<JsonParser> getJsonList(String path) {
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

        return null;
    }

    public final List<?> getList(String path) {
        Object object = get(path);

        if (object instanceof List<?>) {
            return (List<?>) object;
        }

        return null;
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

    @SuppressWarnings("unchecked")
    private Object getBuriedObject(String[] keys) {
        int i = 1;
        Map<String, Object> endObject = (Map<String, Object>) items.get(keys[0]);

        while (instanceOfMap(endObject.get(keys[i]))) {
            endObject = (Map<String, Object>) endObject.get(keys[i++]);
        }

        return endObject.get(keys[i]);
    }

    private boolean instanceOfMap(Object object) {
        if (object instanceof Map) {
            Set<?> keys = ((Map<?, ?>) object).keySet();

            return keys.size() >= 1 && keys.toArray()[0] instanceof String;
        }

        return false;
    }
}