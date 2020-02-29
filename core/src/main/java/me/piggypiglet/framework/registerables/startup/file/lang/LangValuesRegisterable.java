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

package me.piggypiglet.framework.registerables.startup.file.lang;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.framework.LangEnum;
import me.piggypiglet.framework.lang.objects.CustomLang;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.StringUtils;
import me.piggypiglet.framework.utils.annotations.addon.Langs;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LangValuesRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Lang lang;
    @Inject private Framework framework;

    @Override
    protected void execute() {
        final Set<LangEnum> values = lang.getValues();
        final Map<String, Set<LangEnum>> specificValues = lang.getSpecificValues();

        values.addAll(Arrays.asList(Lang.Values.values()));
        specificValues.put("core", new HashSet<>(values));

        bootstrap.getAddons().forEach((c, a) -> {
            Langs lang = a.lang();

            if (lang.clazz() != Lang.Values.class) {
                final Set<LangEnum> tempValues = new HashSet<>();

                for (Enum<? extends LangEnum> val : lang.clazz().getEnumConstants()) {
                    tempValues.add((LangEnum) val);
                }

                values.addAll(tempValues);
                specificValues.put(StringUtils.addonName(c), tempValues);
            }
        });

        final CustomLang custom = framework.getCustomLang();

        if (custom != null) {
            final Set<LangEnum> temp = new HashSet<>(Arrays.asList(custom.getValues()));
            values.addAll(temp);
            specificValues.put("custom", temp);
        }
    }
}
