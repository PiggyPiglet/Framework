package me.piggypiglet.framework.file.framework;

public interface MutableFileConfiguration extends FileConfiguration {
    void set(String path, Object value);

    void save() throws Exception;
}
