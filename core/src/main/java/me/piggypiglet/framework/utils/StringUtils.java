package me.piggypiglet.framework.utils;

import me.piggypiglet.framework.lang.Lang;
import me.piggypiglet.framework.lang.LangEnum;
import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringUtils {
    private static final Pattern SPECIFIC_FORMAT_REGEX = Pattern.compile("/%\\d\\$s/g");
    private static final Pattern INDEX_FORMAT_REGEX = Pattern.compile("/%s/g");

    public static boolean anyStartWith(String str, String... elements) {
        return anyStartWith(str, Arrays.asList(elements));
    }

    public static boolean anyStartWith(String str, List<String> elements) {
        return lowercaseStream(elements).anyMatch(str::startsWith);
    }

    public static boolean anyEndWith(String str, String... elements) {
        return anyEndWith(str, Arrays.asList(elements));
    }

    public static boolean anyEndWith(String str, List<String> elements) {
        return lowercaseStream(elements).anyMatch(str::endsWith);
    }

    private static Stream<String> lowercaseStream(List<String> elements) {
        return elements.stream().map(String::toLowerCase);
    }

    public static String addonName(Class<?> addon) {
        if (!addon.isAnnotationPresent(Addon.class)) throw new UnsupportedOperationException("Class provided is not an addon class.");

        return "addon_" + addon.getSimpleName().toLowerCase().replace("addon", "");
    }

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
            return String.format(messages.get(0), concatenations[0]);
        }

        int midIndex = messages.size() - count;
        midIndex = midIndex > 2 ? midIndex : midIndex - 1;

        final String msg = String.join("", messages.subList(0, midIndex));
        final Object[] vars = messages.subList(midIndex, messages.size()).toArray();

        return String.format(msg, vars);
    }

    public static String format(Object message) {
        return message instanceof LangEnum ? Lang.LanguageGetter.get(((LangEnum) message).getPath()) : String.valueOf(message);
    }
}
