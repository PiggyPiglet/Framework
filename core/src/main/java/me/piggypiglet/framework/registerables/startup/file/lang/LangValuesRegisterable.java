package me.piggypiglet.framework.registerables.startup.file.lang;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.annotations.addon.Langs;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LangValuesRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Lang lang;
    @Inject private Framework framework;

    @Override
    protected void execute() {
        final Set<LangEnum> values = lang.getValues();
        final Map<String, Set<LangEnum>> specificValues = lang.getSpecificValues();

        values.addAll(Arrays.asList(Lang.Values.values()));
        specificValues.put("core", new HashSet<>(values));

        bootstrap.getAddons().forEach((c, a) -> {
            Langs lang = a.lang();

            if (lang.clazz() != Lang.Values.class) {
                final Set<LangEnum> tempValues = new HashSet<>();

                for (Enum val : lang.clazz().getEnumConstants()) {
                    tempValues.add((LangEnum) val);
                }

                values.addAll(tempValues);
                specificValues.put(StringUtils.addonName(c), tempValues);
            }
        });

        final CustomLang custom = framework.getCustomLang();

        if (custom != null) {
            final Set<LangEnum> temp = new HashSet<>(Arrays.asList(custom.getValues()));
            values.addAll(temp);
            specificValues.put("custom", temp);
        }
    }
}
