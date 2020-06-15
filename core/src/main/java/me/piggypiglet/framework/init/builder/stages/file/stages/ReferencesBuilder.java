package me.piggypiglet.framework.init.builder.stages.file.stages;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ReferencesBuilder<R> extends AbstractBuilder<Table<String, String, String>, R> {
    private final Table<String, String, String> references = HashBasedTable.create();

    @NotNull
    public Maps.Builder<String, String, ReferencesBuilder<R>> references(@NotNull final String config) {
        return Maps.builder(new HashMap<>(), map -> {
            map.forEach((base, reference) -> references.put(config, base, reference));
            return this;
        });
    }

    @NotNull
    public ReferencesBuilder<R> reference(@NotNull final String config, @NotNull final Map<String, String> references) {
        references.forEach((base, reference) -> this.references.put(config, base, reference));
        return this;
    }

    @Override
    protected Table<String, String, String> provideBuild() {
        return references;
    }
}
