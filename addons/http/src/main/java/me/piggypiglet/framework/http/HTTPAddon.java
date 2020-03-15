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

package me.piggypiglet.framework.http;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.addon.init.AddonBuilder;
import me.piggypiglet.framework.addon.init.AddonData;
import me.piggypiglet.framework.http.files.DefaultHTTP;
import me.piggypiglet.framework.http.files.HTTP;
import me.piggypiglet.framework.http.registerables.shutdown.ShutdownHTTP;
import me.piggypiglet.framework.http.registerables.startup.HTTPRegisterable;
import me.piggypiglet.framework.http.registerables.startup.PermissionRegisterable;
import me.piggypiglet.framework.http.registerables.startup.RoutesRegisterable;
import me.piggypiglet.framework.http.routes.Route;
import me.piggypiglet.framework.http.routes.auth.permissions.Permission;
import me.piggypiglet.framework.init.bootstrap.BootPriority;
import me.piggypiglet.framework.managers.Manager;
import me.piggypiglet.framework.utils.clazz.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public final class HTTPAddon extends Addon {
    @NotNull
    @Override
    protected AddonData provideConfig(@NotNull final AddonBuilder<AddonData> builder) {
        return builder
                .startup(BootPriority.BEFORE_ADDONS, PermissionRegisterable.class)
                .startup(RoutesRegisterable.class, HTTPRegisterable.class)
                .files()
                        .config("http", "http.json", "http.json", HTTP.class)
                        .file("http-default", "index.html", "index.html", DefaultHTTP.class)
                        .build()
                .shutdown(ShutdownHTTP.class)
                .request("permission_managers", new TypeLiteral<Set<Class<? extends Manager<Permission>>>>() {}.getType(),
                        scanner -> scanner.getSubTypesOf(Manager.class).stream()
                                .filter(manager -> {
                                    try {
                                        return Permission.class.equals(ClassUtils.getImplementedGeneric(manager));
                                    } catch (Exception e) {
                                        return false;
                                    }
                                })
                                .collect(Collectors.toSet()))
                .request("routes", new TypeLiteral<Set<Class<? extends Route>>>() {}.getType(),
                        scanner -> scanner.getSubTypesOf(Route.class))
                .build();
    }
}