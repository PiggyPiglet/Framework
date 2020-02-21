/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

import com.google.inject.Injector;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.FrameworkBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FileData;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public final class Framework {
    private final MainBinding main;
    private final Scanner scanner;
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

    public Framework(MainBinding main, Scanner scanner, BiFunction<FrameworkBootstrap, Framework, InitialModule> initialModule,
                      Injector injector, List<RegisterableData> startupRegisterables, List<Class<? extends ShutdownRegisterable>> shutdownRegisterables,
                      String[] commandPrefixes, List<FileData> files, int threads, Map<Class<?>, ConfigInfo> configs, String configDir,
                      boolean overrideLangFile, ConfigInfo langConfig, CustomLang customLang, boolean debug) {
        this.main = main;
        this.scanner = scanner;
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

    public static <T> FrameworkBuilder<T> builder(@NotNull final Class<T> main) {
        return new FrameworkBuilder<>(main);
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

    public Scanner getScanner() {
        return scanner;
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
}
