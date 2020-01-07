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

package me.piggypiglet.framework.registerables.startup.file.migration;

import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.MutableFileConfiguration;
import me.piggypiglet.framework.file.migration.Migrator;
import me.piggypiglet.framework.logging.Logger;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.Scanner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class MigrationRegisterable extends StartupRegisterable {
    private static final Logger LOGGER = LoggerFactory.getLogger("Config Migration");

    @Inject private Scanner scanner;
    @Inject private FileManager fileManager;

    @Override
    protected void execute() {
        final Set<MutableFileConfiguration> needsSaving = new HashSet<>();

        Multimaps.index(
                scanner.getSubTypesOf(Migrator.class).stream()
                        .map(injector::getInstance)
                        .sorted()
                        .collect(Collectors.toList()),
                m -> (MutableFileConfiguration) fileManager.get(m.getConfig())
        ).asMap().forEach((c, ms) -> ms.forEach(m -> {
            if (m.canMigrate(c)) {
                m.run(c);
                needsSaving.add(c);
            }
        }));

        needsSaving.forEach(c -> {
            try {
                c.save();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        });
    }
}
