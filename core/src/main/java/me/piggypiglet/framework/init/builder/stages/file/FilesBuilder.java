package me.piggypiglet.framework.init.builder.stages.file;

import com.google.common.collect.Table;
import com.google.inject.name.Names;
import me.piggypiglet.framework.file.objects.ConfigData;
import me.piggypiglet.framework.file.objects.ConfigPathReference;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.init.builder.stages.file.stages.ReferencesBuilder;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import me.piggypiglet.framework.utils.builder.BuilderUtils;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FilesBuilder<R> extends AbstractBuilder<FilesData, R> {
    private String fileDir = ".";
    // all files, including configs
    private final List<FileData> files = new ArrayList<>();
    // exclusively configs
    private final Map<String, FileData> configs = new HashMap<>();

    @NotNull
    public FilesBuilder<R> fileDir(@NotNull final String value) {
        fileDir = value;
        return this;
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final String externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final String externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final ConfigPathReference externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final ConfigPathReference externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, internalPath, null, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, null, internalPath, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, internalPath, null, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, null, internalPath, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(true, name, internalPath, null, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(true, name, null, internalPath, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(true, name, internalPath, null, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                  @NotNull final ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(true, name, null, internalPath, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public ReferencesBuilder<FilesBuilder<R>> references(@NotNull final String name) {
        return BuilderUtils.customBuilder(new ReferencesBuilder<>(), table -> {
            configData(name).setReferences(table);
            return this;
        });
    }
    
    @NotNull
    public FilesBuilder<R> references(@NotNull final String name, @NotNull final Table<String, String, String> references) {
        configData(name).setReferences(references);
        return this;
    }

    @NotNull
    public Maps.Builder<String, String, String, FilesBuilder<R>> values(@NotNull final String name) {
        return Maps.builder(new HashMap<>(), map -> {
            configData(name).setValues(map);
            return this;
        });
    }

    @NotNull
    public FilesBuilder<R> values(@NotNull final String name, @NotNull final Map<String, String> values) {
        configData(name).setValues(values);
        return this;
    }

    @NotNull
    private ConfigData configData(@NotNull final String name) {
        final FileData data = configs.get(name);

        if (data == null) {
            throw new AssertionError(name + " isn't a config.");
        }

        return data.getRelocations();
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final String externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final ConfigPathReference externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final ConfigPathReference externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, internalPath, null, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, null, internalPath, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, internalPath, null, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, null, internalPath, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(false, name, internalPath, null, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(false, name, null, internalPath, externalPath, null, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(false, name, internalPath, null, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final ConfigPathReference internalPath,
                                @NotNull final ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(false, name, null, internalPath, null, externalPath, new AnnotationWrapper(annotation));
    }

    @NotNull
    private FilesBuilder<R> data(final boolean config, @NotNull final String name,
                                 @Nullable final String hardInternalPath, @Nullable final ConfigPathReference internalPathReference,
                                 @Nullable final String hardExternalPath, @Nullable final ConfigPathReference externalPathReference,
                                 @NotNull final AnnotationWrapper annotation) {
        final FileData data = new FileData(config, name, hardInternalPath, internalPathReference, hardExternalPath,
                externalPathReference, annotation);
        files.add(data);

        if (config) {
            configs.put(name, data);
        }

        return this;
    }

    @Override
    protected FilesData provideBuild() {
        return new FilesData(fileDir, files);
    }
}
