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

package me.piggypiglet.framework.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static me.piggypiglet.framework.json.ParserConstants.*;

@SuppressWarnings("unused")
public final class JsonParser {
    private final boolean nullableValues;
    private final Gson gson;
    private final JsonElement data;
    private final Map<String, Object> map;
    private final Map<String, Object> flattenedMap;

    private final Function<JsonElement, List<JsonElement>> listGetter;
    private final Function<JsonElement, Map<?, ?>> mapGetter;
    private final Function<JsonElement, List<JsonParser>> jsonListGetter;

    JsonParser(final boolean nullableValues, final Gson gson,
                       final JsonElement data, final Map<String, Object> map,
                       final Map<String, Object> flattenedMap) {
        this.nullableValues = nullableValues;
        this.gson = gson;
        this.data = data;
        this.map = map;
        this.flattenedMap = flattenedMap;

        listGetter = element -> LIST_GETTER.apply(gson, element);
        mapGetter = element -> MAP_GETTER.apply(gson, element);
        jsonListGetter = element -> JSON_LIST_GETTER.apply(this, element);
    }

    @NotNull
    public static JsonParser of(@NotNull final String json) {
        return builder(json).build();
    }

    @NotNull
    public static Builder<JsonParser> builder(@NotNull final String json) {
        return new Builder<>(json);
    }

    public static class Builder<T> extends AbstractBuilder<JsonParser, T> {
        private final String json;

        private boolean nullableValues = true;
        private Gson gson = new Gson();

        private Builder(final String json) {
            this.json = json;
        }

        @NotNull
        public Builder<T> nullableValues(final boolean value) {
            nullableValues = value;
            return this;
        }

        @NotNull
        public Builder<T> gson(@NotNull final Gson value) {
            gson = value;
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected JsonParser provideBuild() {
            final JsonElement data = PARSER.parse(json);
            final Map<String, Object> map = ImmutableMap.copyOf((Map<String, Object>) MAP_GETTER.apply(gson, data));
            final Map<String, Object> flattenedMap = ImmutableMap.copyOf(Maps.flatten(map));

            return new JsonParser(nullableValues, gson, data, map, flattenedMap);
        }
    }

    public boolean isNullableValues() {
        return nullableValues;
    }

    @NotNull
    public Gson getGson() {
        return gson;
    }

    @NotNull
    public JsonElement getData() {
        return data;
    }

    @NotNull
    public Map<String, Object> getMap() {
        return map;
    }

    @NotNull
    public Map<String, Object> getFlattenedMap() {
        return flattenedMap;
    }

    @Nullable
    public String getString(@NotNull final String path) {
        return findFromPath(path, STRING_GETTER, provideDef(STRING_DEFAULT));
    }

    @NotNull
    public String getString(@NotNull final String path, @NotNull final String def) {
        return findFromPath(path, STRING_GETTER, def);
    }

    @Nullable
    public Character getCharacter(@NotNull final String path) {
        return findFromPath(path, CHARACTER_GETTER, provideDef(CHARACTER_DEFAULT));
    }

    public char getCharacter(@NotNull final String path, final char def) {
        return findFromPath(path, CHARACTER_GETTER, def);
    }

    @Nullable
    public Byte getByte(@NotNull final String path) {
        return findFromPath(path, BYTE_GETTER, provideDef(BYTE_DEFAULT));
    }

    public byte getByte(@NotNull final String path, final byte def) {
        return findFromPath(path, BYTE_GETTER, def);
    }

    @Nullable
    public Boolean getBoolean(@NotNull final String path) {
        return findFromPath(path, BOOLEAN_GETTER, provideDef(BOOLEAN_DEFAULT));
    }

    public boolean getBoolean(@NotNull final String path, final boolean def) {
        return findFromPath(path, BOOLEAN_GETTER, def);
    }

    @Nullable
    public Short getShort(@NotNull final String path) {
        return findFromPath(path, SHORT_GETTER, provideDef(NUMBER_DEFAULT.shortValue()));
    }

    public short getShort(@NotNull final String path, final short def) {
        return findFromPath(path, SHORT_GETTER, def);
    }

    @Nullable
    public Integer getInteger(@NotNull final String path) {
        return findFromPath(path, INTEGER_GETTER, provideDef(NUMBER_DEFAULT.intValue()));
    }

    public int getInteger(@NotNull final String path, final int def) {
        return findFromPath(path, INTEGER_GETTER, def);
    }

    @Nullable
    public Float getFloat(@NotNull final String path) {
        return findFromPath(path, FLOAT_GETTER, provideDef(NUMBER_DEFAULT.floatValue()));
    }

    public float getFloat(@NotNull final String path, final float def) {
        return findFromPath(path, FLOAT_GETTER, def);
    }

    @Nullable
    public Double getDouble(@NotNull final String path) {
        return findFromPath(path, DOUBLE_GETTER, provideDef(NUMBER_DEFAULT.doubleValue()));
    }

    public double getDouble(@NotNull final String path, final double def) {
        return findFromPath(path, DOUBLE_GETTER, def);
    }

    @Nullable
    public Long getLong(@NotNull final String path) {
        return findFromPath(path, LONG_GETTER, provideDef(NUMBER_DEFAULT.longValue()));
    }

    public long getLong(@NotNull final String path, final long def) {
        return findFromPath(path, LONG_GETTER, def);
    }

    @Nullable
    public List<JsonElement> getList(@NotNull final String path) {
        return findFromPath(path, listGetter, provideDef(LIST_DEFAULT));
    }

    @NotNull
    public List<JsonElement> getList(@NotNull final String path, @NotNull final List<JsonElement> def) {
        return findFromPath(path, listGetter, def);
    }

    @Nullable
    public List<JsonParser> getJsonList(@NotNull final String path) {
        return findFromPath(path, jsonListGetter, provideDef(JSON_LIST_DEFAULT));
    }

    @NotNull
    public List<JsonParser> getJsonList(@NotNull final String path, @NotNull final List<JsonParser> def) {
        return findFromPath(path, jsonListGetter, def);
    }

    @Nullable
    public Map<?, ?> getMap(@NotNull final String path) {
        return findFromPath(path, mapGetter, provideDef(MAP_DEFAULT));
    }

    @NotNull
    public Map<?, ?> getMap(@NotNull final String path, @NotNull final Map<?, ?> def) {
        return findFromPath(path, mapGetter, def);
    }

    @Nullable
    private <T> T provideDef(@NotNull final T def) {
        return nullableValues ? null : def;
    }

    private <T> T findFromPath(@NotNull final String path, @NotNull final Function<JsonElement, T> getter,
                               @Nullable final T def) {
        JsonElement data = this.data;

        if (data == null || data.isJsonNull()) {
            return def;
        }

        for (final String key : PATH.split(path)) {
            if (!data.isJsonObject()) {
                return def;
            }

            data = data.getAsJsonObject().get(key);
        }

        return data == null ? def : getter.apply(data);
    }
}