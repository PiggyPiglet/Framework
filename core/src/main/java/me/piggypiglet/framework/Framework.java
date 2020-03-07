/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework;

import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.framework.config.AddonConfiguration;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.init.builder.FrameworkBuilder;
import me.piggypiglet.framework.init.builder.stages.file.FilesData;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceData;
import me.piggypiglet.framework.init.builder.stages.language.LanguageData;
import me.piggypiglet.framework.init.builder.stages.scanning.ScanningData;
import me.piggypiglet.framework.utils.builder.GenericBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class Framework {
    private final MainBinding main;
    private final ScanningData scanning;
    private final GuiceData guice;
    private final String[] commandPrefixes;
    private final FilesData files;
    private final Map<Class<? extends Addon>, AddonConfiguration> addons;
    private final int threads;
    private final LanguageData lang;
    private final boolean debug;

    public Framework(@NotNull final MainBinding main, @NotNull final ScanningData scanning,
                     @NotNull final GuiceData guice, @Nullable final String[] commandPrefixes,
                     @NotNull final FilesData files, @NotNull final Map<Class<? extends Addon>, AddonConfiguration> addons,
                     final int threads, @NotNull final LanguageData lang,
                     final boolean debug) {
        this.main = main;
        this.scanning = scanning;
        this.guice = guice;
        this.commandPrefixes = commandPrefixes;
        this.files = files;
        this.addons = addons;
        this.threads = threads;
        this.lang = lang;
        this.debug = debug;
    }

    @NotNull
    public static FrameworkBuilder builder(@NotNull final Object main) {
        return new FrameworkBuilder(main);
    }

    @NotNull
    public static <T> FrameworkBuilder builder(@NotNull final Class<? super T> interfaze, @NotNull final T instance) {
        return new FrameworkBuilder(interfaze, instance);
    }

    @NotNull
    public FrameworkBootstrap init() {
        return GenericBuilder.of(() -> new FrameworkBootstrap(this))
                .with(FrameworkBootstrap::start)
                .build();
    }

    @NotNull
    public MainBinding getMain() {
        return main;
    }

    @NotNull
    public ScanningData getScanning() {
        return scanning;
    }

    @NotNull
    public GuiceData getGuice() {
        return guice;
    }

    @Nullable
    public String[] getCommandPrefixes() {
        return commandPrefixes;
    }

    @NotNull
    public FilesData getFiles() {
        return files;
    }

    @NotNull
    public Map<Class<? extends Addon>, AddonConfiguration> getAddons() {
        return addons;
    }

    public int getThreads() {
        return threads;
    }

    @NotNull
    public LanguageData getLang() {
        return lang;
    }

    public boolean isDebug() {
        return debug;
    }
}
