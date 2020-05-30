package me.piggypiglet.demo.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.framework.BaseCommand;
import me.piggypiglet.framework.minecraft.api.key.Keys;
import me.piggypiglet.framework.minecraft.api.server.Server;
import me.piggypiglet.framework.user.User;
import org.jetbrains.annotations.NotNull;

public final class TestCommand extends BaseCommand {
    @Inject private Server server;

    public TestCommand() {
        super("testserver");
    }

    @Override
    protected boolean execute(@NotNull User user, @NotNull String[] args) {
        user.sendMessage(server.get(Keys.SERVER_ADDRESS));

        return true;
    }
}
