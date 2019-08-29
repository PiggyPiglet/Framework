package me.piggypiglet.framework.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.objects.FileWrapper;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@SuppressWarnings("ResultOfMethodCallIgnored")
@Singleton
public final class FileManager {
    @Inject private FileConfigurationFactory fileConfigurationFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger("FileManager");

    private final Map<String, Object> files = new HashMap<>();

    /**
     * Load a file into the FileManager
     * @param name Name the file will be referenced by
     * @param internalPath Internal path of the file to be exported
     * @param externalPath External path to save and load the file from
     * @return Returns a file wrapper containing the file object and it's plaintext content
     *
     */
    public FileWrapper loadFile(String name, String internalPath, String externalPath) throws Exception {
        LOGGER.info("Loading %s.", name);

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
     */
    public FileConfiguration loadConfig(String name, String internalPath, String externalPath) throws Exception {
        LOGGER.info("Loading %s.", name);

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
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) files.get(name);
    }

    /**
     * Update instances of an AbstractFileConfiguration with physical user changes
     * @param name Name of the config
     */
    public void update(String name) {
        FileConfiguration config = getConfig(name);

        if (config instanceof AbstractFileConfiguration) {
            AbstractFileConfiguration ac = (AbstractFileConfiguration) config;
            ac.load(ac.getFile(), FileUtils.readFileToString(ac.getFile()));
        }
    }

    private <T> T putAndGet(String name, T file) {
        files.put(name, file);
        return file;
    }

    private File createFile(String externalPath, String internalPath) throws Exception {
        File file = new File(externalPath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileUtils.exportResource(Framework.class.getResourceAsStream(internalPath), externalPath);
        }

        return file;
    }
}
