package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.Map;

public final class SSLRegisterable extends StartupRegisterable {
    @Inject private ConfigManager configManager;
    @Inject private FileManager fileManager;

    @Override
    protected void execute() {
        Map<String, Object> config = configManager.getConfigs().get(HTTPAddon.class).getItems();

        if ((Boolean) config.get("ssl.enabled")) {
            try {
                fileManager.loadFile("ssl", "", (String) config.get("ssl.path"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
