/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
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

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.file.exceptions.file.CreateFileException;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.framework.MutableFileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Singleton
public final class FileManager {
    @Inject private FileConfigurationFactory fileConfigurationFactory;
    @Inject private Framework framework;

    private final Map<String, Object> files = new HashMap<>();

    /**
     * Load a file into the FileManager
     *
     * @param name         Name the file will be referenced by
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
     *
     * @param name         Name the config will be referenced by
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
     *
     * @param name Name of the config
     * @return FileConfiguration instance, or null if the file couldn't be found, or isn't a config
     */
    public Optional<FileConfiguration> getConfig(String name) {
        Object obj = files.get(name);

        if (obj instanceof FileConfiguration) {
            return Optional.of((FileConfiguration) obj);
        }

        return Optional.empty();
    }

    /**
     * Check if a file is stored under an identifier.
     *
     * @param name Name of the file
     * @return boolean
     */
    public boolean exists(String name) {
        return files.containsKey(name);
    }

    /**
     * Get the raw type instance of a stored file/config (FileWrapper or FileConfiguration)
     *
     * @param name Name of the file
     * @param <T>  Type
     * @return Type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) files.get(name);
    }

    /**
     * Update instances of AbstractFileConfigurations or FileWrappers with physical changes to their respective files by the user.
     *
     * @param name Reference to the stored object
     * @throws Exception IO error
     */
    public void update(String name) throws Exception {
        Object item = get(name);

        if (item instanceof AbstractFileConfiguration) {
            AbstractFileConfiguration config = (AbstractFileConfiguration) item;
            config.load(config.getFile(), FileUtils.readFileToString(config.getFile()));
        } else {
            FileWrapper file = (FileWrapper) item;
            file.setFileContent(FileUtils.readFileToString(file.getFile()));
        }
    }

    /**
     * Save a MutableFileConfiguration to file.
     *
     * @param name Name of the fileconfiguration.
     * @throws Exception May throw on IO error
     */
    public void save(String name) throws Exception {
        Object item = get(name);

        if (item instanceof MutableFileConfiguration) {
            ((MutableFileConfiguration) item).save();
        }
    }

    /**
     * Get an immutable map of all files currently stored in the filemanager.
     * The first type parameter, the string, corresponds to the file identifier.
     * The object will either be a FileWrapper, or a FileConfiguration, depending
     * on the type of file the identifier was configured with.
     *
     * @return ImmutableMap of String and object
     */
    public ImmutableMap<String, Object> getAll() {
        return ImmutableMap.copyOf(files);
    }

    private <T> T putAndGet(String name, T file) {
        files.put(name, file);
        return file;
    }

    private File createFile(String externalPath, String internalPath) throws Exception {
        externalPath = framework.getFiles().getFileDir() + "/" + externalPath;
        File file = new File(externalPath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            boolean fileSuc = file.createNewFile();
            if (!fileSuc) throw new CreateFileException("can't create file");
            FileUtils.exportResource(Framework.class.getResourceAsStream(internalPath), externalPath);
        }

        return file;
    }
}
