package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import me.piggypiglet.framework.json.JsonParser;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.piggypiglet.framework.json.ParserConstants.*;
import static org.junit.Assert.*;

public final class JsonParserTests {
    private static final TestPOJO testPOJO = new TestPOJO();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final List<JsonParser> PARSERS = Stream.of(true, false).map(b -> JsonParser.builder(GSON.toJson(testPOJO))
            .gson(GSON)
            .nullableValues(b)
            .build()).collect(Collectors.toList());


    @Test
    public void testPOJOSemantics() {
        parse(parser -> assertEquals(parser.getGson(), GSON));
        assertTrue(PARSERS.get(0).isNullableValues());
        assertFalse(PARSERS.get(1).isNullableValues());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testJsonParsing() {
        parse(parser -> {
            assertEquals(parser.getString("str"), testPOJO.str);
            assertEquals(parser.getCharacter("chr"), (Character) testPOJO.chr);
            assertEquals(parser.getByte("bite"), (Byte) testPOJO.bite);
            assertEquals(parser.getBoolean("die"), testPOJO.die);
            assertEquals(parser.getShort("mrLong"), (Short) testPOJO.mrLong);
            assertEquals(parser.getInteger("intejer"), (Integer) testPOJO.intejer);
            assertEquals(parser.getFloat("fall"), (Float) testPOJO.fall);
            assertEquals(parser.getDouble("single"), (Double) testPOJO.single);
            assertEquals(parser.getLong("smol"), (Long) testPOJO.smol);
            assertEquals(parser.getList("elements"), testPOJO.elements);
            assertEquals(parser.getJsonList("list").get(0).getString("name"), testPOJO.list.get(0).name);
            assertEquals(parser.getMap("meth").get("milk"), testPOJO.meth.get("milk"));
            assertEquals(((Map<String, Object>) parser.getMap("meth").get("dad")).get("name"), ((TestPOJO.OtherPOJO) testPOJO.meth.get("dad")).name);
        });
    }

    @Test
    public void testJsonParsingDefaults() {
        parse(parser -> {
            assertEquals(parser.getString("blah", "def"), "def");
            assertEquals(parser.getCharacter("blah", 'd'), 'd');
            assertEquals(parser.getByte("blah", (byte) 2), (byte) 2);
            assertTrue(parser.getBoolean("blah", true));
            assertEquals(parser.getShort("blah", (short) 2), 2);
            assertEquals(parser.getInteger("blah", 2), 2);
            assertEquals(parser.getFloat("blah", 2F), 2F, 69);
            assertEquals(parser.getDouble("blah", 2D), 2D, 69);
            assertEquals(parser.getLong("blah", 2), 2);
            assertEquals(parser.getList("blah", parser.getList("elements")), parser.getList("elements"));
            final List<JsonParser> list = parser.getJsonList("list");
            assertEquals(parser.getJsonList("blah", list), list);
            assertEquals(parser.getMap("blah", parser.getMap("meth")), parser.getMap("meth"));
        });
    }

    @Test
    public void testJsonParsingNulls() {
        final JsonParser parser = PARSERS.get(0);

        assertNull(parser.getString("blah"));
        assertNull(parser.getCharacter("blah"));
        assertNull(parser.getByte("blah"));
        assertNull(parser.getBoolean("blah"));
        assertNull(parser.getShort("blah"));
        assertNull(parser.getInteger("blah"));
        assertNull(parser.getFloat("blah"));
        assertNull(parser.getDouble("blah"));
        assertNull(parser.getLong("blah"));
        assertNull(parser.getList("blah"));
        assertNull(parser.getJsonList("blah"));
        assertNull(parser.getMap("blah"));
    }

    @Test
    public void testJsonParsingDefaultNulls() {
        final JsonParser parser = PARSERS.get(1);

        assertEquals(parser.getString("blah"), STRING_DEFAULT);
        assertEquals(parser.getCharacter("blah"), (Character) CHARACTER_DEFAULT);
        assertEquals(parser.getByte("blah"), (Byte) BYTE_DEFAULT);
        assertEquals(parser.getBoolean("blah"), BOOLEAN_DEFAULT);
        assertEquals(parser.getShort("blah"), (Short) NUMBER_DEFAULT.shortValue());
        assertEquals(parser.getInteger("blah"), (Integer) NUMBER_DEFAULT.intValue());
        assertEquals(parser.getFloat("blah"), (Float) NUMBER_DEFAULT.floatValue());
        assertEquals(parser.getDouble("blah"), (Double) NUMBER_DEFAULT.doubleValue());
        assertEquals(parser.getLong("blah"), (Long) NUMBER_DEFAULT.longValue());
        assertEquals(parser.getList("blah"), LIST_DEFAULT);
        assertEquals(parser.getJsonList("blah"), JSON_LIST_DEFAULT);
        assertEquals(parser.getMap("blah"), MAP_DEFAULT);
    }

    private void parse(@NotNull final Consumer<JsonParser> assertion) {
        PARSERS.forEach(assertion);
    }

    private static class TestPOJO {
        private final String str = "hi";
        private final char chr = 'o';
        private final byte bite = 0x05B;
        private final boolean die = true;
        private final short mrLong = 69;
        private final int intejer = 420;
        private final float fall = 800.8135F;
        private final double single = 38853115.69420;
        private final long smol = 696969696969696969L;
        private final List<JsonElement> elements = Collections.singletonList(new Gson().toJsonTree(new OtherPOJO()));
        private final List<OtherPOJO> list = Collections.singletonList(new OtherPOJO());
        private final Map<String, Object> meth = Maps.of(new HashMap<String, Object>())
                .key("milk").value("me")
                .key("dad").value(new OtherPOJO())
                .build();

        private static class OtherPOJO {
            private final String name = "Piggy";
        }
    }
}
