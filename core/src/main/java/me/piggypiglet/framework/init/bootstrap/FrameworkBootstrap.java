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

package me.piggypiglet.framework.init.bootstrap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.init.AddonData;
import me.piggypiglet.framework.guice.ExceptionalInjector;
import me.piggypiglet.framework.guice.modules.BindingSetterModule;
import me.piggypiglet.framework.guice.modules.InitialModule;
import me.piggypiglet.framework.init.builder.stages.guice.GuiceData;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.AbstractScanner;
import me.piggypiglet.framework.scanning.framework.Scanner;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Singleton
public final class FrameworkBootstrap {
    private final AtomicReference<Injector> injector = new AtomicReference<>();
    private final Set<StartupRegisterable> registerables = new LinkedHashSet<>();
    private final Map<Class<? extends Addon>, AddonData> addons = new HashMap<>();

    @Inject private Scanner scanner;

    private final Framework config;

    public FrameworkBootstrap(Framework config) {
        this.config = config;
    }

    public void start() {
        final GuiceData guice = config.getGuice();
        final InitialModule initialModule = guice.getInitialModule().apply(this, config);

        if (guice.getInjector() == null) {
            injector.set(new ExceptionalInjector(initialModule.createInjector()));
        } else {
            injector.set(new ExceptionalInjector(guice.getInjector().createChildInjector(initialModule)));
        }

        injector.get().injectMembers(this);
        injector.get().injectMembers(config.getMain().getInstance());

        if (scanner instanceof AbstractScanner) {
            ((AbstractScanner) scanner).populate();
        }

        scanner
                .getSubTypesOf(Addon.class)
                .forEach(c -> addons.put(c, injector.get().getInstance(c).getConfig()));

        final Multimap<BootPriority, Class<? extends StartupRegisterable>> boot = ArrayListMultimap.create();

        Arrays.stream(BootPriority.values()).forEach(priority -> boot.putAll(priority, priority.getDefault()));

        addons.values().stream()
                .map(AddonData::getStartup)
                .forEach(boot::putAll);

        boot.putAll(config.getGuice().getStartup());

        for (BootPriority priority : BootPriority.values()) {
            final Collection<Class<? extends StartupRegisterable>> section = boot.get(priority);

            section.forEach(r -> {
                StartupRegisterable registerable = injector.get().getInstance(r);
                registerable.run(injector.get());

                if (Stream.of(registerable.getBindings(), registerable.getAnnotatedBindings(), registerable.getStaticInjections())
                        .anyMatch(list -> !list.isEmpty())) {
                    injector.set(injector.get().createChildInjector(new BindingSetterModule(
                            registerable.getBindings(),
                            registerable.getAnnotatedBindings(),
                            registerable.getStaticInjections().toArray(new Class[]{})
                    )));
                }

                registerables.add(registerable);
            });
        }
    }

    /**
     * Get an instance of the current injector, as the injectable injector isn't updated through child injectors.
     *
     * @return Injector instance
     */
    public Injector getInjector() {
        return injector.get();
    }

    /**
     * Get all the startup registerable instances that were initialized during startup.
     *
     * @return Set of StartupRegisterable instances
     */
    public Set<StartupRegisterable> getRegisterables() {
        return registerables;
    }

    /**
     * Get all found addons and their data.
     *
     * @return Set of Addon annotation data objects.
     */
    public Map<Class<? extends Addon>, AddonData> getAddons() {
        return addons;
    }
}