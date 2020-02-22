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

package me.piggypiglet.framework.registerables.startup.addon;

import com.google.inject.Inject;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.addon.builders.Config;
import me.piggypiglet.framework.addon.builders.ConfigInfo;
import me.piggypiglet.framework.file.FileManager;
import me.piggypiglet.framework.init.bootstrap.FrameworkBootstrap;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class UserConfigsRegisterable extends StartupRegisterable {
    @Inject private FrameworkBootstrap bootstrap;
    @Inject private Framework framework;
    @Inject private FileManager fileManager;
    @Inject private ConfigManager configManager;

    @Override
    protected void execute() {
        Map<Class<?>, ConfigInfo> configs = framework.getConfigs();

        for (Map.Entry<Class<?>, Addon> entry : bootstrap.getAddons().entrySet()) {
            Class<?> c = entry.getKey();
            Addon a = entry.getValue();

            if (a.config().name().equals("null")) continue;

            ConfigInfo info;

            if (configs.containsKey(c)) {
                info = configs.get(c);
            } else {
                info = new ConfigInfo(a.config().name(), Arrays.stream(a.config().keys()).collect(Collectors.toMap(s -> s, s -> s)), true);
            }

            final Map<String, Object> items = info.getLocations().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> fileManager.getConfig(info.getConfig()).get(e.getValue())));

            configManager.getConfigs().put(c, new Config(items));
        }
    }
}
