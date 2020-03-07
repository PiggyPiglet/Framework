package me.piggypiglet.framework.file.objects;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ConfigData {
    private Map<String, String> values = new HashMap<>();
    private Table<String, String, String> references = HashBasedTable.create();

    @NotNull
    public Map<String, String> getValues() {
        return values;
    }

    @NotNull
    public Table<String, String, String> getReferences() {
        return references;
    }

    public void setValues(@NotNull final Map<String, String> values) {
        this.values = values;
    }

    public void setReferences(@NotNull final Table<String, String, String> references) {
        this.references = references;
    }
}
