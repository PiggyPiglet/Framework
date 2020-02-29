package me.piggypiglet.framework.addon.builders.objects;

import me.piggypiglet.framework.lang.framework.LangEnum;
import org.jetbrains.annotations.NotNull;

public final class LangData {
    private final String file;
    private final Class<? extends Enum<? extends LangEnum>> clazz;

    public LangData(@NotNull final String file, @NotNull final Class<? extends Enum<? extends LangEnum>> clazz) {
        this.file = file;
        this.clazz = clazz;
    }

    @NotNull
    public String getFile() {
        return file;
    }

    @NotNull
    public Class<? extends Enum<? extends LangEnum>> getClazz() {
        return clazz;
    }
}
