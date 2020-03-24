package me.piggypiglet.framework.minecraft.api.inventory.builder;

import me.piggypiglet.framework.minecraft.api.inventory.framework.Inventory;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;

public class InventoryBuilder<T extends InventoryBuilder<T, R>, R> extends AbstractBuilder<Inventory, R> {
    @SuppressWarnings("unchecked") private final T instance = (T) this;

    protected String name;

    @NotNull
    public T name(@NotNull final String name) {
        this.name = name;
        return instance;
    }

    @Override
    protected Inventory provideBuild() {
        return null;
    }
}
