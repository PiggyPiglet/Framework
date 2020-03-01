package me.piggypiglet.framework.init.builder.stages.addons;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.framework.api.AddonConfiguration;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class AddonsBuilder extends AbstractBuilder<Map<Class<? extends Addon<?>>, AddonConfiguration>, Framework> {
    private final Map<Class<? extends Addon<?>>, AddonConfiguration> addons = new HashMap<>();

    @NotNull
    public <A extends Addon<A>> A addon(@NotNull final Class<A> addon) {
        try {
            return BuilderUtils.customBuilder(addon.newInstance(), config -> {
                addons.put(addon, config);
                return this;
            });
        } catch (Exception e) {
            throw new AssertionError("The addon provided does not have a public no-arg constructor.");
        }
    }

    @Override
    protected Map<Class<? extends Addon<?>>, AddonConfiguration> provideBuild() {
        return addons;
    }
}
