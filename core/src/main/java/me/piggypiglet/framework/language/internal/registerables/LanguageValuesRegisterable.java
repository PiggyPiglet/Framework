package me.piggypiglet.framework.language.internal.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.stages.language.LanguageData;
import me.piggypiglet.framework.language.LanguageManager;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.internal.Internal;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class LanguageValuesRegisterable extends StartupRegisterable {
    @Inject private Framework framework;
    @Inject private FrameworkBootstrap main;
    @Inject @Internal("languages") private Set<Class<? extends Enum<? extends LanguageEnum>>> enums;
    @Inject private LanguageManager languageManager;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        final LanguageData userData = framework.getLang();
        final Map<Class<? extends Addon<?>>, me.piggypiglet.framework.addon.init.objects.LanguageData> addonData = main.getAddons().values().stream()
                .collect(Collectors.toMap((Addon<?> a) -> (Class<? extends Addon<?>>) a.getClass(), a -> a.getConfig().getLang()));
    }
}
