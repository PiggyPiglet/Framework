package me.piggypiglet.framework.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ParserConstants {
    static final Pattern PATH = Pattern.compile("\\.");
    static final com.google.gson.JsonParser PARSER = new com.google.gson.JsonParser();

    static final Function<JsonElement, String> STRING_GETTER = JsonElement::getAsString;
    static final Function<JsonElement, Character> CHARACTER_GETTER = JsonElement::getAsCharacter;
    static final Function<JsonElement, Byte> BYTE_GETTER = JsonElement::getAsByte;
    static final Function<JsonElement, Boolean> BOOLEAN_GETTER = JsonElement::getAsBoolean;
    static final Function<JsonElement, Short> SHORT_GETTER = JsonElement::getAsShort;
    static final Function<JsonElement, Integer> INTEGER_GETTER = JsonElement::getAsInt;
    static final Function<JsonElement, Float> FLOAT_GETTER = JsonElement::getAsFloat;
    static final Function<JsonElement, Double> DOUBLE_GETTER = JsonElement::getAsDouble;
    static final Function<JsonElement, Long> LONG_GETTER = JsonElement::getAsLong;
    static final BiFunction<Gson, JsonElement, List<JsonElement>> LIST_GETTER = (gson, element) -> gson.fromJson(element, new TypeToken<List<JsonElement>>(){}.getType());
    static final BiFunction<Gson, JsonElement, Map<?, ?>> MAP_GETTER = (gson, element) -> gson.fromJson(element, new TypeToken<Map<String, ?>>(){}.getType());

    @SuppressWarnings("unchecked")
    static final BiFunction<JsonParser, JsonElement, List<JsonParser>> JSON_LIST_GETTER = (parser, element) ->
            LIST_GETTER.apply(parser.getGson(), element).stream()
                    .map(item -> {
                        final Map<String, Object> map = ImmutableMap.copyOf((Map<String, Object>) MAP_GETTER.apply(parser.getGson(), item));

                        return new JsonParser(parser.isNullableValues(), parser.getGson(), item, map,
                                ImmutableMap.copyOf(Maps.flatten(map)));
                    })
                    .collect(Collectors.toList());

    public static final String STRING_DEFAULT = "null";
    public static final char CHARACTER_DEFAULT = 'Ǝ';
    public static final byte BYTE_DEFAULT = -1;
    public static final boolean BOOLEAN_DEFAULT = false;
    public static final Number NUMBER_DEFAULT = -1;
    public static final List<JsonElement> LIST_DEFAULT = ImmutableList.of();
    public static final Map<?, ?> MAP_DEFAULT = ImmutableMap.of();
    public static final List<JsonParser> JSON_LIST_DEFAULT = ImmutableList.of();
}
