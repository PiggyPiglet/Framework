package me.piggypiglet.framework.addon.builders;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.piggypiglet.framework.addon.builders.objects.LangData;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.builder.stages.file.FilesBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.lang.framework.LangEnum;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AddonBuilder<R> extends AbstractBuilder<AddonData, R> {
    private final Addon<?> addon;
    private FilesData files = null;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup = ArrayListMultimap.create();
    private final List<Class<? extends ShutdownRegisterable>> shutdown = new ArrayList<>();
    private LangData lang = null;

    public AddonBuilder(@NotNull final Addon<?> addon) {
        this.addon = addon;
    }

    @NotNull
    public FilesBuilder<AddonBuilder<R>> files() {
        return BuilderUtils.customBuilder(new FilesBuilder<>(), data -> {
            files = data;
            return this;
        });
    }

    @SafeVarargs
    @NotNull
    public final AddonBuilder<R> startup(@NotNull final Class<? extends StartupRegisterable>... registerables) {
        return startup(BootPriority.MANUAL, registerables);
    }

    @SafeVarargs
    @NotNull
    public final AddonBuilder<R> startup(@NotNull final BootPriority priority, @NotNull final Class<? extends StartupRegisterable>... registerables) {
        Arrays.stream(registerables).forEach(registerable -> startup.put(priority, registerable));
        return this;
    }

    @SafeVarargs
    @NotNull
    public final AddonBuilder<R> shutdown(@NotNull final Class<? extends ShutdownRegisterable>... registerables) {
        shutdown.addAll(Arrays.asList(registerables));
        return this;
    }

    @NotNull
    public AddonBuilder<R> lang(@NotNull final String file, @NotNull final Class<? extends Enum<? extends LangEnum>> clazz) {
        lang = new LangData(file, clazz);
        return this;
    }

    @NotNull
    @Override
    protected AddonData provideBuild() {
        return new AddonData(addon, files, startup, shutdown, lang);
    }
}
