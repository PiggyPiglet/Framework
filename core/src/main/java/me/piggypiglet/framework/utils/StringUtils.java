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

package me.piggypiglet.framework.utils;

import me.piggypiglet.framework.addon.framework.Addon;
import me.piggypiglet.framework.language.framework.LanguageEnum;
import me.piggypiglet.framework.language.internal.LanguageGetter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringUtils {
    private static final Pattern SPECIFIC_FORMAT_REGEX = Pattern.compile("%\\d\\$s");
    private static final Pattern INDEX_FORMAT_REGEX = Pattern.compile("%s");

    private StringUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    /**
     * Check if a string starts with any of the triggers supplied in the
     * varargs.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean startsWithAny(String string, String... triggers) {
        return startsWithAny(string, Arrays.asList(triggers));
    }

    /**
     * Check if a string starts with any of the triggers supplied in the
     * list.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean startsWithAny(String string, List<String> triggers) {
        return anyWith(string, triggers, (t, s) -> s.startsWith(t));
    }

    /**
     * Check if any of the strings supplied in the varargs start with a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyStartWith(String trigger, String... strings) {
        return anyStartWith(trigger, Arrays.asList(strings));
    }

    /**
     * Check if any of the strings supplied in the varargs start with a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyStartWith(String trigger, List<String> strings) {
        return anyWith(trigger, strings, String::startsWith);
    }

    /**
     * Check if a string ends with any of the triggers supplied in the
     * varargs.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean endsWithAny(String string, String... triggers) {
        return endsWithAny(string, Arrays.asList(triggers));
    }

    /**
     * Check if a string ends with any of the triggers supplied in the
     * list.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean endsWithAny(String string, List<String> triggers) {
        return anyWith(string, triggers, (t, s) -> s.endsWith(t));
    }

    /**
     * Check if any of the strings supplied in the varargs end with a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyEndWith(String trigger, String... strings) {
        return anyEndWith(trigger, Arrays.asList(strings));
    }

    /**
     * Check if any of the strings supplied in the list end with a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyEndWith(String trigger, List<String> strings) {
        return anyWith(trigger, strings, String::endsWith);
    }

    /**
     * Check if a string contains any of the triggers supplied in the
     * varargs.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean containsAny(String string, String... triggers) {
        return containsAny(string, Arrays.asList(triggers));
    }

    /**
     * Check if a string contains any of the triggers supplied in the
     * list.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean containsAny(String string, List<String> triggers) {
        return anyWith(string, triggers, (t, s) -> s.contains(t));
    }

    /**
     * Check if any of the strings supplied in the varargs contain a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyContains(String trigger, String... strings) {
        return anyContains(trigger, Arrays.asList(strings));
    }

    /**
     * Check if any of the strings supplied in the list contain a
     * specific trigger.
     *
     * @param trigger Trigger to check against
     * @param strings Strings to check against
     * @return boolean
     */
    public static boolean anyContains(String trigger, List<String> strings) {
        return anyWith(trigger, strings, String::contains);
    }

    /**
     * Check if a pattern matches any of the triggers supplied in the
     * varargs.
     *
     * @param pattern  Pattern to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean matchesAny(Pattern pattern, String... triggers) {
        return matchesAny(pattern, Arrays.asList(triggers));
    }

    /**
     * Check if a pattern matches any of the triggers supplied in the
     * list.
     *
     * @param pattern  Pattern to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean matchesAny(Pattern pattern, List<String> triggers) {
        return anyWith(pattern, triggers, (s, p) -> p.matcher(s).matches());
    }

    /**
     * Check if any of the patterns supplied in the varargs match a
     * specific trigger.
     *
     * @param trigger  Trigger to check against
     * @param patterns Patterns to check against
     * @return boolean
     */
    public static boolean anyMatches(String trigger, Pattern... patterns) {
        return anyMatches(trigger, Arrays.asList(patterns));
    }

    /**
     * Check if any of the patterns supplied in the list match a
     * specific trigger.
     *
     * @param trigger  Trigger to check against
     * @param patterns Patterns to check against
     * @return boolean
     */
    public static boolean anyMatches(String trigger, List<Pattern> patterns) {
        return anyWith(trigger, patterns, (p, s) -> p.matcher(s).matches());
    }

    /**
     * Check if a string equals any of the triggers supplied in the
     * varargs.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean equalsAny(String string, String... triggers) {
        return equalsAny(string, Arrays.asList(triggers));
    }

    /**
     * Check if a string equals any of the triggers supplied in the
     * list.
     *
     * @param string   String to check against
     * @param triggers Triggers to check against
     * @return boolean
     */
    public static boolean equalsAny(String string, List<String> triggers) {
        return anyWith(string, triggers, String::equals);
    }

    /**
     * Check if a list of elements matches a predefined test against a specific element.
     *
     * @param element  Specific element
     * @param elements List of elements
     * @param test     Match test
     * @param <T>      Trigger type
     * @param <U>      Element type
     * @return boolean
     */
    public static <T, U> boolean anyWith(T element, List<U> elements, BiPredicate<U, T> test) {
        final T clone = toLowerCase(element);
        return lowercaseStream(elements).anyMatch(s -> test.test(s, clone));
    }

    private static <T> Stream<T> lowercaseStream(List<T> elements) {
        return elements.stream().map(StringUtils::toLowerCase);
    }

    @SuppressWarnings("unchecked")
    private static <T> T toLowerCase(T element) {
        return element instanceof String ? (T) ((String) element).toLowerCase() : element;
    }

    /**
     * Format an addon class into an addon name
     *
     * @param addon Addon class
     * @return String
     */
    public static String addonName(@NotNull final Class<? extends Addon> addon) {
        return "addon_" + addon.getSimpleName().toLowerCase().replace("addon", "");
    }

    /**
     * Extended implementation of String.format, with support for the Lang system, and type independent
     * concatenations. Currently supports iterative variables, and index variables (%s and %num$s).
     * <p>
     * Examples:
     * <i>Lang.TEST = "Hello %s."</i>
     * <strong>format(Lang.TEST, "World")</strong> = <i>Hello World.</i>
     * <i>Lang.TEST = "Hello %s." | Lang.TEST2 = World</i>
     * <strong>format(Lang.TEST, Lang.TEST2)</strong> = <i>Hello World.</i>
     * <strong>format("Hello %s.", "World")</strong> = <i>Hello World.</i>
     * <p>
     * Keep in mind, when concatenating different types, or declaring variable values, you must separate via
     * a <strong>,</strong>, not the conventional <strong>+</strong>.
     *
     * @param message        Main message
     * @param concatenations Extra concatenations and/or variables.
     * @return Formatted string.
     */
    public static String format(Object message, Object... concatenations) {
        if (concatenations.length == 0) {
            return format(message);
        }

        final List<Object> items = new ArrayList<>();
        items.add(message);
        items.addAll(Arrays.asList(concatenations));

        final List<String> messages = items.stream()
                .map(StringUtils::format)
                .collect(Collectors.toList());
        final String joined = String.join("", messages);
        final Matcher specificMatcher = SPECIFIC_FORMAT_REGEX.matcher(joined);

        int count = INDEX_FORMAT_REGEX.split(joined, -1).length - 1;

        if (specificMatcher.find()) {
            final List<Integer> nums = new ArrayList<>();

            while (specificMatcher.find()) {
                nums.add(Integer.valueOf(specificMatcher.group(0).replace("%", "").replace("$s", "")));
            }

            final int highest = Collections.max(nums);

            if (highest > count) {
                count = highest;
            }
        }

        if (concatenations.length == 1) {
            return String.format(messages.get(0), concatenations[0]);
        }

        final int midIndex = messages.size() - count;
        final String msg = String.join("", messages.subList(0, midIndex));
        final Object[] vars = messages.subList(midIndex, messages.size()).toArray();

        return String.format(msg, vars);
    }

    public static String format(Object message) {
        return message instanceof LanguageEnum ? LanguageGetter.get((LanguageEnum) message) : String.valueOf(message);
    }
}