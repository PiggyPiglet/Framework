package me.piggypiglet.framework.addon.init;

import com.google.common.collect.Multimap;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.init.objects.LanguageData;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.internal.objects.ScanningRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class AddonData {
    private final Addon addon;
    private final FilesData files;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup;
    private final List<Class<? extends ShutdownRegisterable>> shutdown;
    private final Set<ScanningRequest> requests;
    private final LanguageData lang;

    AddonData(@NotNull final Addon addon, @Nullable final FilesData files,
              @NotNull final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup,
              @NotNull final List<Class<? extends ShutdownRegisterable>> shutdown, @NotNull final Set<ScanningRequest> requests,
              @Nullable final LanguageData lang) {
        this.addon = addon;
        this.files = files;
        this.startup = startup;
        this.shutdown = shutdown;
        this.requests = requests;
        this.lang = lang;
    }

    @NotNull
    public Addon getAddon() {
        return addon;
    }

    @NotNull
    public Optional<FilesData> getFiles() {
        return Optional.ofNullable(files);
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
    public Set<ScanningRequest> getRequests() {
        return requests;
    }

    @NotNull
    public Optional<LanguageData> getLang() {
        return Optional.ofNullable(lang);
    }
}