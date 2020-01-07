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

package me.piggypiglet.framework.file.migration;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.file.framework.MutableFileConfiguration;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;

import java.util.Collection;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class MigratorManager extends Manager<Migrator> {
    @Inject private FileManager fileManager;

    private final Multimap<MutableFileConfiguration, Migrator> migrators = ArrayListMultimap.create();

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(String.class, fileManager::get)
                    .getter(this::stringGetter)
                    .exists(r -> stringGetter(r) != null)
                    .bundle()
                .key(MutableFileConfiguration.class)
                    .getter(r -> migrators.get(r).stream()
                            .filter(c -> c.canMigrate(r))
                            .findFirst().orElse(null))
                    .exists(r -> get(r) != null)
                    .bundle()
                .build();
    }

    @Override
    protected void insert(Migrator item) {
        migrators.put(fileManager.get(item.getConfig()), item);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    protected void delete(Migrator item) {
        migrators.remove(fileManager.get(item.getConfig()), item);
    }

    @Override
    protected Collection<Migrator> retrieveAll() {
        return migrators.values();
    }

    private <T> Object stringGetter(T file) {
        if (file instanceof MutableFileConfiguration) {
            return get(file);
        }

        return null;
    }
}
