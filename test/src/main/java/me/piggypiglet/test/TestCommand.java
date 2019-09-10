package me.piggypiglet.test;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.jda.annotation.ID;
import me.piggypiglet.framework.user.User;
import net.dv8tion.jda.api.entities.Guild;

public final class TestCommand extends Command {
    private final Guild guild;

    @Inject
    public TestCommand(@ID("620557484012077077") Guild guild) {
        super("test");
        this.guild = guild;
    }

    @Override
    protected boolean execute(User user, String[] args) {
        System.out.println(guild);
        return true;
    }
}