package me.piggypiglet.framework.minecraft.api.versions.registerables;

import me.piggypiglet.framework.minecraft.api.versions.Versions;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class VersionRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        //todo: proper impl
        addBinding(Versions.class, Versions.V1_15);
    }
}
