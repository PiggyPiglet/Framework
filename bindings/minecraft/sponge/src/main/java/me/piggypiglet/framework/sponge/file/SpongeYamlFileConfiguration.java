package me.piggypiglet.framework.sponge.file;

import me.piggypiglet.framework.file.framework.implementations.map.MapFileConfiguration;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SpongeYamlFileConfiguration extends MapFileConfiguration {
    public SpongeYamlFileConfiguration() {
        super(s -> s.endsWith(".yml") || s.endsWith(".yaml"));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map<String, Object> provide(File file, String fileContent) {
        try {
             return (Map<String, Object>) YAMLConfigurationLoader.builder()
                     .setFile(file)
                     .setFlowStyle(DumperOptions.FlowStyle.BLOCK)
                     .build().load().getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}
