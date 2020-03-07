package me.piggypiglet.framework.addon.framework.config;

public final class DefaultAddonConfigurationBuilder extends AddonConfigurationBuilder<DefaultAddonConfigurationBuilder> {
    @Override
    protected AddonConfiguration provideBuild() {
        return new AddonConfiguration();
    }
}
