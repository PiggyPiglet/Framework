package me.piggypiglet.framework.registerables.startup.addon;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.Map;

public final class DefaultConfigsRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Framework framework;
    @Inject private ConfigManager configManager;

    @Override
    protected void execute() {
        Map<Class<?>, ConfigInfo> configs = framework.getConfigs();

        bootstrap.getAddons().forEach((c, a) -> {
            if (!configs.containsKey(c) && !a.config().name().equals("null")) {
                configManager.getFilesToBeCreated().add(a.config().name());
            }
        });
    }
}
