package me.piggypiglet.framework;

import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;

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
    private final String pckg;
    private final List<Class<? extends StartupRegisterable>> startupRegisterables;
    private final List<Class<? extends ShutdownRegisterable>> shutdownRegisterables;
    private final String commandPrefix;
    private final List<FileData> files;

    private Framework(String pckg, List<Class<? extends StartupRegisterable>> startupRegisterables, List<Class<? extends ShutdownRegisterable>> shutdownRegisterables, String commandPrefix, List<FileData> files) {
        this.pckg = pckg;
        this.startupRegisterables = startupRegisterables;
        this.shutdownRegisterables = shutdownRegisterables;
        this.commandPrefix = commandPrefix;
        this.files = files;
    }

    public static FrameworkBuilder builder() {
        return new FrameworkBuilder();
    }

    public void init() {
        new FrameworkBootstrap(this);
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

    public List<FileData> getFiles() {
        return files;
    }

    public static final class FrameworkBuilder {
        private String pckg = "d-pckg";
        private List<Class<? extends StartupRegisterable>> startupRegisterables = new ArrayList<>();
        private List<Class<? extends ShutdownRegisterable>> shutdownRegisterables = new ArrayList<>();
        private String commandPrefix = "d-commandPrefix";
        private final List<FileData> files = new ArrayList<>();

        private FrameworkBuilder() {}

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

        public final FrameworkBuilder file(boolean config, String name, String internalPath, String externalPath, Class<? extends Annotation> annotation) {
            files.add(new FileData(config, name, internalPath, externalPath, annotation));
            return this;
        }

        public final Framework build() {
            String unsetVars = Stream.of(pckg, commandPrefix).filter(o -> {
                try {
                    return ((String) o).startsWith("d-");
                } catch (Exception e) {
                    return false;
                }
            }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

            if (!unsetVars.isEmpty()) throw new RuntimeException("These required vars weren't set in your FrameworkBuilder: " + unsetVars);

            return new Framework(pckg, startupRegisterables, shutdownRegisterables, commandPrefix, files);
        }
    }
}
