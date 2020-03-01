package mapping.gson.objects;

import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class NestedObject {
    public static final NestedObject DEFAULT;

    static {
        DEFAULT = new NestedObject();
        DEFAULT.number = "test";
        DEFAULT.iterable1 = Maps.of(new HashMap<String, String>())
                .key("test").value("value")
                .build();
        DEFAULT.num3 = Maps.of(new HashMap<String, Integer>())
                .key("key").value(5)
                .build();
        DEFAULT.string = Arrays.asList("beep", "boop");
        DEFAULT.num2 = new HashSet<>(Arrays.asList("beepity", "boopity"));
        DEFAULT.map2 = 5;
        DEFAULT.num4 = 60;
        DEFAULT.iterable2 = 32.5123;
        DEFAULT.map1 = 3123552139599125142L;
    }

    private String number;
    private Map<String, String> iterable1;
    private Map<String, Integer> num3;
    private List<String> string;
    private Set<String> num2;
    private Number map2;
    private int num4;
    private double iterable2;
    private long map1;

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final NestedObject that = (NestedObject) o;

        return num4 == that.num4 &&
                Double.compare(that.iterable2, iterable2) == 0 &&
                map1 == that.map1 &&
                number.equals(that.number) &&
                iterable1.equals(that.iterable1) &&
                num3.equals(that.num3) &&
                string.equals(that.string) &&
                num2.equals(that.num2) &&
                map2.equals(that.map2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, iterable1, num3, string, num2, map2, num4, iterable2, map1);
    }
}
