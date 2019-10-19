package me.piggypiglet.framework.nukkit.logging;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.logging.Logger;

public final class NukkitLogger extends Logger<PluginLogger> {
    @Inject private MainBinding main;

    @Override
    protected PluginLogger init(String name) {
        return ((PluginBase) main.getInstance()).getLogger();
    }

    @Override
    protected void info(String message) {
        logger.info(message);
    }

    @Override
    protected void warning(String message) {
        logger.warning(message);
    }

    @Override
    protected void error(String message) {
        logger.error(message);
    }
}
