package me.piggypiglet.framework.lang.objects;

import me.piggypiglet.framework.lang.LangEnum;

public final class CustomLang {
    private final String config;
    private final LangEnum[] values;

    public CustomLang(final String config, final LangEnum[] values) {
        this.config = config;
        this.values = values;
    }

    public String getConfig() {
        return config;
    }

    public LangEnum[] getValues() {
        return values;
    }
}
