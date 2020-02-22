package me.piggypiglet.framework.init.builder.stages.file;

import me.piggypiglet.framework.file.objects.FileData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class FilesData {
    private final String fileDir;
    private final List<FileData> files;

    public FilesData(@NotNull final String fileDir, @NotNull final List<FileData> files) {
        this.fileDir = fileDir;
        this.files = files;
    }

    @NotNull
    public String getFileDir() {
        return fileDir;
    }

    @NotNull
    public List<FileData> getFiles() {
        return files;
    }
}
