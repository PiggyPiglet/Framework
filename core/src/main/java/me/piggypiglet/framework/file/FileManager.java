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

    public FileWrapper loadFile(String name, String internalPath, String externalPath) throws Exception {
        LOGGER.info("Loading %s.", name);

        if (externalPath == null) {
            return putAndGet(name, new FileWrapper(null, FileUtils.readEmbedToString(internalPath)));
        }

        File file = createFile(externalPath, internalPath);

        return putAndGet(name, new FileWrapper(file, FileUtils.readFileToString(file)));
    }

    public FileConfiguration loadConfig(String name, String internalPath, String externalPath) throws Exception {
        LOGGER.info("Loading %s.", name);

        if (externalPath == null) {
            return putAndGet(name, fileConfigurationFactory.get(internalPath, FileUtils.readEmbedToString(internalPath)));
        }

        File file = createFile(externalPath, internalPath);

        return putAndGet(name, fileConfigurationFactory.get(file));
    }

    public FileConfiguration getConfig(String name) {
        return (FileConfiguration) files.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) files.get(name);
    }

    // only works with abstract file configuration extensions
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
