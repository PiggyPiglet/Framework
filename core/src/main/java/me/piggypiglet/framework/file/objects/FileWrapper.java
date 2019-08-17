package me.piggypiglet.framework.file.objects;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileWrapper {
    private final File file;
    private final String fileContent;

    public FileWrapper(File file, String fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

    /**
     * Get the stored File instance
     * @return Stored file instance
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the stored raw content of the file as a String
     * @return File Content
     */
    public String getFileContent() {
        return fileContent;
    }
}