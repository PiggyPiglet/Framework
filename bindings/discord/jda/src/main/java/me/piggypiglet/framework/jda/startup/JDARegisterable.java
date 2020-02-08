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

package me.piggypiglet.framework.jda.startup;

import com.google.inject.Inject;
import me.piggypiglet.framework.addon.ConfigManager;
import me.piggypiglet.framework.jda.JDAAddon;
import me.piggypiglet.framework.logging.LoggerFactory;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.task.Task;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JDARegisterable extends StartupRegisterable {
    @Inject private ConfigManager configManager;
    @Inject private Scanner scanner;
    @Inject private Task task;

    @Override
    protected void execute() {
        LoggerFactory.getLogger("JDA").debug("Initializing bot and bindings.");

        final Map<String, Object> items = configManager.getConfigs().get(JDAAddon.class).getItems();

        JDABuilder builder = new JDABuilder(AccountType.BOT)
                .setToken((String) items.get("token"))
                .setActivity(Activity.of(
                        Activity.ActivityType.valueOf(((String) items.getOrDefault("activity.type", "default")).toUpperCase()), (String) items.get("activity.activity")
                )
        );

        Stream.of(
                EventListener.class,
                ListenerAdapter.class
        ).map(this::getListeners).forEach(l -> l.forEach(builder::addEventListeners));

        JDA jda;

        try {
            jda = builder.build();
        addBinding(JDA.class, jda);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        task.async(r -> {
            if (jda.getStatus() != JDA.Status.CONNECTED) {
                LoggerFactory.getLogger("JDA").debug("JDA has not connected in over 20 seconds, shutting down.");
                System.exit(0);
            }
        }, "20 seconds", false);

        while (true) {
            JDA.Status status = jda.getStatus();

            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (status) {
                if (status == JDA.Status.CONNECTED) {
                    break;
                }
            }
        }
    }

    private <T> List<T> getListeners(Class<T> clazz) {
        return scanner.getSubTypesOf(clazz).stream().map(injector::getInstance).collect(Collectors.toList());
    }
}
