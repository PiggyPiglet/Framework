package me.piggypiglet.framework.lang;

import com.google.inject.Singleton;
import me.piggypiglet.framework.lang.objects.LangConfig;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public final class Lang {
    private final Set<LangEnum> values = new HashSet<>();
    private final Map<String, Set<LangEnum>> specificValues = new HashMap<>();

    public enum Values implements LangEnum {
        // Commands
        INCORRECT_USAGE("commands.incorrect_usage"),
        NO_PERMISSION("commands.no_permission"),
        UNKNOWN_COMMAND("commands.unknown_command");

        private final String path;

        Values(String path) {
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public String toString() {
            return LanguageGetter.get(path);
        }
    }

    public static class LanguageGetter {
        @Inject private static LangConfig config;

        public static String get(String path) {
            return config.get(path);
        }
    }

    public Set<LangEnum> getValues() {
        return values;
    }

    public Map<String, Set<LangEnum>> getSpecificValues() {
        return specificValues;
    }
}
