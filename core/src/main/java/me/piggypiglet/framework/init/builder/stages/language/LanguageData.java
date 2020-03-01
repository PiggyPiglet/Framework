package me.piggypiglet.framework.init.builder.stages.language;

import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileEnumPair;
import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileMappings;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class LanguageData {
    private final boolean overrideDefault;
    private final Set<LanguageFileMappings> mappings;
    private final LanguageFileEnumPair custom;

    LanguageData(final boolean overrideDefault, @NotNull final Set<LanguageFileMappings> mappings,
                 @NotNull final LanguageFileEnumPair custom) {
        this.overrideDefault = overrideDefault;
        this.mappings = mappings;
        this.custom = custom;
    }

    public boolean isOverrideDefault() {
        return overrideDefault;
    }

    @NotNull
    public Set<LanguageFileMappings> getMappings() {
        return mappings;
    }

    @NotNull
    public LanguageFileEnumPair getCustom() {
        return custom;
    }
}
