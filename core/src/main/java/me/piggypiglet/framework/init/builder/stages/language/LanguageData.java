package me.piggypiglet.framework.init.builder.stages.language;

import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileEnumPair;
import org.jetbrains.annotations.NotNull;

public final class LanguageData {
    private final boolean overrideDefault;
    private final LanguageFileEnumPair custom;

    LanguageData(final boolean overrideDefault, @NotNull final LanguageFileEnumPair custom) {
        this.overrideDefault = overrideDefault;
        this.custom = custom;
    }

    public boolean isOverrideDefault() {
        return overrideDefault;
    }

    @NotNull
    public LanguageFileEnumPair getCustom() {
        return custom;
    }
}
