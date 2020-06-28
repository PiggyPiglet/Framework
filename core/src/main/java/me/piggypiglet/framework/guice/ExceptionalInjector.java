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

package me.piggypiglet.framework.guice;

import com.google.inject.*;
import com.google.inject.spi.TypeConverterBinding;
import me.piggypiglet.framework.guice.exceptions.InjectionException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ExceptionalInjector implements Injector {
    private final Injector composition;

    public ExceptionalInjector(@NotNull final Injector injector) {
        composition = injector;
    }

    @Override
    public void injectMembers(@NotNull final Object instance) {
        composition.injectMembers(instance);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(@NotNull final TypeLiteral<T> typeLiteral) {
        return composition.getMembersInjector(typeLiteral);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(@NotNull final Class<T> type) {
        return composition.getMembersInjector(type);
    }

    @Override
    public Map<Key<?>, Binding<?>> getBindings() {
        return composition.getBindings();
    }

    @Override
    public Map<Key<?>, Binding<?>> getAllBindings() {
        return composition.getAllBindings();
    }

    @Override
    public <T> Binding<T> getBinding(@NotNull final Key<T> key) {
        return composition.getBinding(key);
    }

    @Override
    public <T> Binding<T> getBinding(@NotNull final Class<T> type) {
        return composition.getBinding(type);
    }

    @Override
    public <T> Binding<T> getExistingBinding(@NotNull final Key<T> key) {
        return composition.getExistingBinding(key);
    }

    @Override
    public <T> List<Binding<T>> findBindingsByType(@NotNull final TypeLiteral<T> type) {
        return composition.findBindingsByType(type);
    }

    @Override
    public <T> Provider<T> getProvider(@NotNull final Key<T> key) {
        return composition.getProvider(key);
    }

    @Override
    public <T> Provider<T> getProvider(@NotNull final Class<T> type) {
        return composition.getProvider(type);
    }

    @Override
    public <T> T getInstance(@NotNull final Key<T> key) {
        try {
            return composition.getInstance(key);
        } catch (final Exception exception) {
            throw new InjectionException(exception);
        }
    }

    @Override
    public <T> T getInstance(@NotNull final Class<T> type) {
        try {
            return composition.getInstance(type);
        } catch (final Exception exception) {
            throw new InjectionException(exception);
        }
    }

    @Override
    public Injector getParent() {
        return composition.getParent();
    }

    @Override
    public Injector createChildInjector(@NotNull final Iterable<? extends Module> modules) {
        return new ExceptionalInjector(composition.createChildInjector(modules));
    }

    @Override
    public Injector createChildInjector(@NotNull final Module... modules) {
        return new ExceptionalInjector(composition.createChildInjector(modules));
    }

    @Override
    public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
        return composition.getScopeBindings();
    }

    @Override
    public Set<TypeConverterBinding> getTypeConverterBindings() {
        return composition.getTypeConverterBindings();
    }
}
