package me.piggypiglet.framework.init.builder.stages.lang;

import me.piggypiglet.framework.init.builder.stages.lang.objects.LangFileEnumPair;
import me.piggypiglet.framework.init.builder.stages.lang.objects.LangFileMappings;
import me.piggypiglet.framework.lang.framework.LangEnum;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LangBuilder<R> extends AbstractBuilder<LangData, R> {
    private boolean overrideDefault = false;
    private final Set<LangFileMappings> mappings = new HashSet<>();
    private LangFileEnumPair custom;

    @NotNull
    public LangBuilder<R> overrideDefault(final boolean value) {
        overrideDefault = value;
        return this;
    }

    @NotNull
    public Maps.Builder<String, String, String, LangBuilder<R>> map(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), mappings -> {
            this.mappings.add(new LangFileMappings(config, mappings));
            return this;
        });
    }

    @NotNull
    public LangBuilder<R> map(@NotNull final String config, @NotNull final Map<String, String> mappings) {
        this.mappings.add(new LangFileMappings(config, mappings));
        return this;
    }

    @NotNull
    public LangBuilder<R> custom(@NotNull final String config, @NotNull final Class<? extends Enum<? extends LangEnum>> langEnum) {
        return custom(config, (LangEnum[]) langEnum.getEnumConstants());
    }

    @NotNull
    public LangBuilder<R> custom(@NotNull final String config, @NotNull final LangEnum... enums) {
        this.custom = new LangFileEnumPair(config, enums);
        return this;
    }

    @NotNull
    @Override
    protected LangData provideBuild() {
        return new LangData(overrideDefault, mappings, custom);
    }
}
