package me.piggypiglet.framework.lang;

import me.piggypiglet.framework.lang.objects.LangConfig;

import javax.inject.Inject;

public class LanguageGetter {
    @Inject private static LangConfig config;

    private LanguageGetter() {}

    public static String get(String path) {
        return config.get(path);
    }
}
