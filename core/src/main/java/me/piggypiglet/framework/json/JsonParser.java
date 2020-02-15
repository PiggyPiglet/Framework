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
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static me.piggypiglet.framework.json.ParserConstants.*;

/**
 * GSON JsonParser wrapper that implements a seemingly flattened
 * key-value system, with . as the nested element path separator.
 * Suppose the following JSON is supplied,
 * <p>
 * {
 * "meta": {
 * "name: "Piggy",
 * "age": "118"
 * },
 * <p>
 * "active": true
 * }
 * <p>
 * Retrieving the name value, would be as simple as invoking
 * JsonParser#getString("meta.name");
 * <p>
 * While the end result mimics that of a flattened map, the underlying
 * data source is not actually flattened. Path resolving is done when
 * requested, not before. This means that this utility should not be
 * used in performance critical circumstances. Consider object mapping
 * instead.
 */
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

    /**
     * Package private constructor used by JsonParser.Builder for
     * instantiation, and ParserConstants for JsonParser list building.
     * All the parameters requested in the constructor are simply
     * wired to their corresponding fields, like any regular POJO
     * constructor. Additionally however, the listGetter, mapGetter,
     * and jsonListGetter fields are also populated here, as they
     * require the GSON instance, or the JsonParser instance.
     *
     * @param nullableValues Should null be returned, or a constant
     *                       default value.
     * @param gson           GSON instance
     * @param data           Deserialized json string
     * @param map            Map interpretation of the data
     * @param flattenedMap   Flattened version of map
     */
    JsonParser(final boolean nullableValues, @NotNull final Gson gson,
               @NotNull final JsonElement data, @NotNull final Map<String, Object> map,
               @NotNull final Map<String, Object> flattenedMap) {
        this.nullableValues = nullableValues;
        this.gson = gson;
        this.data = data;
        this.map = map;
        this.flattenedMap = flattenedMap;

        listGetter = element -> LIST_GETTER.apply(gson, element);
        mapGetter = element -> MAP_GETTER.apply(gson, element);
        jsonListGetter = element -> JSON_LIST_GETTER.apply(this, element);
    }

    /**
     * Create a barebones JsonParser instance, with the provided json string
     * and default values. Default values being a plain GSON instance,
     * and null values allowed.
     *
     * @param json Serialized JSON String
     * @return Loaded JsonParser instance
     */
    @NotNull
    public static JsonParser of(@NotNull final String json) {
        return builder(json).build();
    }

    /**
     * Create a JsonParser Builder instance, with the provided initial
     * serialized json.
     *
     * @param json Serialized JSON String
     * @return JsonParser Builder
     */
    @NotNull
    public static Builder<JsonParser> builder(@NotNull final String json) {
        return new Builder<>(json);
    }

    /**
     * Create a JsonParser Builder instance, with the provided initial
     * serialized json, that returns a custom type when built.
     *
     * @param json  Serialized JSON String
     * @param build Build method logic
     * @param <R>   Return type generic
     * @return JsonParser Builder
     */
    @NotNull
    public static <R> Builder<R> builder(@NotNull final String json, @NotNull final Function<JsonParser, R> build) {
        return BuilderUtils.customBuilder(new Builder<>(json), build);
    }

    /**
     * JsonParser construction utility. Currently houses the optional
     * nullability option, and the custom gson instance.
     *
     * @param <T> Build type
     */
    public static class Builder<T> extends AbstractBuilder<JsonParser, T> {
        private final String json;

        private boolean nullableValues = true;
        private Gson gson = GSON_DEFAULT;

        private Builder(final String json) {
            this.json = json;
        }

        /**
         * Should this builder be able to return null if specified
         * path's don't exist? If set to true, null will be returned
         * in that scenario. If not, constant defaults set in
         * ParserConstants will be supplied.
         *
         * @param value Boolean value
         * @return Builder instance
         */
        @NotNull
        public Builder<T> nullableValues(final boolean value) {
            nullableValues = value;
            return this;
        }

        /**
         * Provide a custom GSON instance if you require specific
         * options to be set in order for your serialized json
         * to be correctly deserialized.
         *
         * @param value GSON instance
         * @return Builder instance
         */
        @NotNull
        public Builder<T> gson(@NotNull final Gson value) {
            gson = value;
            return this;
        }

        /**
         * Compiles the deserialized JSON and optional values into a
         * JsonParser. Serialized JSON is deserialized with google's
         * JsonParser class, JsonParser#map is an immutable copy of a
         * map generated with ParserConstants#MAP_GETTER, and
         * JsonParser#flattenedMap is #map flattened with Maps#flatten,
         * and then made immutable.
         *
         * @return Configured JsonParser instance
         */
        @SuppressWarnings("unchecked")
        @Override
        protected JsonParser provideBuild() {
            final JsonElement data = PARSER.parse(json);
            final Map<String, Object> map = ImmutableMap.copyOf((Map<String, Object>) MAP_GETTER.apply(gson, data));
            final Map<String, Object> flattenedMap = ImmutableMap.copyOf(Maps.flatten(map));

            return new JsonParser(nullableValues, gson, data, map, flattenedMap);
        }
    }

    /**
     * Is null being returned for non-existent paths, or
     * constant default values.
     *
     * @return Boolean value
     */
    public boolean isNullableValues() {
        return nullableValues;
    }

    /**
     * Get the GSON instance being used by this JsonParser.
     * If one wasn't set manually in the builder, the default
     * will be returned, ParserConstants.GSON_DEFAULT.
     *
     * @return GSON Instance
     */
    @NotNull
    public Gson getGson() {
        return gson;
    }

    /**
     * Get the underlying JsonElement instance. This is the
     * deserialized form of the provided JSON string, and is
     * what is used for all of the defined value getters.
     *
     * @return JsonElement instance
     */
    @NotNull
    public JsonElement getData() {
        return data;
    }

    /**
     * Get the map interpretation of the JsonElement data.
     * It's important to note that this is functionally
     * different from the semantics this class ensures.
     * Essentially, this means that nested elements will
     * no longer be reachable via keys (paths) separated with
     * periods. If you wish to retain this path functionality,
     * use the flattened map interpretation.
     * <p>
     * This map is immutable.
     *
     * @return Map&lt;String, Object&gt;
     */
    @NotNull
    public Map<String, Object> getMap() {
        return map;
    }

    /**
     * Get the flattened version of JsonParser#getMap. This should
     * be functionally equivalent to this class's path splitting
     * semantics, except with the Object generic, instead of explicit
     * type getters.
     *
     * @return Map&lt;String, Object&gt;
     */
    @NotNull
    public Map<String, Object> getFlattenedMap() {
        return flattenedMap;
    }

    /**
     * Get a String located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.STRING_DEFAULT
     * will be returned.
     *
     * @param path Path of the string
     * @return String
     */
    @Nullable
    public String getString(@NotNull final String path) {
        return findFromPath(path, STRING_GETTER, provideDef(STRING_DEFAULT));
    }

    /**
     * Get a String located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     * Runtime assertion isn't performed regarding the nullness of the
     * default value, this is the programmer's prerogative.
     *
     * @param path Path of the string
     * @param def  Default value
     * @return String
     */
    @NotNull
    public String getString(@NotNull final String path, @NotNull final String def) {
        return findFromPath(path, STRING_GETTER, def);
    }

    /**
     * Get a Character located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.CHARACTER_DEFAULT
     * will be returned.
     *
     * @param path Path of the character
     * @return Character
     */
    @Nullable
    public Character getCharacter(@NotNull final String path) {
        return findFromPath(path, CHARACTER_GETTER, provideDef(CHARACTER_DEFAULT));
    }

    /**
     * Get a char located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the char
     * @param def  Default value
     * @return char
     */
    public char getCharacter(@NotNull final String path, final char def) {
        return findFromPath(path, CHARACTER_GETTER, def);
    }

    /**
     * Get a Byte located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.BYTE_DEFAULT
     * will be returned.
     *
     * @param path Path of the byte
     * @return Byte
     */
    @Nullable
    public Byte getByte(@NotNull final String path) {
        return findFromPath(path, BYTE_GETTER, provideDef(BYTE_DEFAULT));
    }

    /**
     * Get a byte located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the byte
     * @param def  Default value
     * @return byte
     */
    public byte getByte(@NotNull final String path, final byte def) {
        return findFromPath(path, BYTE_GETTER, def);
    }

    /**
     * Get a Boolean located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.BOOLEAN_DEFAULT
     * will be returned.
     *
     * @param path Path of the boolean
     * @return Boolean
     */
    @Nullable
    public Boolean getBoolean(@NotNull final String path) {
        return findFromPath(path, BOOLEAN_GETTER, provideDef(BOOLEAN_DEFAULT));
    }

    /**
     * Get a boolean located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the boolean
     * @param def  Default value
     * @return boolean
     */
    public boolean getBoolean(@NotNull final String path, final boolean def) {
        return findFromPath(path, BOOLEAN_GETTER, def);
    }

    /**
     * Get a Short located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.NUMBER_DEFAULT
     * will be returned.
     *
     * @param path Path of the short
     * @return Short
     */
    @Nullable
    public Short getShort(@NotNull final String path) {
        return findFromPath(path, SHORT_GETTER, provideDef(NUMBER_DEFAULT.shortValue()));
    }

    /**
     * Get a short located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the short
     * @param def  Default value
     * @return short
     */
    public short getShort(@NotNull final String path, final short def) {
        return findFromPath(path, SHORT_GETTER, def);
    }

    /**
     * Get an Integer located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.NUMBER_DEFAULT
     * will be returned.
     *
     * @param path Path of the integer
     * @return Integer
     */
    @Nullable
    public Integer getInteger(@NotNull final String path) {
        return findFromPath(path, INTEGER_GETTER, provideDef(NUMBER_DEFAULT.intValue()));
    }

    /**
     * Get an int located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the int
     * @param def  Default value
     * @return int
     */
    public int getInteger(@NotNull final String path, final int def) {
        return findFromPath(path, INTEGER_GETTER, def);
    }

    /**
     * Get a Float located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.NUMBER_DEFAULT
     * will be returned.
     *
     * @param path Path of the float
     * @return Float
     */
    @Nullable
    public Float getFloat(@NotNull final String path) {
        return findFromPath(path, FLOAT_GETTER, provideDef(NUMBER_DEFAULT.floatValue()));
    }

    /**
     * Get a float located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the float
     * @param def  Default value
     * @return float
     */
    public float getFloat(@NotNull final String path, final float def) {
        return findFromPath(path, FLOAT_GETTER, def);
    }

    /**
     * Get a Double located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.NUMBER_DEFAULT
     * will be returned.
     *
     * @param path Path of the double
     * @return Double
     */
    @Nullable
    public Double getDouble(@NotNull final String path) {
        return findFromPath(path, DOUBLE_GETTER, provideDef(NUMBER_DEFAULT.doubleValue()));
    }

    /**
     * Get a double located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the double
     * @param def  Default value
     * @return double
     */
    public double getDouble(@NotNull final String path, final double def) {
        return findFromPath(path, DOUBLE_GETTER, def);
    }

    /**
     * Get a Long located at the specified path. If the path doesn't
     * resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.NUMBER_DEFAULT
     * will be returned.
     *
     * @param path Path of the long
     * @return Long
     */
    @Nullable
    public Long getLong(@NotNull final String path) {
        return findFromPath(path, LONG_GETTER, provideDef(NUMBER_DEFAULT.longValue()));
    }

    /**
     * Get a long located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     *
     * @param path Path of the long
     * @param def  Default value
     * @return long
     */
    public long getLong(@NotNull final String path, final long def) {
        return findFromPath(path, LONG_GETTER, def);
    }

    /**
     * Get a List of JsonElements located at the specified path. If the path
     * doesn't resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.LIST_DEFAULT
     * will be returned.
     *
     * @param path Path of the list
     * @return List of JsonElements
     */
    @Nullable
    public List<JsonElement> getList(@NotNull final String path) {
        return findFromPath(path, listGetter, provideDef(LIST_DEFAULT));
    }

    /**
     * Get a List of JsonElements located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     * Runtime assertion isn't performed regarding the nullness of the
     * default value, this is the programmer's prerogative.
     *
     * @param path Path of the list
     * @param def  Default value
     * @return List of JsonElements
     */
    @NotNull
    public List<JsonElement> getList(@NotNull final String path, @NotNull final List<JsonElement> def) {
        return findFromPath(path, listGetter, def);
    }

    /**
     * Get a List of JsonParsers located at the specified path. If the path
     * doesn't resolve, one of two things will happen. If nullableValues is set
     * to true, null will be returned. If not, ParserConstants.JSON_LIST_DEFAULT
     * will be returned.
     *
     * @param path Path of the list
     * @return List of JsonParsers
     */
    @Nullable
    public List<JsonParser> getJsonList(@NotNull final String path) {
        return findFromPath(path, jsonListGetter, provideDef(JSON_LIST_DEFAULT));
    }

    /**
     * Get a List of JsonParsers located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     * Runtime assertion isn't performed regarding the nullness of the
     * default value, this is the programmer's prerogative.
     *
     * @param path Path of the list
     * @param def  Default value
     * @return List of JsonParsers
     */
    @NotNull
    public List<JsonParser> getJsonList(@NotNull final String path, @NotNull final List<JsonParser> def) {
        return findFromPath(path, jsonListGetter, def);
    }

    /**
     * Get a Map located at the specified path. If the path doesn't resolve,
     * one of two things will happen. If nullableValues is set to true, null
     * will be returned. If not, ParserConstants.JSON_LIST_DEFAULT will be
     * returned.
     *
     * @param path Path of the map
     * @return Map
     */
    @Nullable
    public Map<?, ?> getMap(@NotNull final String path) {
        return findFromPath(path, mapGetter, provideDef(MAP_DEFAULT));
    }

    /**
     * Get a Map located at the specified path. If the path doesn't
     * resolve, the provided default value will be returned instead.
     * Runtime assertion isn't performed regarding the nullness of the
     * default value, this is the programmer's prerogative.
     *
     * @param path Path of the map
     * @param def  Default value
     * @return Map
     */
    @NotNull
    public Map<?, ?> getMap(@NotNull final String path, @NotNull final Map<?, ?> def) {
        return findFromPath(path, mapGetter, def);
    }

    /**
     * Quick logic util to decide on whether a default or
     * null should be returned. If nullableValues is set to
     * true, null will be used, otherwise the provided default
     * value will.
     *
     * @param def Default value
     * @param <T> Value type generic
     * @return End result (null or default)
     */
    @Nullable
    private <T> T provideDef(@NotNull final T def) {
        return nullableValues ? null : def;
    }

    /**
     * Recurses through the JsonElement data following a specific
     * path. If the path is, or resolves to null, the provided (nullable) default
     * value will be returned. If the path resolves to a legitimate; non-null
     * value, the getter will be invoked to retrieve the requested type.
     *
     * @param path   Path of the object
     * @param getter Getter function to invoke on JsonElement, to get the originally
     *               requested type.
     * @param def    Nullable default value, returned if path is, or resolves to null.
     * @param <T>    Return type generic
     * @return Object at path, the default value, or null.
     */
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