package me.piggypiglet.framework.minecraft;

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.minecraft.lang.Lang;
import me.piggypiglet.framework.minecraft.registerables.CommandHandlerRegisterable;
import me.piggypiglet.framework.minecraft.registerables.ServerRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.Langs;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

@Addon(
        startup = {
                @Startup(
                        value = ServerRegisterable.class,
                        priority = BootPriority.IMPL
                ),
                @Startup(
                        value = CommandHandlerRegisterable.class,
                        priority = BootPriority.COMMANDS
                )
        },
        lang = @Langs(
                file = "minecraft_lang.json",
                clazz = Lang.class
        )
)
public final class MinecraftAddon {}
