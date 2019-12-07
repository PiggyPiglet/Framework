/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.bukkit.file;

import me.piggypiglet.framework.file.framework.implementations.map.MapFileConfiguration;
import me.piggypiglet.framework.file.framework.objects.Flattener;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.util.Map;

public final class SpigotFileConfiguration extends MapFileConfiguration {
    private static final String BLANK_CONFIG = "{}\n";
    private static final DumperOptions OPTIONS = new DumperOptions();
    private static final Representer REPRESENTER = new YamlRepresenter();

    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> new Yaml(new YamlConstructor(), REPRESENTER, OPTIONS));

    static {
        OPTIONS.setIndent(4);
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        REPRESENTER.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }

    public SpigotFileConfiguration() {
        super(s -> s.endsWith(".yml"), Flattener.builder(MemorySection.class).flattener(m -> m.getValues(true)).build());
    }

    @Override
    protected Map<String, Object> provide(File file, String fileContent) {
        final FileConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return config.getValues(true);
    }

    @Override
    protected String convert(Map<String, Object> items) {
        String dump = yaml.get().dump(items);

        if (dump.equals(BLANK_CONFIG)) {
            dump = "";
        }

        return dump;
    }
}
