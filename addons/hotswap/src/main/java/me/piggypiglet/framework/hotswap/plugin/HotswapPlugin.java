package me.piggypiglet.framework.hotswap.plugin;

import com.google.inject.Inject;
import me.piggypiglet.framework.FrameworkBootstrap;
import org.hotswap.agent.annotation.LoadEvent;
import org.hotswap.agent.annotation.OnClassLoadEvent;
import org.hotswap.agent.annotation.Plugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Plugin(
        name = "RPF-Plugin",
        description = "RPF HotswapAgent plugin",
        testedVersions = "Tested on RPF"
)
public final class HotswapPlugin {
    @Inject private static FrameworkBootstrap main;
    
    @OnClassLoadEvent(classNameRegexp = ".*", events = LoadEvent.REDEFINE)
    public static void onAnyReload() {
        main.getRegisterables().stream().filter(RedefinableRegisterables::redefinable).forEach(r -> {
            r.run(main.getInjector());
        });
    }
}