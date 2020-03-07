package me.piggypiglet.framework.file.internal.registerables;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.framework.MapFileConfiguration;
import me.piggypiglet.framework.file.objects.FileData;
import me.piggypiglet.framework.registerables.StartupRegisterable;

import java.util.stream.Collectors;

public final class FileRelocationRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FileManager fileManager;

    @Override
    protected void execute() {
        framework.getFiles().getFiles().stream()
                .collect(Collectors.toMap(FileData::getName, FileData::getRelocations))
                .forEach((name, data) -> {
                    final FileConfiguration config = fileManager.getConfig(name)
                            .orElseThrow(() -> new RuntimeException("Provided config: " + name + " for relocation doesn't exist."));
                    final MapFileConfiguration mapConfig;

                    if (config instanceof MapFileConfiguration) {
                        mapConfig = (MapFileConfiguration) config;
                    } else {
                        throw new RuntimeException("Provided config: " + name + " for relocation isn't a MapFileConfiguration.");
                    }

                    //config, base, reference
                    final Table<FileConfiguration, String, String> references = HashBasedTable.create();
                    data.getReferences().rowMap().forEach((cfg, map) -> {
                        final FileConfiguration referenceConfig = fileManager.getConfig(cfg)
                                .orElseThrow(() -> new RuntimeException("The provided relocation config: " + cfg + " doesn't exist."));

                        map.forEach((base, reference) -> references.put(referenceConfig, base, reference));
                    });

                    data.getValues().forEach(mapConfig::set);
                });
    }
}
