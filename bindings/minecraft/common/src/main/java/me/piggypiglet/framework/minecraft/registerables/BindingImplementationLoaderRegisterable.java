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

package me.piggypiglet.framework.minecraft.registerables;

import com.google.inject.Inject;
import me.piggypiglet.framework.minecraft.plugin.DefaultPlugin;
import me.piggypiglet.framework.minecraft.plugin.Plugin;
import me.piggypiglet.framework.minecraft.server.DefaultServer;
import me.piggypiglet.framework.minecraft.server.Server;
import me.piggypiglet.framework.minecraft.text.Text;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;

import java.util.Optional;

public final class BindingImplementationLoaderRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;

    @Override
    protected void execute() {
       bind(Server.class, new DefaultServer());
       bind(Plugin.class, new DefaultPlugin());
       requestStaticInjections(Text.class);
    }

    @SuppressWarnings("unchecked")
    private <T> void bind(Class<T> clazz, T def) {
        addBinding(clazz, ((Optional<T>) scanner.getSubTypesOf(clazz).stream().map(injector::getInstance).findFirst()).orElse(def));
    }
}
