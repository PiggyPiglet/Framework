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

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import me.piggypiglet.framework.guice.objects.AnnotatedBinding;

import java.util.List;
import java.util.Map;

public final class BindingSetterModule extends AbstractModule {
    private final Map<Class<?>, Object> providers;
    private final Map<TypeLiteral<?>, Object> genericBindings;
    private final List<AnnotatedBinding<?>> annotatedBindings;
    private final Class<?>[] staticInjections;

    public BindingSetterModule(Map<Class<?>, Object> providers, Map<TypeLiteral<?>, Object> genericBindings, List<AnnotatedBinding<?>> annotatedBindings, Class<?>... staticInjections) {
        this.providers = providers;
        this.genericBindings = genericBindings;
        this.annotatedBindings = annotatedBindings;
        this.staticInjections = staticInjections;
    }

    @Override
    public void configure() {
        providers.forEach(this::bind);
        genericBindings.forEach(this::bindGeneric);
        annotatedBindings.forEach(this::bindAnnotation);
        requestStaticInjection(staticInjections);
    }

    @SuppressWarnings("unchecked")
    private <T> void bind(Class<?> clazz, T instance) {
        bind((Class<? super T>) clazz).toInstance(instance);
    }

    @SuppressWarnings("unchecked")
    private <T> void bindGeneric(TypeLiteral<?> interfaze, T instance) {
        bind((TypeLiteral<? super T>) interfaze).toInstance(instance);
    }

    private <T> void bindAnnotation(AnnotatedBinding<T> binding) {
        final AnnotatedBindingBuilder<? super T> bind = bind(binding.getClazz());
        final LinkedBindingBuilder<? super T> link;

        if (binding.isObject()) {
            link = bind.annotatedWith(binding.getObjectAnnotation());
        } else {
            link = bind.annotatedWith(binding.getClassAnnotation());
        }

        link.toInstance(binding.getInstance());
    }
}