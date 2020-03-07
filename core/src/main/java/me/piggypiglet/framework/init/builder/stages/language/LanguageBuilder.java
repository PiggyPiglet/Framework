package me.piggypiglet.framework.init.builder.stages.language;

import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileEnumPair;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

public final class LanguageBuilder<R> extends AbstractBuilder<LanguageData, R> {
    private boolean overrideDefault = false;
    private LanguageFileEnumPair custom;

    @NotNull
    public LanguageBuilder<R> overrideDefault(final boolean value) {
        overrideDefault = value;
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
        return new LanguageData(overrideDefault, custom);
    }
}
