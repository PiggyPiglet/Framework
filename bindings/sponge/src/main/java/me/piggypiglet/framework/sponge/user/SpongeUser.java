package me.piggypiglet.framework.sponge.user;

import me.piggypiglet.framework.user.User;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.serializer.TextSerializers;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpongeUser extends User {
    private final CommandSource src;

    public SpongeUser(CommandSource src) {
        super(
                src.getName(),
                src.getFriendlyIdentifier().orElse("null")
        );

        this.src = src;
    }

    @Override
    protected void sendMessage(String message) {
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return src.hasPermission(permission);
    }
}
