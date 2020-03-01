package mapping.gson;

import mapping.gson.objects.TestObject;
import org.junit.Test;

public final class LevenshteinMapperTests {
    @Test
    public void testFromData() {
        System.out.println(GsonConstants.GSON.toJson(TestObject.DEFAULT));
    }
}
