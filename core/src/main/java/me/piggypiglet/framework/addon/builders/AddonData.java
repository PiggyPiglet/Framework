package me.piggypiglet.framework.addon.builders;

import com.google.common.collect.Multimap;
import me.piggypiglet.framework.addon.builders.objects.LangData;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class AddonData {
    private final Addon addon;
    private final FilesData files;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup;
    private final List<Class<? extends ShutdownRegisterable>> shutdown;
    private final LangData lang;

    AddonData(@NotNull final Addon addon, @NotNull final FilesData files,
              @NotNull final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup,
              @NotNull final List<Class<? extends ShutdownRegisterable>> shutdown, @NotNull final LangData lang) {
        this.addon = addon;
        this.files = files;
        this.startup = startup;
        this.shutdown = shutdown;
        this.lang = lang;
    }

    @NotNull
    public Addon getAddon() {
        return addon;
    }

    @NotNull
    public FilesData getFiles() {
        return files;
    }

    @NotNull
    public Multimap<BootPriority, Class<? extends StartupRegisterable>> getStartup() {
        return startup;
    }

    @NotNull
    public List<Class<? extends ShutdownRegisterable>> getShutdown() {
        return shutdown;
    }

    @NotNull
    public LangData getLang() {
        return lang;
    }
}