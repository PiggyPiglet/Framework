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

package me.piggypiglet.framework.jda;

import me.piggypiglet.framework.jda.annotation.Bot;
import me.piggypiglet.framework.jda.shutdown.JDAShutdown;
import me.piggypiglet.framework.jda.startup.BuilderRegisterable;
import me.piggypiglet.framework.jda.startup.EventsRegisterable;
import me.piggypiglet.framework.jda.startup.FinishRegisterable;
import me.piggypiglet.framework.utils.annotations.addon.Addon;
import me.piggypiglet.framework.utils.annotations.addon.File;
import me.piggypiglet.framework.utils.annotations.registerable.Startup;

@Addon(
        startup = {
                @Startup(BuilderRegisterable.class),
                @Startup(EventsRegisterable.class),
                @Startup(FinishRegisterable.class)
        },
        files = {@File(
                config = true,
                name = "bot",
                externalPath = "./bot.json",
                internalPath = "/bot.json",
                annotation = Bot.class
        )},
        shutdown = JDAShutdown.class
)
public final class JDAAddon {
}
