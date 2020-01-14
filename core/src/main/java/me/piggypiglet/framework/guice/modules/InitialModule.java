/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
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

package me.piggypiglet.framework.guice.modules;

import com.google.inject.*;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.scanning.implementations.ZISScanner;

public class InitialModule extends AbstractModule {
    protected final FrameworkBootstrap boot;
    protected final Framework config;

    public InitialModule(FrameworkBootstrap boot, Framework config) {
        this.boot = boot;
        this.config = config;
    }

    public final Injector createInjector() {
        return Guice.createInjector(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void configure() {
        final MainBinding main = config.getMain();

        if (main.getAnnotation() != null) {
            bind(main.getClazz()).annotatedWith(main.getAnnotation()).toInstance(main.getInstance());
        } else {
            bind(main.getClazz()).toInstance(main.getInstance());
        }

        customConfigure();
    }

    protected void customConfigure() {}

    @Provides
    @Singleton
    public final MainBinding providesMainBinding() {
        return config.getMain();
    }

    @Provides
    @Singleton
    public final FrameworkBootstrap providesFrameworkBootstrap() {
        return boot;
    }

    @Provides
    @Singleton
    public final Framework providesConfig() {
        return config;
    }

    @Provides
    @Singleton
    public Scanner providesScanner() {
        return new ZISScanner(config.getMain().getInstance().getClass(), config.getPckg(), config.getPckgExclusions());
    }
}
