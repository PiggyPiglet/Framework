package me.piggypiglet.framework.utils.builder;

import javax.annotation.Nonnull;
import java.util.function.Function;

public abstract class AbstractBuilder<D, R> {
    private Object[] requiredVariables = null;
    private Object[] warningVariables = null;
    private Function<D, R> builder = null;

    void setBuilder(@Nonnull final Function<D, R> builder) {
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

    protected final class Options {
        public Options requiredVariables(@Nonnull final Object... variables) {
            requiredVariables = variables;
            return this;
        }

        public Options warningVariables(@Nonnull final Object... variables) {
            warningVariables = variables;
            return this;
        }
    }
}
