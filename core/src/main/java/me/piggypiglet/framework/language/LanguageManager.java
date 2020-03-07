package me.piggypiglet.framework.language;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public final class LanguageManager {
    private static final String NULL = "null";

    private final Map<String, Table<LanguageEnum, String, String>> languages = new HashMap<>();
    private final Collection<Table<LanguageEnum, String, String>> values = languages.values();

    @NotNull
    public Table<LanguageEnum, String, String> getLanguage(@NotNull final String language) {
        return languages.get(language);
    }

    @NotNull
    public String getValue(@NotNull final String path) {
        return getValue(path, true);
    }

    @NotNull
    public String getValue(@NotNull final LanguageEnum key) {
        return getValue(key, false);
    }

    @NotNull
    private <T> String getValue(@NotNull final T key, final boolean path) {
        return values.stream()
                .flatMap(table -> (path ? table.rowMap() : table.columnMap()).entrySet().stream())
                .filter(entry -> entry.getKey().equals(key))
                .findAny().map(e -> Iterables.getFirst(e.getValue().values(), NULL)).orElse(NULL);
    }

    public void overwriteLanguage(@NotNull final String language, @NotNull final Table<LanguageEnum, String, String> data) {
        languages.put(language, ImmutableTable.copyOf(data));
    }

    public void populate(@NotNull final Map<String, Table<LanguageEnum, String, String>> languages) {
        this.languages.clear();
        this.languages.putAll(languages);
    }
}
