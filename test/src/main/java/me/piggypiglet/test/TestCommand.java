package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.jars.loading.JarLoader;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.test.loader.TestLoader;

import java.net.URL;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    @Inject private JarLoader jarLoader;

    public TestCommand() {
        super("test");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        boolean exists = false;

        try {
            Class.forName("me.piggypiglet.test.TestClass");
            exists = true;
//            user.sendMessage(me.piggypiglet.test.TestClass.test());
            return true;
        } catch (Exception ignored) {}

        user.sendMessage(String.valueOf(exists));

        try {
            jarLoader.loadAll("modules/", new TestLoader.Test("test", "1.0.0", "05c77d015d5e69ee406d0b3b553f962e", new URL("https://rpf.piggypiglet.me/test/testr.jar")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
