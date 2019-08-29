package me.piggypiglet.framework;

import com.google.inject.Injector;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.utils.annotations.Main;
import me.piggypiglet.framework.utils.annotations.registerable.RegisterableData;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Framework {
    private final MainBinding main;
    private final String pckg;
    private final Injector injector;
    private final List<RegisterableData> startupRegisterables;
    private final List<Class<? extends ShutdownRegisterable>> shutdownRegisterables;
    private final String commandPrefix;
    private final List<FileData> files;

    private Framework(MainBinding main, String pckg, Injector injector, List<RegisterableData> startupRegisterables, List<Class<? extends ShutdownRegisterable>> shutdownRegisterables, String commandPrefix, List<FileData> files) {
        this.main = main;
        this.pckg = pckg;
        this.injector = injector;
        this.startupRegisterables = startupRegisterables;
        this.shutdownRegisterables = shutdownRegisterables;
        this.commandPrefix = commandPrefix;
        this.files = files;
    }

    /**
     * Initialize a new instance of FrameworkBuilder
     * @return FrameworkBuilder
     */
    public static FrameworkBuilder builder() {
        return new FrameworkBuilder();
    }

    /**
     * Start the bootstrap process with the current framework configuration.
     */
    public void init() {
        new FrameworkBootstrap(this);
    }

    /**
     * Get the main instance
     * @return MainBinding
     */
    public MainBinding getMain() {
        return main;
    }

    /**
     * Get the projects package
     * @return String
     */
    public String getPckg() {
        return pckg;
    }

    /**
     * Get the project's initial injector
     * @return Injector
     */
    public Injector getInjector() {
        return injector;
    }

    /**
     * Get all manually inputted StartupRegisterables
     * @return Classes extending StartupRegisterable
     */
    public List<RegisterableData> getStartupRegisterables() {
        return startupRegisterables;
    }

    /**
     * Get all manually inputted ShutdownRegisterables
     * @return Classes extending ShutdownRegisterable
     */
    public List<Class<? extends ShutdownRegisterable>> getShutdownRegisterables() {
        return shutdownRegisterables;
    }

    /**
     * Get the application's main command prefix.
     * @return String
     */
    public String getCommandPrefix() {
        return commandPrefix;
    }

    /**
     * Get information on all files that need to be made
     * @return List of FileData
     */
    public List<FileData> getFiles() {
        return files;
    }

    public static final class FrameworkBuilder {
        private Object main = "d-main";
        private String pckg = "d-pckg";
        private Injector injector = null;
        private List<RegisterableData> startupRegisterables = new ArrayList<>();
        private List<Class<? extends ShutdownRegisterable>> shutdownRegisterables = new ArrayList<>();
        private String commandPrefix = "d-commandPrefix";
        private final List<FileData> files = new ArrayList<>();

        private FrameworkBuilder() {}

        /**
         * Set the application's main instance
         * @param instance Main instance
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder main(Object instance) {
            this.main = new MainBinding(Object.class, instance, Main.class);
            return this;
        }

        /**
         * Set the application's main instance
         * @param clazz Class to bind the instance under, for example, in bukkit, you'd enter JavaPlugin.class
         * @param instance Main instance
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder main(Class clazz, Object instance) {
            this.main = new MainBinding(clazz, instance);
            return this;
        }

        /**
         * Set the application's package
         * @param pckg Application's package
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder pckg(String pckg) {
            this.pckg = pckg;
            return this;
        }

        /**
         * Set the application's initial injector, if one is already made.
         * @param injector Application's injector
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder injector(Injector injector) {
            this.injector = injector;
            return this;
        }

        /**
         * Add startup registerables to be ran in the bootstrap, in order.
         * @param registerables Varargs classes extending StartupRegisterable
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder startup(RegisterableData... registerables) {
            startupRegisterables = Arrays.asList(registerables);
            return this;
        }

        /**
         * Add shutdown registerables to be ran in the shutdown hook, in order.
         * @param registerables Varargs classes extending ShutdownRegisterable
         * @return FrameworkBuilder
         */
        @SafeVarargs
        public final FrameworkBuilder shutdown(Class<? extends ShutdownRegisterable>... registerables) {
            shutdownRegisterables = Arrays.asList(registerables);
            return this;
        }

        /**
         * The application's command prefix, to be used in command handlers.
         * @param commandPrefix String
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder commandPrefix(String commandPrefix) {
            this.commandPrefix = commandPrefix;
            return this;
        }

        /**
         * Add a file to be copied from an embed and loaded into memory.
         * @param config Should this file be stored as a FileConfiguration
         * @param name Name of the file to be referenced in FileManager
         * @param internalPath The internal path of the file.
         * @param externalPath The external path of the file, set to null if file shouldn't be copied outside the jar.
         * @param annotation Annotation to bind the instance to.
         * @return FrameworkBuilder
         */
        public final FrameworkBuilder file(boolean config, String name, String internalPath, String externalPath, Class<? extends Annotation> annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        /**
         * Compile all the user-set options into an instance of Framework
         * NOTE: Will crash if any of the following aren't set:
         * - main
         * - package
         * - command prefix
         * @return Framework instance
         */
        public final Framework build() {
            String unsetVars = Stream.of(main, pckg, commandPrefix).filter(o -> {
                try {
                    return ((String) o).startsWith("d-");
                } catch (Exception e) {
                    return false;
                }
            }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

            if (!unsetVars.isEmpty()) throw new RuntimeException("These required vars weren't set in your FrameworkBuilder: " + unsetVars);

            return new Framework((MainBinding) main, pckg, injector, startupRegisterables, shutdownRegisterables, commandPrefix, files);
        }
    }
}
