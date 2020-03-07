package me.piggypiglet.framework.addon.framework.config;

import me.piggypiglet.framework.init.builder.FrameworkBuilder;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;

@SuppressWarnings("unchecked")
public abstract class AddonConfigurationBuilder<T extends AddonConfigurationBuilder<T>> extends AbstractBuilder<AddonConfiguration, FrameworkBuilder> {
    private final T instance = (T) this;
}
