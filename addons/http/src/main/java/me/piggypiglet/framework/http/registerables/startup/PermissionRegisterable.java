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

package me.piggypiglet.framework.http.registerables.startup;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.http.routes.auth.permissions.DefaultPermissionStore;
import me.piggypiglet.framework.http.routes.auth.permissions.Permission;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.clazz.ClassUtils;

import java.util.Optional;

public final class PermissionRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;
    @Inject private DefaultPermissionStore defaultPermissionStore;

    @Override
    @SuppressWarnings("unchecked")
    protected void execute() {
        final Optional<? extends Manager> manager = scanner.getSubTypesOf(Manager.class).stream().filter(m -> {
            try {
                return Permission.class.isAssignableFrom(ClassUtils.getImplementedGeneric(m.getClass()));
            } catch (Exception e) {
                return false;
            }
        }).map(injector::getInstance).findAny();

        final TypeLiteral<Manager<Permission>> typeLiteral = new TypeLiteral<Manager<Permission>>(){};

        if (manager.isPresent()) {
            //noinspection unchecked
            addBinding(typeLiteral, (Manager<Permission>) manager.get());
        } else {
            addBinding(typeLiteral, defaultPermissionStore);
            defaultPermissionStore.setup();
        }
    }
}
