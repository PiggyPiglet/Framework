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

package me.piggypiglet.framework.nukkit.file;

import cn.nukkit.utils.Config;
import com.google.common.io.Files;
import com.google.gson.GsonBuilder;
import me.piggypiglet.framework.file.framework.implementations.map.MapFileConfiguration;
import me.piggypiglet.framework.utils.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public final class NukkitFileConfiguration extends MapFileConfiguration {
    public NukkitFileConfiguration() {
        super(s -> StringUtils.anyEndWith(s, ".properties", ".con", ".conf", ".config", ".yml", ".yaml", ".txt", ".list", ".enum"));
    }

    @Override
    protected Map<String, Object> provide(File file, String fileContent) {
        return new Config(file).getAll();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected String convert(Map<String, Object> items) {
        StringBuilder content = new StringBuilder();

        // https://github.com/NukkitX/Nukkit/blob/master/src/main/java/cn/nukkit/utils/Config.java#L216-L254
        switch (Config.format.get(Files.getFileExtension(getFile().getName()))) {
            case Config.PROPERTIES:
                content = new StringBuilder("#Properties Config file\r\n#" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\r\n");

                for (Object o : items.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    Object v = entry.getValue();
                    Object k = entry.getKey();
                    if (v instanceof Boolean) {
                        v = (Boolean) v ? "on" : "off";
                    }
                    content.append(k).append("=").append(v).append("\r\n");
                }
                break;

            case Config.JSON:
                content = new StringBuilder(new GsonBuilder().setPrettyPrinting().create().toJson(items));
                break;

            case Config.YAML:
                DumperOptions dumperOptions = new DumperOptions();
                dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Yaml yaml = new Yaml(dumperOptions);
                content = new StringBuilder(yaml.dump(items));
                break;

            case Config.ENUM:
                for (Object o : items.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    content.append(entry.getKey()).append("\r\n");
                }
                break;
        }

        return content.toString();
    }
}
