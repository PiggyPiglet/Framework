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

package me.piggypiglet.demo.commands;

import com.google.inject.Inject;
import me.piggypiglet.framework.commands.framework.Command;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.framework.utils.annotations.files.Config;

public final class DemoInfoCommand extends Command {
    @Inject @Config private FileConfiguration config;

    public DemoInfoCommand() {
        super("info");
        options.usage("").description("Get info about the demo plugin.");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        String[] message = {
                "This is a demo plugin, using the RPF Bukkit Bindings. Here's the info stored in your config:",
                "Broadcast Message: %s",
                "Broadcast Interval: %s"
        };

        user.sendMessage(String.join("\n", message), config.getString("broadcast.message"), config.getString("broadcast.interval"));

        return true;
    }
}