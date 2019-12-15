package me.piggypiglet.framework.file.framework;

import me.piggypiglet.framework.file.exceptions.config.ConfigSaveException;

public interface MutableFileConfiguration extends FileConfiguration {
    void set(String path, Object value);

    void save() throws ConfigSaveException;
}
