package mapping.gson.objects;

import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class TestObject {
    public static final TestObject DEFAULT;

    static {
        DEFAULT = new TestObject();
        DEFAULT.string = "test";
        DEFAULT.map1 = Maps.of(new HashMap<String, String>())
                .key("test").value("value")
                .build();
        DEFAULT.map2 = Maps.of(new HashMap<String, Integer>())
                .key("key").value(5)
                .build();
        DEFAULT.iterable1 = Arrays.asList("beep", "boop");
        DEFAULT.iterable2 = new HashSet<>(Arrays.asList("beepity", "boopity"));
        DEFAULT.number = 5;
        DEFAULT.num2 = 60;
        DEFAULT.num3 = 32.5123;
        DEFAULT.num4 = 3123552139599125142L;
        DEFAULT.nested = NestedObject.DEFAULT;
    }

    private String string;
    private Map<String, String> map1;
    private Map<String, Integer> map2;
    private List<String> iterable1;
    private Set<String> iterable2;
    private Number number;
    private int num2;
    private double num3;
    private long num4;
    private NestedObject nested;

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TestObject that = (TestObject) o;

        return num2 == that.num2 &&
                Double.compare(that.num3, num3) == 0 &&
                num4 == that.num4 &&
                string.equals(that.string) &&
                map1.equals(that.map1) &&
                map2.equals(that.map2) &&
                iterable1.equals(that.iterable1) &&
                iterable2.equals(that.iterable2) &&
                number.equals(that.number) &&
                nested.equals(that.nested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, map1, map2, iterable1, iterable2, number, num2, num3, num4, nested);
    }
}
