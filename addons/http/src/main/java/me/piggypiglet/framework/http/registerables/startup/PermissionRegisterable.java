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
import me.piggypiglet.framework.utils.annotations.internal.Internal;

import java.util.Optional;
import java.util.Set;

public final class PermissionRegisterable extends StartupRegisterable {
    private static final TypeLiteral<Manager<Permission>> MANAGER_TYPE = new TypeLiteral<Manager<Permission>>(){};;

    @Inject @Internal("permission_managers") private Set<Class<? extends Manager<Permission>>> managers;
    @Inject private DefaultPermissionStore defaultPermissionStore;

    @Override
    protected void execute() {
        final Optional<Manager<Permission>> manager = managers.stream()
                .map(injector::getInstance)
                .map(instance -> (Manager<Permission>) instance)
                .findAny();

        if (!manager.isPresent()) {
            defaultPermissionStore.setup();
        }

        addBinding(MANAGER_TYPE, manager.orElse(defaultPermissionStore));
    }
}
