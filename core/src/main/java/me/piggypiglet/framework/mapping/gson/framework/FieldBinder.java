package me.piggypiglet.framework.mapping.gson.framework;

import me.piggypiglet.framework.mapping.gson.objects.FieldBind;
import org.jetbrains.annotations.NotNull;

public interface FieldBinder {
    @NotNull
    FieldBind get(@NotNull final String input);
}
