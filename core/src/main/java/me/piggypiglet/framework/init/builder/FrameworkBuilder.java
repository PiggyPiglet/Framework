package me.piggypiglet.framework.init.builder;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.framework.api.AddonConfiguration;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.init.builder.stages.addons.AddonsBuilder;
import me.piggypiglet.framework.init.builder.stages.commands.CommandsBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceBuilder;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceData;
import me.piggypiglet.framework.init.builder.stages.language.LanguageBuilder;
import me.piggypiglet.framework.init.builder.stages.language.LanguageData;
import me.piggypiglet.framework.init.builder.stages.scanning.ScanningBuilder;
import me.piggypiglet.framework.init.builder.stages.scanning.ScanningData;
import me.piggypiglet.framework.utils.annotations.Main;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class FrameworkBuilder<T> {
    private final MainBinding main;

    private ScanningData scanning = null;
    private GuiceData guice = null;
    private String[] commandPrefixes = null;
    private FilesData files = null;
    private Map<Class<? extends Addon<?>>, AddonConfiguration> addons = null;
    private int threads = 15;
    private LanguageData language;
    private boolean debug = false;

    public FrameworkBuilder(@NotNull final T main) {
        this.main = new MainBinding(main.getClass(), main, Main.class);
    }

    public FrameworkBuilder(@NotNull final Class<? super T> interfaze, @NotNull final T instance) {
        this.main = new MainBinding(interfaze, instance);
    }

    @NotNull
    public ScanningBuilder<FrameworkBuilder<T>> scanning() {
        return BuilderUtils.customBuilder(new ScanningBuilder<>(main.getClazz()), data -> {
            scanning = data;
            return this;
        });
    }

    @NotNull
    public GuiceBuilder<FrameworkBuilder<T>> guice() {
        return BuilderUtils.customBuilder(new GuiceBuilder<>(), data -> {
            guice = data;
            return this;
        });
    }

    @NotNull
    public CommandsBuilder<FrameworkBuilder<T>> commands() {
        return BuilderUtils.customBuilder(new CommandsBuilder<>(), prefixes -> {
            commandPrefixes = prefixes;
            return this;
        });
    }

    @NotNull
    public FilesBuilder<FrameworkBuilder<T>> files() {
        return BuilderUtils.customBuilder(new FilesBuilder<>(), data -> {
            files = data;
            return this;
        });
    }

    @NotNull
    public AddonsBuilder addons() {
        return BuilderUtils.customBuilder(new AddonsBuilder(), addons -> {
            this.addons = addons;
            return build();
        });
    }

    @NotNull
    public FrameworkBuilder<T> threads(int threads) {
        this.threads = threads;
        return this;
    }

    @NotNull
    public LanguageBuilder<FrameworkBuilder<T>> language() {
        return BuilderUtils.customBuilder(new LanguageBuilder<>(), data -> {
            language = data;
            return this;
        });
    }

    /**
     * Should debug messages be logged?
     *
     * @param debug True/false
     * @return FrameworkBuilder
     */
    @NotNull
    public FrameworkBuilder<T> debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    @NotNull
    public Framework build() {
        return new Framework(main, scanning, guice, commandPrefixes, files, addons, threads, language, debug);
    }
}
