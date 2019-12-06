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

package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.utils.annotations.id.ID;
import me.piggypiglet.framework.utils.annotations.id.IDInfo;
import me.piggypiglet.framework.utils.annotations.id.Ids;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GuildBindingRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;
    @Inject private JDA jda;

    @Override
    protected void execute() {
        final Set<IDInfo> info = Stream.concat(scanner.getParametersInConstructorsAnnotatedWith(ID.class).stream(), scanner.getFieldsAnnotatedWith(ID.class).stream())
                .map(p -> new IDInfo(p instanceof Parameter ? ((Parameter) p).getType() : ((Field) p).getType(), p.getAnnotation(ID.class)))
                .collect(Collectors.toSet());

        info.forEach(i -> {
            switch (i.getType().getSimpleName().toLowerCase()) {
                case "guild":
                    addAnnotatedBinding(Guild.class, Ids.id(i.getId().value()), jda.getGuildById(i.getId().value()));
                    break;
            }
        });
    }
}
