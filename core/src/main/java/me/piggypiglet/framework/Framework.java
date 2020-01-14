/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.utils.annotations.Main;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import me.piggypiglet.framework.utils.builder.GenericBuilder;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.BiFunction;

public final class Framework {
    private final MainBinding main;
    private final String pckg;
    private final String[] pckgExclusions;
    private final BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule;
    private final Injector injector;
    private final List<RegisterableData> startupRegisterables;
    private final List<Class<? extends ShutdownRegisterable>> shutdownRegisterables;
    private final String[] commandPrefixes;
    private final List<FileData> files;
    private final int threads;
    private final Map<Class<?>, ConfigInfo> configs;
    private final String fileDir;
    private final boolean overrideLangFile;
    private final ConfigInfo langConfig;
    private final CustomLang customLang;
    private final boolean debug;

    private Framework(MainBinding main, String pckg, String[] pckgExclusions, BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule,
                      Injector injector, List<RegisterableData> startupRegisterables, List<Class<? extends ShutdownRegisterable>> shutdownRegisterables,
                      String[] commandPrefixes, List<FileData> files, int threads, Map<Class<?>, ConfigInfo> configs, String configDir,
                      boolean overrideLangFile, ConfigInfo langConfig, CustomLang customLang, boolean debug) {
        this.main = main;
        this.pckg = pckg;
        this.pckgExclusions = pckgExclusions;
        this.initialModule = initialModule;
        this.injector = injector;
        this.startupRegisterables = startupRegisterables;
        this.shutdownRegisterables = shutdownRegisterables;
        this.commandPrefixes = commandPrefixes;
        this.files = files;
        this.threads = threads;
        this.configs = configs;
        this.fileDir = configDir;
        this.overrideLangFile = overrideLangFile;
        this.langConfig = langConfig;
        this.customLang = customLang;
        this.debug = debug;
    }

    /**
     * Initialize a new instance of FrameworkBuilder
     *
     * @return FrameworkBuilder
     */
    public static FrameworkBuilder builder() {
        return new FrameworkBuilder();
    }

    /**
     * Start the bootstrap process with the current framework configuration.
     *
     * @return FrameworkBootstrap instance
     */
    public FrameworkBootstrap init() {
        return GenericBuilder.of(() -> new FrameworkBootstrap(this))
                .with(FrameworkBootstrap::start)
                .build();
    }

    /**
     * Get the main instance
     *
     * @return MainBinding
     */
    public MainBinding getMain() {
        return main;
    }

    /**
     * Get the projects package
     *
     * @return String
     */
    public String getPckg() {
        return pckg;
    }

    /**
     * Get any packages that should be excluded when scanning
     *
     * @return String array
     */
    public String[] getPckgExclusions() {
        return pckgExclusions;
    }

    public BiFunction<FrameworkBootstrap, Framework, InitialModule> getInitialModule() {
        return initialModule;
    }

    /**
     * Get the project's initial injector
     *
     * @return Injector
     */
    public Injector getInjector() {
        return injector;
    }

    /**
     * Get all manually inputted StartupRegisterables
     *
     * @return Classes extending StartupRegisterable
     */
    public List<RegisterableData> getStartupRegisterables() {
        return startupRegisterables;
    }

    /**
     * Get all manually inputted ShutdownRegisterables
     *
     * @return Classes extending ShutdownRegisterable
     */
    public List<Class<? extends ShutdownRegisterable>> getShutdownRegisterables() {
        return shutdownRegisterables;
    }

    /**
     * Get the application's command prefixes.
     *
     * @return String
     */
    public String[] getCommandPrefixes() {
        return commandPrefixes;
    }

    /**
     * Get information on all files that need to be made
     *
     * @return List of FileData
     */
    public List<FileData> getFiles() {
        return files;
    }

    /**
     * Get the amount of threads to be stored in the default task manager's thread pool
     *
     * @return Amount of threads available in the thread pool
     */
    public int getThreads() {
        return threads;
    }

    /**
     * Get user defined configs for addons
     *
     * @return custom addon configs
     */
    public Map<Class<?>, ConfigInfo> getConfigs() {
        return configs;
    }

    /**
     * Get the directory files will be put in.
     *
     * @return directory path
     */
    public String getFileDir() {
        return fileDir;
    }

    /**
     * Whether to override default language files with a custom one.
     *
     * @return boolean
     */
    public boolean overrideLangFile() {
        return overrideLangFile;
    }

    /**
     * Info about custom lang config, used if default is overridden.
     *
     * @return ConfigInfo
     */
    public ConfigInfo getLangConfig() {
        return langConfig;
    }

    /**
     * Custom language data, not necessarily related to overriding defaults.
     *
     * @return CustomLang
     */
    public CustomLang getCustomLang() {
        return customLang;
    }

    /**
     * Whether to log debug messages.
     *
     * @return boolean
     */
    public boolean isDebug() {
        return debug;
    }

    public static final class FrameworkBuilder {
        private Object main = "d-main";
        private String pckg = "d-pckg";
        private String[] pckgExclusions = new String[]{};
        private BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule = InitialModule::new;
        private Injector injector = null;
        private List<RegisterableData> startupRegisterables = new ArrayList<>();
        private List<Class<? extends ShutdownRegisterable>> shutdownRegisterables = new ArrayList<>();
        private String[] commandPrefixes = null;
        private final List<FileData> files = new ArrayList<>();
        private int threads = 15;
        private final Map<Class<?>, ConfigInfo> configs = new HashMap<>();
        private String fileDir = ".";
        private boolean overrideLangFile = false;
        private ConfigInfo langConfig = null;
        private CustomLang customLang = null;
        private boolean debug = false;

        private FrameworkBuilder() {
        }

        /**
         * Set the application's main instance
         *
         * @param instance Main instance
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder main(Object instance) {
            this.main = new MainBinding(Object.class, instance, Main.class);
            return this;
        }

        /**
         * Set the application's main instance
         *
         * @param clazz    Class to bind the instance under, for example, in bukkit,
         *                 you'd enter JavaPlugin.class
         * @param instance Main instance
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder main(Class<?> clazz, Object instance) {
            this.main = new MainBinding(clazz, instance);
            return this;
        }

        /**
         * Set the application's package which will be scanned,
         * and optionally any sub packages to exclude.
         *
         * @param pckg       Application's package
         * @param exclusions Packages to be excluded
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder pckg(String pckg, String... exclusions) {
            this.pckg = pckg;
            this.pckgExclusions = exclusions;
            return this;
        }

        public final FrameworkBuilder initialModule(BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule) {
            this.initialModule = initialModule;
            return this;
        }

        /**
         * Set the application's initial injector, if one is already made.
         *
         * @param injector Application's injector
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder injector(Injector injector) {
            this.injector = injector;
            return this;
        }

        /**
         * Add startup registerables to be ran in the bootstrap, in order.
         *
         * @param registerables Varargs classes extending StartupRegisterable
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder startup(RegisterableData... registerables) {
            startupRegisterables = Arrays.asList(registerables);
            return this;
        }

        /**
         * Add shutdown registerables to be ran in the shutdown hook, in order.
         *
         * @param registerables Varargs classes extending ShutdownRegisterable
         * @return FrameworkBuilder
         */
        @SafeVarargs
        public final FrameworkBuilder shutdown(Class<? extends ShutdownRegisterable>... registerables) {
            shutdownRegisterables = Arrays.asList(registerables);
            return this;
        }

        /**
         * The application's command prefixes, to be used in command handlers.
         *
         * @param commandPrefixes Strings
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder commandPrefixes(String... commandPrefixes) {
            this.commandPrefixes = commandPrefixes;
            return this;
        }

        /**
         * Add a file to be copied from an embed and loaded into memory.
         *
         * @param config       Should this file be stored as a FileConfiguration
         * @param name         Name of the file to be referenced in FileManager
         * @param internalPath The hard internal path of the file.
         * @param externalPath The external path of the file, set to null if file shouldn't be copied
         *                     outside the jar.
         * @param annotation   Annotation class to bind the instance to.
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder file(boolean config, String name, String internalPath, String externalPath, Class<? extends Annotation> annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        /**
         * Add a file to be copied from an embed and loaded into memory.
         *
         * @param config       Should this file be stored as a FileConfiguration
         * @param name         Name of the file to be referenced in FileManager
         * @param internalPath The hard internal path of the file.
         * @param externalPath The external path of the file, set to null if file shouldn't be copied
         *                     outside the jar.
         * @param annotation   Annotation instance to bind the instance to.
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder file(boolean config, String name, String internalPath, String externalPath, Annotation annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        /**
         * Add a file to be copied from an embed and loaded into memory.
         *
         * @param config       Should this file be stored as a FileConfiguration
         * @param name         Name of the file to be referenced in FileManager
         * @param internalPath The dynamic internal path of the file.
         * @param externalPath The external path of the file, set to null if file shouldn't be copied
         *                     outside the jar.
         * @param annotation   Annotation class to bind the instance to.
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder file(boolean config, String name, FileData.ConfigPathReference internalPath, String externalPath, Class<? extends Annotation> annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        /**
         * Add a file to be copied from an embed and loaded into memory.
         *
         * @param config       Should this file be stored as a FileConfiguration
         * @param name         Name of the file to be referenced in FileManager
         * @param internalPath The dynamic internal path of the file.
         * @param externalPath The external path of the file, set to null if file shouldn't be copied
         *                     outside the jar.
         * @param annotation   Annotation instance to bind the instance to.
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder file(boolean config, String name, FileData.ConfigPathReference internalPath, String externalPath, Annotation annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        /**
         * Set the amount of threads that will be available via the default task manager's thread pol
         *
         * @param threads Amount of threads
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder threads(int threads) {
            this.threads = threads;
            return this;
        }

        /**
         * Configure a config for an addon that requires configuration. If not done manually,
         * the addon will usually create it's own configuration file.
         *
         * @param addon     Addon to configure
         * @param config    String reference to config in FileManager
         * @param locations Locations of the values the addon needs
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder config(Class<?> addon, String config, Map<String, String> locations) {
            Preconditions.checkArgument(addon.getAnnotation(Addon.class) != null, "%s is not a valid addon.", addon.getSimpleName());

            configs.put(addon, new ConfigInfo(config, locations, false));
            return this;
        }

        /**
         * Set the parent directory configs will be put in. Don't include a file separator (/ or \) at the end.
         *
         * @param dir Path of the directory
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder fileDir(String dir) {
            fileDir = dir;
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
            BuilderUtils.checkVars("FrameworkBuilder", main, pckg);

            return new Framework((MainBinding) main, pckg, pckgExclusions, initialModule, injector, startupRegisterables,
                    shutdownRegisterables, commandPrefixes, files, threads, configs, fileDir, overrideLangFile, langConfig, customLang, debug);
        }
    }
}
