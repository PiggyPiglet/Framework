package me.piggypiglet.framework.init.builder.stages.language;

import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileEnumPair;
import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileMappings;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LanguageBuilder<R> extends AbstractBuilder<LanguageData, R> {
    private boolean overrideDefault = false;
    private final Set<LanguageFileMappings> mappings = new HashSet<>();
    private LanguageFileEnumPair custom;

    @NotNull
    public LanguageBuilder<R> overrideDefault(final boolean value) {
        overrideDefault = value;
        return this;
    }

    @NotNull
    public Maps.Builder<String, String, String, LanguageBuilder<R>> map(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), mappings -> {
            this.mappings.add(new LanguageFileMappings(config, mappings));
            return this;
        });
    }

    @NotNull
    public LanguageBuilder<R> map(@NotNull final String config, @NotNull final Map<String, String> mappings) {
        this.mappings.add(new LanguageFileMappings(config, mappings));
        return this;
    }

    @NotNull
    public LanguageBuilder<R> custom(@NotNull final String config, @NotNull final Class<? extends Enum<? extends LanguageEnum>> langEnum) {
        return custom(config, (LanguageEnum[]) langEnum.getEnumConstants());
    }

    @NotNull
    public LanguageBuilder<R> custom(@NotNull final String config, @NotNull final LanguageEnum... enums) {
        this.custom = new LanguageFileEnumPair(config, enums);
        return this;
    }

    @NotNull
    @Override
    protected LanguageData provideBuild() {
        return new LanguageData(overrideDefault, mappings, custom);
    }
}
