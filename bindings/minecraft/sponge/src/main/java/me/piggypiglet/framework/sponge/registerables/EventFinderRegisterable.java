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

package me.piggypiglet.framework.sponge.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.guice.objects.MainBinding;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.clazz.ClassUtils;
import me.piggypiglet.framework.utils.clazz.GenericException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Listener;

public final class EventFinderRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;
    @Inject private MainBinding main;
    @Inject private Logger logger;

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        final Object main = this.main.getInstance();
        scanner.getClassesWithAnnotatedMethods(Listener.class).stream().map(injector::getInstance).filter(o -> o != main).forEach(l -> Sponge.getEventManager().registerListeners(main, l));
        scanner.getSubTypesOf(EventListener.class).forEach(l -> {
            try {
                Sponge.getEventManager().registerListener(main, ClassUtils.getImplementedGeneric(l), injector.getInstance(l));
            } catch (GenericException e) {
                logger.warning("Could not find valid event on %s.", l.getName());
            }
        });
    }
}