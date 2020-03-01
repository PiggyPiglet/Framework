package me.piggypiglet.framework.init.builder.stages.language.objects;

import me.piggypiglet.framework.language.framework.LanguageEnum;
import org.jetbrains.annotations.NotNull;

public final class LanguageFileEnumPair {
    private final String config;
    private final LanguageEnum[] enums;

    public LanguageFileEnumPair(@NotNull final String config, @NotNull final LanguageEnum[] enums) {
        this.config = config;
        this.enums = enums;
    }

    @NotNull
    public String getConfig() {
        return config;
    }

    @NotNull
    public LanguageEnum[] getEnums() {
        return enums;
    }
}
