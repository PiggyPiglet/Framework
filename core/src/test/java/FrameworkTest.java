import me.piggypiglet.framework.Framework;
import org.junit.Test;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FrameworkTest {
    @Test
    public void startupTest() {
        Framework.builder().main(null).pckg("me.piggypiglet.framework").commandPrefix("test").build().init();
    }
}
