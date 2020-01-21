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

import me.piggypiglet.framework.bootstrap.BootPriority;
import me.piggypiglet.framework.http.files.DefaultHTTP;
import me.piggypiglet.framework.http.files.HTTP;
import me.piggypiglet.framework.http.registerables.shutdown.ShutdownHTTP;
import me.piggypiglet.framework.http.registerables.startup.HTTPRegisterable;
import me.piggypiglet.framework.http.registerables.startup.PermissionRegisterable;
import me.piggypiglet.framework.http.registerables.startup.RoutesRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.Config;
import me.piggypiglet.framework.utils.annotations.addon.File;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

@Addon(
        startup = {
                @Startup(RoutesRegisterable.class),
                @Startup(HTTPRegisterable.class),
                @Startup(
                        value = PermissionRegisterable.class,
                        priority = BootPriority.BEFORE_ADDONS
                )
        },
        files = {@File(
                config = true,
                name = "http",
                externalPath = "http.json",
                internalPath = "/http.json",
                annotation = HTTP.class
        ), @File(
                config = false,
                name = "http-default",
                externalPath = "index.html",
                internalPath = "/index.html",
                annotation = DefaultHTTP.class
        )},
        config = @Config(
                name = "http",
                keys = {
                        "host", "port", "ssl.enabled", "ssl.path", "ssl.password", "standard-authentication", "standard-authentication.enabled",
                        "standard-authentication.tokens"
                }
        ),
        shutdown = ShutdownHTTP.class
)
public final class HTTPAddon {}
