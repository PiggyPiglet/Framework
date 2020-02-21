package me.piggypiglet.framework.init.builder.stages.guice;

import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;

public final class GuiceData {
    private final BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule;
    private final Injector injector;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup;
    private final List<Class<? extends ShutdownRegisterable>> shutdown;

    GuiceData(@NotNull final BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule, @Nullable final Injector injector,
              @NotNull final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup, @NotNull final List<Class<? extends ShutdownRegisterable>> shutdown) {
        this.initialModule = initialModule;
        this.injector = injector;
        this.startup = startup;
        this.shutdown = shutdown;
    }

    @NotNull
    public BiFunction<FrameworkBootstrap, Framework, InitialModule> getInitialModule() {
        return initialModule;
    }

    @Nullable
    public Injector getInjector() {
        return injector;
    }

    @NotNull
    public Multimap<BootPriority, Class<? extends StartupRegisterable>> getStartup() {
        return startup;
    }

    @NotNull
    public List<Class<? extends ShutdownRegisterable>> getShutdown() {
        return shutdown;
    }
}
