package me.piggypiglet.framework.init.builder.stages.file;

import com.google.inject.name.Names;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.utils.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public final class FilesBuilder<R> extends AbstractBuilder<FilesData, R> {
    private String fileDir = ".";
    private final List<FileData> files = new ArrayList<>();

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
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final String externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath) {
        return config(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, internalPath, null, externalPath, null, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, null, internalPath, externalPath, null, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, internalPath, null, null, externalPath, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(true, name, null, internalPath, null, externalPath, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(true, name, internalPath, null, externalPath, null, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(true, name, null, internalPath, externalPath, null, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final String internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(true, name, internalPath, null, null, externalPath, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> config(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                  @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(true, name, null, internalPath, null, externalPath, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final String externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath) {
        return file(name, internalPath, externalPath, Names.named(name));
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, internalPath, null, externalPath, null, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final String externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, null, internalPath, externalPath, null, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, internalPath, null, null, externalPath, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Class<? extends Annotation> annotation) {
        return data(false, name, null, internalPath, null, externalPath, annotation, null);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(false, name, internalPath, null, externalPath, null, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final String externalPath, @NotNull final Annotation annotation) {
        return data(false, name, null, internalPath, externalPath, null, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final String internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(false, name, internalPath, null, null, externalPath, null, annotation);
    }

    @NotNull
    public FilesBuilder<R> file(@NotNull final String name, @NotNull final FileData.ConfigPathReference internalPath,
                                @NotNull final FileData.ConfigPathReference externalPath, @NotNull final Annotation annotation) {
        return data(false, name, null, internalPath, null, externalPath, null, annotation);
    }

    @NotNull
    private FilesBuilder<R> data(final boolean config, @NotNull final String name,
                                 @Nullable final String hardInternalPath, @Nullable final FileData.ConfigPathReference internalPathReference,
                                 @Nullable final String hardExternalPath, @Nullable final FileData.ConfigPathReference externalPathReference,
                                 @Nullable final Class<? extends Annotation> annotationClass, @Nullable final Annotation annotationInstance) {
        files.add(new FileData(config, name, hardInternalPath, internalPathReference, hardExternalPath, externalPathReference,
                annotationClass, annotationInstance));
        return this;
    }

    @Override
    protected FilesData provideBuild() {
        return new FilesData(fileDir, files);
    }
}
