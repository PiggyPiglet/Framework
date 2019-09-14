package me.piggypiglet.framework.registerables.startup.addon;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.addon.objects.Config;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class UserConfigsRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private ConfigManager configManager;

    @Override
    protected void execute() {
        Map<Class<?>, ConfigInfo> configs = framework.getConfigs();

        bootstrap.getAddons().forEach((c, a) -> {
            ConfigInfo info;

            if (configs.containsKey(c)) {
                info = configs.get(c);
            } else {
                info = new ConfigInfo(a.config().name(), Arrays.stream(a.config().keys()).collect(Collectors.toMap(s -> s, s -> s)), true);
            }

            final Map<String, Object> items = info.getLocations().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, e -> fileManager.getConfig(info.getConfig()).get(e.getKey())));

            configManager.getConfigs().put(c, new Config(items));
        });
    }
}
