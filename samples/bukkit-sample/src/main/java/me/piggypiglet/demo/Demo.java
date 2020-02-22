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

package me.piggypiglet.demo;

import me.piggypiglet.demo.registerables.BroadcasterRegisterable;
import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.registerables.objects.RegisterableData;
import me.piggypiglet.framework.utils.annotations.files.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class Demo extends JavaPlugin {
    @Override
    public void onEnable() {
        Framework.builder()
                .main(JavaPlugin.class, this)
                .scanner()
                        .pckg("me.piggypiglet.demo")
                        .build()
                .commandPrefixes("demo")
                .startup(new RegisterableData(BroadcasterRegisterable.class))
                .file(true, "config", "/config.yml", getDataFolder() + "/config.yml", Config.class)
                .build()
                .init();
    }
}
