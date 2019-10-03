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

package me.piggypiglet.framework.file.implementations.json;

import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.json.JsonParser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class JsonFileConfiguration extends AbstractFileConfiguration {
    private JsonParser parser;

    public JsonFileConfiguration() {
        super(s -> s.endsWith(".json"));
    }

    public JsonFileConfiguration(Map<String, Object> items) {
        this();
        this.parser = new JsonParser(items);
    }

    private JsonFileConfiguration(JsonParser parser) {
        this();
        this.parser = parser;
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        parser = new JsonParser(fileContent);
    }

    @Override
    public Object get(String path) {
        return parser.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return new JsonFileConfiguration(parser.getJsonSection(path));
    }

    @Override
    public String getString(String path) {
        return parser.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return parser.getInt(path);
    }

    @Override
    public Long getLong(String path) {
        return parser.getLong(path);
    }

    @Override
    public Double getDouble(String path) {
        return parser.getDouble(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return parser.getBoolean(path);
    }

    @Override
    public final List<String> getStringList(String path) {
        return parser.getStringList(path);
    }

    @Override
    public final List<FileConfiguration> getConfigList(String path) {
        List<JsonParser> jsons = parser.getJsonList(path);

        // the npe shouldn't be caused in this class
        if (jsons == null) {
            return null;
        }

        return jsons.stream().map(JsonFileConfiguration::new).collect(Collectors.toList());
    }

    @Override
    public final List<?> getList(String path) {
        return parser.getList(path);
    }

    @Override
    protected Map<String, Object> retrieveAll() {
        return parser.getAll();
    }
}