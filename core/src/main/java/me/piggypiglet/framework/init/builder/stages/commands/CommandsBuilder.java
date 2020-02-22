package me.piggypiglet.framework.init.builder.stages.commands;

import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CommandsBuilder<R> extends AbstractBuilder<String[], R> {
    private String[] prefixes = null;

    @NotNull
    public CommandsBuilder<R> prefixes(@NotNull final String... prefixes) {
        this.prefixes = prefixes;
        return this;
    }

    @Nullable
    @Override
    protected String[] provideBuild() {
        return prefixes;
    }
}
