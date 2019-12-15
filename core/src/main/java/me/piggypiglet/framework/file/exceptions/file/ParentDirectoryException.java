package me.piggypiglet.framework.file.exceptions.file;

import me.piggypiglet.framework.file.exceptions.RPFFileException;

public final class ParentDirectoryException extends RPFFileException {
    public ParentDirectoryException(String message) {
        super(message);
    }
}
