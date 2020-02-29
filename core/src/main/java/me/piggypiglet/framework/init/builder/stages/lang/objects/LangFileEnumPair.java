package me.piggypiglet.framework.init.builder.stages.lang.objects;

import me.piggypiglet.framework.lang.framework.LangEnum;
import org.jetbrains.annotations.NotNull;

public final class LangFileEnumPair {
    private final String config;
    private final LangEnum[] enums;

    public LangFileEnumPair(@NotNull final String config, @NotNull final LangEnum[] enums) {
        this.config = config;
        this.enums = enums;
    }

    @NotNull
    public String getConfig() {
        return config;
    }

    @NotNull
    public LangEnum[] getEnums() {
        return enums;
    }
}
