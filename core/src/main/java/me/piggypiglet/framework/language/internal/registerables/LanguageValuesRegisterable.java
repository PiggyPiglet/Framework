package me.piggypiglet.framework.language.internal.registerables;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.file.internal.InternalFiles;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.stages.language.LanguageData;
import me.piggypiglet.framework.init.builder.stages.language.objects.LanguageFileEnumPair;
import me.piggypiglet.framework.language.LanguageManager;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.language.internal.Language;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.map.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class LanguageValuesRegisterable extends StartupRegisterable {
    private static final String CORE = InternalFiles.CORE_LANGUAGE.getName();

    @Inject private Framework framework;
    @Inject private FrameworkBootstrap main;
    @Inject private FileManager fileManager;
    @Inject private LanguageManager languageManager;

    @Override
    protected void execute() {
        final LanguageData userInput = framework.getLang();
        final Map<Class<? extends Addon>, me.piggypiglet.framework.addon.init.objects.LanguageData> addonInput = main.getAddons().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getLang()));

        // name, table<enum, path, value>
        final Map<String, Table<LanguageEnum, String, String>> languages = Maps.of(new HashMap<String, Table<LanguageEnum, String, String>>())
                .key(CORE).value(data(Language.values(), fileManager.getConfig(CORE)
                        .orElseThrow(() -> new RuntimeException("Something went wrong when loading the core language file."))))
                .build();

        final LanguageFileEnumPair custom = userInput.getCustom();
        final Table<LanguageEnum, String, String> userData = data(custom.getEnums(), fileManager.getConfig(custom.getConfig())
                .orElseThrow(() -> new RuntimeException("Supplied custom config: " + custom.getConfig() + " doesn't exist.")));
        languages.put("custom", userData);

        addonInput.forEach((addon, data) ->
                languages.put(StringUtils.addonName(addon), data((LanguageEnum[]) data.getClazz().getEnumConstants(), fileManager.getConfig(data.getFile())
                        .orElseThrow(() -> new RuntimeException("Supplied addon config: " + data.getFile() + " doesn't exist.")))));

        languageManager.populate(languages);

        requestStaticInjections(StringUtils.class);
    }

    @NotNull
    private static Table<LanguageEnum, String, String> data(@NotNull final LanguageEnum[] enums, @NotNull final FileConfiguration config) {
        final Table<LanguageEnum, String, String> result = HashBasedTable.create();
        Arrays.stream(enums).forEach(lang -> result.put(lang, lang.getPath(), config.getString(lang.getPath())));

        return result;
    }
}
