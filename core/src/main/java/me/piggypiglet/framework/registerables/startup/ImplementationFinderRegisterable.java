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

package me.piggypiglet.framework.registerables.startup;

import com.google.inject.Inject;
import com.google.inject.name.Names;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.logging.implementations.DefaultLogger;
import me.piggypiglet.framework.reflection.Reflections;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.task.Task;
import me.piggypiglet.framework.task.implementations.DefaultTask;

import java.util.Optional;

public final class ImplementationFinderRegisterable extends StartupRegisterable {
    @Inject private Reflections reflections;
    @Inject private Framework framework;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        addBinding(Task.class, ((Optional<Task>) find(Task.class, DefaultTask.class).map(injector::getInstance)).orElse(new DefaultTask(framework.getThreads())));
        addAnnotatedBinding(Class.class, Names.named("logger"), find(Logger.class, DefaultLogger.class).orElse(DefaultLogger.class));
        requestStaticInjections(LoggerFactory.class);
    }

    private <T> Optional<Class<? extends T>> find(Class<T> interfaze, Class<? extends T> defaultClass) {
        return reflections.getSubTypesOf(interfaze).stream().filter(c -> c != defaultClass).findFirst();
    }
}
