package me.piggypiglet.framework.registerables.startup.file.lang;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.lang.objects.LangConfig;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.annotations.addon.Langs;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class LangFileRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private FrameworkBootstrap main;
    @Inject private Lang lang;

    @Override
    protected void execute() {
        final CustomLang customLang = framework.getCustomLang();
        final Map<String, String> map = new HashMap<>();

        if (customLang != null) {
            map.putAll(doConfig(Arrays.stream(customLang.getValues()).collect(Collectors.toMap(LangEnum::getPath, LangEnum::getPath)), fileManager.getConfig(customLang.getConfig())));
        }

        if (framework.overrideLangFile()) {
            final ConfigInfo langConfig = framework.getLangConfig();
            boolean langSuccess = false;

            if (langConfig != null) {
                final FileConfiguration lang = fileManager.getConfig(langConfig.getConfig());

                if (lang != null) {
                    map.putAll(doConfig(langConfig.getLocations(), lang));
                    langSuccess = true;
                }
            }

            if (!langSuccess) {
                map.putAll(useDefaultLang(true));
            }
        } else {
            map.putAll(useDefaultLang(false));
        }

        addBinding(new LangConfig(map));
        requestStaticInjections(me.piggypiglet.framework.lang.Lang.LanguageGetter.class);
    }

    private Map<String, String> doConfig(Map<String, String> locations, FileConfiguration config) {
        return locations.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> config.getString(e.getValue(), "null")));
    }

    private Map<String, String> useDefaultLang(boolean external) {
        final String exception = "Something went dreadfully wrong when loading the default language file. How disappointing :(";
        final Collector<? super LangEnum, ?, Map<String, String>> collector = Collectors.toMap(LangEnum::getPath, LangEnum::getPath);
        final Map<String, String> map = new HashMap<>();

        main.getAddons().forEach((c, a) -> {
            final Langs lang = a.lang();

            if (lang.clazz() != Lang.Values.class) {
                try {
                    final String file = lang.file();
                    final String name = StringUtils.addonName(c);

                    map.putAll(doConfig(
                            this.lang.getSpecificValues().get(name).stream().collect(collector),
                            fileManager.loadConfig(name, "/" + file, external ? "lang/" + file : null)
                    ));
                } catch (Exception e) {
                    throw new RuntimeException(exception + ":\n" + e);
                }
            }
        });

        try {
            final Map<String, String> m = doConfig(
                    this.lang.getSpecificValues().get("core").stream().collect(collector),
                    fileManager.loadConfig("def_lang", "/core_lang.json", external ? "lang/core_lang.json" : null)
            );

            map.putAll(m);
        } catch (Exception e) {
            throw new RuntimeException(exception + ":\n" + e);
        }

        return map;
    }
}
