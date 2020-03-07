package me.piggypiglet.framework.addon.init;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.init.objects.LanguageData;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.init.builder.stages.file.FilesBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.registerables.ShutdownRegisterable;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.scanning.internal.objects.ScanningRequest;
import me.piggypiglet.framework.utils.annotations.internal.InternalAnnotations;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public final class AddonBuilder<R> extends AbstractBuilder<AddonData, R> {
    private final Addon addon;
    private FilesData files = null;
    private final Multimap<BootPriority, Class<? extends StartupRegisterable>> startup = ArrayListMultimap.create();
    private final List<Class<? extends ShutdownRegisterable>> shutdown = new ArrayList<>();
    private final Set<ScanningRequest> requests = new HashSet<>();
    private LanguageData language = null;

    public AddonBuilder(@NotNull final Addon addon) {
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @NotNull
    public <T> AddonBuilder<R> request(@NotNull final String annotation, @NotNull final Function<Scanner, Set<T>> function) {
        requests.add(new ScanningRequest(new AnnotationWrapper(InternalAnnotations.internal(annotation)), new TypeLiteral<Set<T>>(){}.getType(),
                (Function) function));
        return this;
    }

    @NotNull
    public AddonBuilder<R> language(@NotNull final String file, @NotNull final Class<? extends Enum<? extends LanguageEnum>> clazz) {
        language = new LanguageData(file, clazz);
        return this;
    }

    @NotNull
    @Override
    protected AddonData provideBuild() {
        return new AddonData(addon, files, startup, shutdown, requests, language);
    }
}
