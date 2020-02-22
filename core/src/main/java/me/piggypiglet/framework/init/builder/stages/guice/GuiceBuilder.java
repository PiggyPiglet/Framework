package me.piggypiglet.framework.init.builder.stages.guice;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public final class GuiceBuilder<R> extends AbstractBuilder<GuiceData, R> {
    private BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule = InitialModule::new;
    private Injector injector = null;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup = ArrayListMultimap.create();
    private final List<Class<? extends ShutdownRegisterable>> shutdown = new ArrayList<>();

    @NotNull
    public GuiceBuilder<R> initialModule(@NotNull final BiFunction<FrameworkBootstrap, Framework, InitialModule> value) {
        initialModule = value;
        return this;
    }

    @NotNull
    public GuiceBuilder<R> injector(@NotNull final Injector value) {
        injector = value;
        return this;
    }

    @SafeVarargs
    @NotNull
    public final GuiceBuilder<R> startup(@NotNull final Class<? extends StartupRegisterable>... registerables) {
        return startup(BootPriority.MANUAL, registerables);
    }

    @SafeVarargs
    @NotNull
    public final GuiceBuilder<R> startup(@NotNull final BootPriority priority, @NotNull final Class<? extends StartupRegisterable>... registerables) {
        Arrays.stream(registerables).forEach(registerable -> startup.put(priority, registerable));
        return this;
    }

    @SafeVarargs
    @NotNull
    public final GuiceBuilder<R> shutdown(@NotNull final Class<? extends ShutdownRegisterable>... registerables) {
        shutdown.addAll(Arrays.asList(registerables));
        return this;
    }

    @NotNull
    @Override
    protected GuiceData provideBuild() {
        return new GuiceData(initialModule, injector, startup, shutdown);
    }
}