package me.piggypiglet.framework.minecraft;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.minecraft.registerables.ServerRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

@Addon(
        startup = @Startup(
                value = ServerRegisterable.class,
                priority = BootPriority.IMPL
        )
)
public final class MinecraftAddon {
}
