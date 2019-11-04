package me.piggypiglet.framework.registerables.startup.file.lang;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Langs;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public final class LangValuesRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Lang lang;
    @Inject private Framework framework;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        final List<LangEnum> values = lang.getValues();

        bootstrap.getAddons().values().forEach(a -> {
            Langs lang = a.lang();

            if (lang.clazz() != Lang.Values.class) {
                for (String val : lang.values()) {
                    values.add((LangEnum) Enum.valueOf((Class) lang.clazz(), val));
                }
            }
        });

        LangEnum[] custom = framework.getCustomLang();

        if (custom != null) {
            values.addAll(Arrays.asList(custom));
        }
    }
}
