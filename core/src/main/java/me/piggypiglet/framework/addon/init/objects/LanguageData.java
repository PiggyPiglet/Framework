package me.piggypiglet.framework.addon.init.objects;

import me.piggypiglet.framework.language.framework.LanguageEnum;
import org.jetbrains.annotations.NotNull;

public final class LanguageData {
    private final String file;
    private final Class<? extends Enum<? extends LanguageEnum>> clazz;

    public LanguageData(@NotNull final String file, @NotNull final Class<? extends Enum<? extends LanguageEnum>> clazz) {
        this.file = file;
        this.clazz = clazz;
    }

    @NotNull
    public String getFile() {
        return file;
    }

    @NotNull
    public Class<? extends Enum<? extends LanguageEnum>> getClazz() {
        return clazz;
    }
}
