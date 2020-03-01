package me.piggypiglet.framework.addon.framework.api;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AddonConfiguration {
    private final Map<String, FileConfigInfo> fileConfigs;

    public AddonConfiguration(@NotNull final Map<String, FileConfigInfo> fileConfigs) {
        this.fileConfigs = fileConfigs;
    }

    @NotNull
    public Map<String, FileConfigInfo> getFileConfigs() {
        return fileConfigs;
    }
}
