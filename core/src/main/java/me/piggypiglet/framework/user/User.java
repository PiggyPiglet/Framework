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

package me.piggypiglet.framework.user;

import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class User {
    private static final Pattern SPECIFIC_FORMAT_REGEX = Pattern.compile("/%\\d\\$s/g");
    private static final Pattern INDEX_FORMAT_REGEX = Pattern.compile("/%s/g");

    private final String name;
    private final String id;

    /**
     * Provide basic info to the superclass
     * @param name Name of the user
     * @param id ID of the user
     */
    protected User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Get the user's name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the user's unique identifier
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Does the user have a specific permission
     * @param permission String
     * @return Boolean
     */
    public abstract boolean hasPermission(String permission);

    /**
     * Implementation of sending the user a message
     * @param message Message to send
     */
    protected abstract void sendMessage(String message);

    /**
     * Send the user a message
     * @param message Message to send
     * @param concatenations Concatenations to main message & variables
     */
    public void sendMessage(Object message, Object... concatenations) {
        if (concatenations.length == 0) {
            sendMessage(message(message));
            return;
        }

        final List<Object> items = new ArrayList<>();
        items.add(message);
        items.addAll(Arrays.asList(concatenations));

        final List<String> messages = items.stream()
                .map(this::message)
                .collect(Collectors.toList());
        final String joined = String.join("", messages);

        final Matcher specificMatcher = SPECIFIC_FORMAT_REGEX.matcher(joined);

        int count = INDEX_FORMAT_REGEX.split(joined, -1).length;

        if (SPECIFIC_FORMAT_REGEX.matcher(joined).matches()) {
            final List<Integer> nums = new ArrayList<>();

            while (specificMatcher.find()) {
                nums.add(Integer.valueOf(specificMatcher.group(1).replace("%", "").replace("$s", "")));
                specificMatcher.group(2);
            }

            final int highest = Collections.max(nums);

            if (highest > count) {
                count = highest;
            }
        }

        if (concatenations.length == 1) {
            sendMessage(String.format(messages.get(0), concatenations[0]));
            return;
        }

        int midIndex = messages.size() - count;
        midIndex = midIndex > 2 ? midIndex : midIndex - 1;

        final String msg = String.join("", messages.subList(0, midIndex));
        final Object[] vars = messages.subList(midIndex, messages.size()).toArray();

        sendMessage(String.format(msg, vars));
    }

    private String message(Object message) {
        return message instanceof LangEnum ? Lang.LanguageGetter.get(((LangEnum) message).getPath()) : String.valueOf(message);
    }
}
