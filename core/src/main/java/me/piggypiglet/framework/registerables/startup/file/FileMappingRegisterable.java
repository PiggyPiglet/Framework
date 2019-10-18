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

package me.piggypiglet.framework.registerables.startup.file;

import com.google.inject.Inject;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.AbstractFileConfiguration;
import me.piggypiglet.framework.file.mapping.Maps;
import me.piggypiglet.framework.mapper.LevenshteinObjectMapper;
import me.piggypiglet.framework.scanning.Scanner;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class FileMappingRegisterable extends StartupRegisterable {
    @Inject private FileManager fileManager;
    @Inject private Scanner scanner;

    @Override
    protected void execute() {
        scanner.getTypesAnnotatedWith(Maps.class).forEach(c -> add(c, c.getAnnotation(Maps.class).value()));
    }

    private <T> void add(Class<T> clazz, String name) {
        AbstractFileConfiguration config = (AbstractFileConfiguration) fileManager.getConfig(name);

        if (config != null) {
            addBinding(clazz, new LevenshteinObjectMapper<T>(clazz){}.dataToType(config.getAll()));
        }
    }
}
