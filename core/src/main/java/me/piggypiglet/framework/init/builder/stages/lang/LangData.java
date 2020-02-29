package me.piggypiglet.framework.init.builder.stages.lang;

import me.piggypiglet.framework.init.builder.stages.lang.objects.LangFileEnumPair;
import me.piggypiglet.framework.init.builder.stages.lang.objects.LangFileMappings;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class LangData {
    private final boolean overrideDefault;
    private final Set<LangFileMappings> mappings;
    private final LangFileEnumPair custom;

    LangData(final boolean overrideDefault, @NotNull final Set<LangFileMappings> mappings,
             @NotNull final LangFileEnumPair custom) {
        this.overrideDefault = overrideDefault;
        this.mappings = mappings;
        this.custom = custom;
    }

    public boolean isOverrideDefault() {
        return overrideDefault;
    }

    @NotNull
    public Set<LangFileMappings> getMappings() {
        return mappings;
    }

    @NotNull
    public LangFileEnumPair getCustom() {
        return custom;
    }
}
