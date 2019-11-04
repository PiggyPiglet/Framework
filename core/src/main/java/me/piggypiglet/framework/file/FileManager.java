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

package me.piggypiglet.framework.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Singleton
public final class FileManager {
    @Inject private FileConfigurationFactory fileConfigurationFactory;
    @Inject private Framework framework;

    private final Map<String, Object> files = new HashMap<>();

    /**
     * Load a file into the FileManager
     * @param name Name the file will be referenced by
     * @param internalPath Internal path of the file to be exported
     * @param externalPath External path to save and load the file from
     * @return Returns a file wrapper containing the file object and it's plaintext content
     * @throws Exception Will throw if IO, BadConfigType, or UnknownConfigType exceptions are encountered
     */
    public FileWrapper loadFile(String name, String internalPath, String externalPath) throws Exception {
        if (externalPath == null) {
            return putAndGet(name, new FileWrapper(null, FileUtils.readEmbedToString(internalPath)));
        }

        File file = createFile(externalPath, internalPath);

        return putAndGet(name, new FileWrapper(file, FileUtils.readFileToString(file)));
    }

    /**
     * Load a config into the FileManager
     * @param name Name the config will be referenced by
     * @param internalPath Internal path of the config to be exported
     * @param externalPath External path to save and load the config from
     * @return FileConfiguration object
     * @throws Exception Will throw if IO, BadConfigType, or UnknownConfigType exceptions are encountered
     */
    public FileConfiguration loadConfig(String name, String internalPath, String externalPath) throws Exception {
        if (externalPath == null) {
            return putAndGet(name, fileConfigurationFactory.get(internalPath, FileUtils.readEmbedToString(internalPath)));
        }

        File file = createFile(externalPath, internalPath);

        return putAndGet(name, fileConfigurationFactory.get(file));
    }

    /**
     * Get a FileConfiguration that's stored in the file manager.
     * @param name Name of the config
     * @return FileConfiguration instance, or null if the file couldn't be found, or isn't a config
     */
    public FileConfiguration getConfig(String name) {
        Object obj = files.get(name);

        if (obj instanceof FileConfiguration) {
            return (FileConfiguration) files.get(name);
        }

        return null;
    }

    /**
     * Get the raw type instance of a stored file/config (FileWrapper or FileConfiguration)
     * @param name Name of the file
     * @param <T> Type
     * @return Type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) files.get(name);
    }

    /**
     * Update instances of AbstractFileConfigurations or FileWrappers with physical changes to their respective files by the user.
     * @param name Reference to the stored object
     */
    public void update(String name) {
        Object item = get(name);

        if (item instanceof AbstractFileConfiguration) {
            AbstractFileConfiguration config = (AbstractFileConfiguration) item;
            config.load(config.getFile(), FileUtils.readFileToString(config.getFile()));
        } else {
            FileWrapper file = (FileWrapper) item;
            file.setFileContent(FileUtils.readFileToString(file.getFile()));
        }
    }

    private <T> T putAndGet(String name, T file) {
        files.put(name, file);
        return file;
    }

    private File createFile(String externalPath, String internalPath) throws Exception {
        externalPath = framework.getFileDir() + "/" + externalPath;
        File file = new File(externalPath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileUtils.exportResource(Framework.class.getResourceAsStream(internalPath), externalPath);
        }

        return file;
    }
}
