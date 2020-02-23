package me.piggypiglet.framework.init.builder;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.builders.ConfigInfo;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.framework.config.AddonConfiguration;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.init.builder.stages.addons.AddonsBuilder;
import me.piggypiglet.framework.init.builder.stages.commands.CommandsBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceBuilder;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceData;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.scanning.Scanners;
import me.piggypiglet.framework.scanning.builders.ScannerBuilder;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.Main;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class FrameworkBuilder<T> {
    private final MainBinding main;

    private Scanner scanner = null;
    private GuiceData guice = null;
    private String[] commandPrefixes = null;
    private FilesData files = null;
    private Map<Class<? extends Addon<?>>, AddonConfiguration> addons = null;
    private int threads = 15;
    private final Map<Class<?>, ConfigInfo> configs = new HashMap<>();
    private boolean overrideLangFile = false;
    private ConfigInfo langConfig = null;
    private CustomLang customLang = null;
    private boolean debug = false;

    public FrameworkBuilder(@NotNull final T main) {
        this.main = new MainBinding(main.getClass(), main, Main.class);
    }

    public FrameworkBuilder(@NotNull final Class<? super T> interfaze, @NotNull final T instance) {
        this.main = new MainBinding(interfaze, instance);
    }

    @NotNull
    public ScannerBuilder<FrameworkBuilder<T>> scanner() {
        return Scanners.builder(main.getClazz(), scanner -> {
            this.scanner = scanner;
            return this;
        });
    }

    @NotNull
    public FrameworkBuilder<T> scanner(@NotNull final Scanner scanner) {
        this.scanner = scanner;
        return this;
    }

    @NotNull
    public GuiceBuilder<FrameworkBuilder<T>> guice() {
        return BuilderUtils.customBuilder(new GuiceBuilder<>(), data -> {
            guice = data;
            return this;
        });
    }

    public CommandsBuilder<FrameworkBuilder<T>> commands() {
        return BuilderUtils.customBuilder(new CommandsBuilder<>(), prefixes -> {
            commandPrefixes = prefixes;
            return this;
        });
    }

    public FilesBuilder<FrameworkBuilder<T>> files() {
        return BuilderUtils.customBuilder(new FilesBuilder<>(), data -> {
            files = data;
            return this;
        });
    }

    public AddonsBuilder addons() {
        return BuilderUtils.customBuilder(new AddonsBuilder(), addons -> {
            this.addons = addons;
            return build();
        });
    }

    /**
     * Set the amount of threads that will be available via the default task manager's thread pol
     *
     * @param threads Amount of threads
     * @return FrameworkBuilder
     */
    public FrameworkBuilder<T> threads(int threads) {
        this.threads = threads;
        return this;
    }

    /**
     * Declare whether a lang file will be used over hardcoded values.
     *
     * @param value True or false
     * @return FrameworkBuilder
     */
    public final FrameworkBuilder overrideLangFile(boolean value) {
        this.overrideLangFile = value;
        return this;
    }

    /**
     * If useLangFile is set to true, optionally use your own config with mappings.
     *
     * @param config    Config name
     * @param locations Mapping
     * @return FrameworkBuilder
     */
    public final FrameworkBuilder langConfig(String config, Map<String, String> locations) {
        this.langConfig = new ConfigInfo(config, locations, false);
        return this;
    }

    /**
     * Specify a custom language enum
     *
     * @param config Config identifier
     * @param values Values of the enum
     * @return FrameworkBuilder
     */
    public final FrameworkBuilder customLang(String config, LangEnum[] values) {
        customLang = new CustomLang(config, values);
        return this;
    }

    /**
     * Should debug messages be logged?
     *
     * @param debug True/false
     * @return FrameworkBuilder
     */
    public final FrameworkBuilder debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    /**
     * Compile all the user-set options into an instance of Framework
     * NOTE: Will crash if any of the following aren't set:
     * - main
     * - pckg
     *
     * @return Framework instance
     */
    public final Framework build() {
        BuilderUtils.requiredVariables("FrameworkBuilder", main);

        return new Framework((MainBinding) main, scanner, initialModule, injector, startupRegisterables,
                shutdownRegisterables, commandPrefixes, files, threads, configs, fileDir, overrideLangFile, langConfig, customLang, debug);
    }
}
