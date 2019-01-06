package me.piggypiglet.framework;

import com.google.inject.Injector;
import lombok.Getter;
import me.piggypiglet.framework.dependencies.DependencyLoader;
import me.piggypiglet.framework.dependencies.MavenLibraries;
import me.piggypiglet.framework.dependencies.MavenLibrary;
import me.piggypiglet.framework.dependencies.guice.BinderModule;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@MavenLibraries(
        value = {
                @MavenLibrary(groupId = "org.reflections", artifactId = "reflections", version = "0.9.11"),
                @MavenLibrary(groupId = "javassist", artifactId = "javassist", version = "3.12.1.GA"),
                @MavenLibrary(groupId = "com.google.inject", artifactId = "guice", version = "4.2.2"),
                @MavenLibrary(groupId = "javax.inject", artifactId = "javax.inject", version = "1"),
                @MavenLibrary(groupId = "aopalliance", artifactId = "aopalliance", version = "1.0"),
                @MavenLibrary(groupId = "org.apache.commons", artifactId = "commons-lang3", version = "3.8.1"),
                @MavenLibrary(groupId = "commons-io", artifactId = "commons-io", version = "2.6"),
                @MavenLibrary(groupId = "mysql", artifactId = "mysql-connector-java", version = "5.1.33"),
                @MavenLibrary(groupId = "co.aikar", artifactId = "idb-core", version = "1.0.0-SNAPSHOT", repo = "https://repo.aikar.co/content/groups/aikar/"),
                @MavenLibrary(groupId = "com.zaxxer", artifactId = "HikariCP", version = "2.4.1")
        }
)
public final class Framework {
    @Getter private static String programName;
    @Getter private static Object main;

    public Framework(Object main, String programName) {
        Framework.programName = programName;
        Framework.main = main;

        DependencyLoader.loadAll(main.getClass());
    }

    public Reflections getReflections(String packaj) {
        return new Reflections(packaj);
    }

    public Injector initGuice(Class main, Class... staticInjections) {
        Injector injector = new BinderModule(main, staticInjections).createInjector();
        injector.injectMembers(Framework.main);

        return injector;
    }
}
