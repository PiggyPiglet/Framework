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

package me.piggypiglet.framework.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import me.piggypiglet.framework.guice.objects.AnnotatedBinding;
import me.piggypiglet.framework.guice.objects.Binding;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationWrapper;

import java.util.List;

public final class BindingSetterModule extends AbstractModule {
    private final List<Binding<?>> bindings;
    private final List<AnnotatedBinding<?>> annotatedBindings;
    private final Class<?>[] staticInjections;

    public BindingSetterModule(List<Binding<?>> bindings, List<AnnotatedBinding<?>> annotatedBindings, Class<?>... staticInjections) {
        this.bindings = bindings;
        this.annotatedBindings = annotatedBindings;
        this.staticInjections = staticInjections;
    }

    @Override
    public void configure() {
        bindings.forEach(this::bind);
        annotatedBindings.forEach(this::bindAnnotation);
        requestStaticInjection(staticInjections);
    }

    private <T> void bind(Binding<T> binding) {
        final LinkedBindingBuilder<? super T> bind;

        if (binding.isTypeLiteral()) {
            bind = bind(binding.getTypeLiteral());
        } else {
            bind = bind(binding.getTypeClass());
        }

        bind.toInstance(binding.getInstance());
    }

    private <T> void bindAnnotation(AnnotatedBinding<T> binding) {
        final AnnotatedBindingBuilder<? super T> bind;

        if (binding.isTypeLiteral()) {
            bind = bind(binding.getTypeLiteral());
        } else {
            bind = bind(binding.getTypeClazz());
        }

        final LinkedBindingBuilder<? super T> link;
        final AnnotationWrapper annotation = binding.getAnnotation();

        if (annotation.getAnnotationClass() == null) {
            link = bind.annotatedWith(annotation.getAnnotationInstance());
        } else {
            link = bind.annotatedWith(annotation.getAnnotationClass());
        }

        link.toInstance(binding.getInstance());
    }
}