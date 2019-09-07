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
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import me.piggypiglet.framework.guice.objects.AnnotatedBinding;

import java.util.List;
import java.util.Map;

public final class BindingSetterModule extends AbstractModule {
    private final Map<Class, Object> providers;
    private final List<AnnotatedBinding> annotatedBindings;
    private final Class[] staticInjections;

    public BindingSetterModule(Map<Class, Object> providers, List<AnnotatedBinding> annotatedBindings, Class... staticInjections) {
        this.providers = providers;
        this.annotatedBindings = annotatedBindings;
        this.staticInjections = staticInjections;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure() {
        providers.forEach((c, o) -> bind(c).toInstance(o));
        annotatedBindings.forEach(b -> {
            AnnotatedBindingBuilder bind = bind(b.getClazz());
            LinkedBindingBuilder link;

            if (b.isObject()) {
                link = bind.annotatedWith(b.getObjectAnnotation());
            } else {
                link = bind.annotatedWith(b.getClassAnnotation());
            }

            link.toInstance(b.getInstance());
        });
        requestStaticInjection(staticInjections);
    }
}