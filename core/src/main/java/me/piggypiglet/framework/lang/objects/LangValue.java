package me.piggypiglet.framework.lang.objects;

import me.piggypiglet.framework.lang.Lang;

public final class LangValue {
    private final String path;

    public LangValue(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return Lang.LanguageGetter.get(path);
    }
}
