package me.piggypiglet.framework.bungeecord.file.api;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class YamlConfiguration {
    private static final ThreadLocal<Yaml> YAML = ThreadLocal.withInitial(() -> {
        Representer representer = new Representer() {{
            representers.put( Configuration.class, data -> represent( ( (Configuration) data ).self ));
        }};

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );

        return new Yaml( new Constructor(), representer, options );
    });

    public static Configuration load(File file) throws IOException {
        try (FileInputStream is = new FileInputStream(file)) {
            Map<String, Object> map = YAML.get().loadAs(is, LinkedHashMap.class);

            if (map == null) {
                map = new LinkedHashMap<>();
            }

            return new Configuration(map, null);
        }
    }
}
