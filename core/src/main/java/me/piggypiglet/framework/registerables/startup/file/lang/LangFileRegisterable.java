package me.piggypiglet.framework.registerables.startup.file.lang;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.objects.ConfigInfo;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.lang.objects.LangConfig;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Langs;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class LangFileRegisterable extends StartupRegisterable {
    @Inject
    private Framework framework;
    @Inject
    private FileManager fileManager;
    @Inject
    private FrameworkBootstrap main;
    @Inject
    private Lang lang;

    @Override
    protected void execute() {
        if (framework.isUseLangFile()) {
            final ConfigInfo langConfig = framework.getLangConfig();
            boolean langSuccess = false;

            if (langConfig != null) {
                final FileConfiguration lang = fileManager.getConfig(langConfig.getConfig());

                if (lang != null) {
                    doConfig(langConfig.getLocations(), lang);
                    langSuccess = true;
                }
            }

            if (!langSuccess) {
                useDefaultLang(true);
            }
        } else {
            useDefaultLang(false);
        }

        requestStaticInjections(me.piggypiglet.framework.lang.Lang.LanguageGetter.class);
    }

    private void doConfig(Map<String, String> locations, FileConfiguration config) {
        final Map<String, String> map = new HashMap<>();
        locations.forEach((k, v) -> map.put(k, config.getString(v)));
        addBinding(new LangConfig(map));
    }

    private void useDefaultLang(boolean external) {
        main.getAddons().forEach((c, a) -> {
            final Langs lang = a.lang();

            if (lang.clazz() != Lang.Values.class) {
                try {
                    final String file = "lang/" + c.getSimpleName().toLowerCase().replace("addon", "").trim() + "_" + lang.file();

                    doConfig(
                            this.lang.getValues().stream().collect(Collectors.toMap(LangEnum::getPath, LangEnum::getPath)),
                            fileManager.loadConfig("lang", file, external ? file : null)
                    );
                } catch (Exception e) {
                    throw new RuntimeException("Something went dreadfully wrong when loading the default language file. How disappointing :(\n" + e);
                }
            }
        });
    }
}
