package me.piggypiglet.framework.language;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Singleton
public final class LanguageManager {
    private final Map<String, Map<String, String>> languages = new HashMap<>();

    @NotNull
    public Map<String, String> getLanguage(@NotNull final String language) {
        return languages.get(language);
    }

    public void overwriteLanguage(@NotNull final String language, @NotNull final Map<String, String> data) {
        languages.put(language, ImmutableMap.copyOf(data));
    }
}
