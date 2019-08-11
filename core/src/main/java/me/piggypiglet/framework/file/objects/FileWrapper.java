package me.piggypiglet.framework.file.objects;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileWrapper {
    private final java.io.File file;
    private final String fileContent;

    public FileWrapper(java.io.File file, String fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

    public java.io.File getFile() {
        return file;
    }

    public String getFileContent() {
        return fileContent;
    }
}