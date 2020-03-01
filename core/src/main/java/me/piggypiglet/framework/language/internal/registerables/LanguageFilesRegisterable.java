package me.piggypiglet.framework.language.internal.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class LanguageFilesRegisterable extends StartupRegisterable {
    @Inject private Framework framework;

    @Override
    protected void execute() {

    }
}
