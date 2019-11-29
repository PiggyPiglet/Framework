package me.piggypiglet.framework.sponge.file;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.implementations.json.JsonFileConfiguration;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SpongeYamlFileConfiguration extends AbstractFileConfiguration {
    private JsonFileConfiguration config;

    public SpongeYamlFileConfiguration() {
        super(s -> s.endsWith(".yml") || s.endsWith(".yaml"));
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        try {
            Object obj = YAMLConfigurationLoader.builder()
                    .setFile(file)
                    .setFlowStyle(DumperOptions.FlowStyle.BLOCK)
                    .build().load().getValue();

            System.out.println(obj);

            config = new JsonFileConfiguration((Map<String, Object>) obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // todo: implement this
    @Override
    protected Map<String, Object> retrieveAll() {
        return new HashMap<>();
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return config.getConfigSection(path);
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public Long getLong(String path) {
        return config.getLong(path);
    }

    @Override
    public Double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public List<FileConfiguration> getConfigList(String path) {
        return config.getConfigList(path);
    }

    @Override
    public List<?> getList(String path) {
        return config.getList(path);
    }
}
