package me.piggypiglet.framework;

import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Framework {
    private final Class main;
    private final String pckg;
    private final List<Class<? extends StartupRegisterable>> startupRegisterables;
    private final List<Class<? extends ShutdownRegisterable>> shutdownRegisterables;
    private final String commandPrefix;

    private Framework(Class main, String pckg, List<Class<? extends StartupRegisterable>> startupRegisterables, List<Class<? extends ShutdownRegisterable>> shutdownRegisterables, String commandPrefix) {
        this.main = main;
        this.pckg = pckg;
        this.startupRegisterables = startupRegisterables;
        this.shutdownRegisterables = shutdownRegisterables;
        this.commandPrefix = commandPrefix;
    }

    public static FrameworkBuilder builder() {
        return new FrameworkBuilder();
    }

    public void init() {
        new FrameworkBootstrap(this);
    }

    public Class getMain() {
        return main;
    }

    public String getPckg() {
        return pckg;
    }

    public List<Class<? extends StartupRegisterable>> getStartupRegisterables() {
        return startupRegisterables;
    }

    public List<Class<? extends ShutdownRegisterable>> getShutdownRegisterables() {
        return shutdownRegisterables;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public static final class FrameworkBuilder {
        private Object main = "d-main";
        private String pckg = "d-pckg";
        private List<Class<? extends StartupRegisterable>> startupRegisterables = null;
        private List<Class<? extends ShutdownRegisterable>> shutdownRegisterables = null;
        private String commandPrefix = "d-commandPrefix";

        private FrameworkBuilder() {}

        public final FrameworkBuilder main(Class main) {
            this.main = main;
            return this;
        }

        public final FrameworkBuilder pckg(String pckg) {
            this.pckg = pckg;
            return this;
        }

        @SafeVarargs
        public final FrameworkBuilder startup(Class<? extends StartupRegisterable>... registerables) {
            startupRegisterables = Arrays.asList(registerables);
            return this;
        }

        @SafeVarargs
        public final FrameworkBuilder shutdown(Class<? extends ShutdownRegisterable>... registerables) {
            shutdownRegisterables = Arrays.asList(registerables);
            return this;
        }

        public final FrameworkBuilder commandPrefix(String commandPrefix) {
            this.commandPrefix = commandPrefix;
            return this;
        }

        public Framework build() {
            String unsetVars = Stream.of(main, pckg, commandPrefix).filter(o -> {
                try {
                    return ((String) o).startsWith("d-");
                } catch (Exception e) {
                    return false;
                }
            }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

            if (!unsetVars.isEmpty()) throw new RuntimeException("These required vars weren't set in your FrameworkBuilder: " + unsetVars);

            return new Framework((Class) main, pckg, startupRegisterables, shutdownRegisterables, commandPrefix);
        }
    }
}
