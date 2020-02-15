package me.piggypiglet.framework.utils.builder;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class AbstractBuilder<D, R> {
    private Function<D, R> builder = null;

    void setBuilder(@NotNull final Function<D, R> builder) {
        this.builder = builder;
    }

    protected abstract D provideBuild();

    @SuppressWarnings("unchecked")
    public R build() {
        final D build = provideBuild();

        if (builder != null) {
            return builder.apply(build);
        }

        return (R) build;
    }
}
