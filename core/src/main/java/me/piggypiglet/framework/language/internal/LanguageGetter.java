package me.piggypiglet.framework.language.internal;

import com.google.inject.Inject;
import me.piggypiglet.framework.language.LanguageManager;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import org.jetbrains.annotations.NotNull;

public final class LanguageGetter {
    @Inject private static LanguageManager languageManager;

    @NotNull
    public static String get(@NotNull final LanguageEnum key) {
        return languageManager.getValue(key);
    }
}
