package me.piggypiglet.framework.utils;

import me.piggypiglet.framework.utils.annotations.addon.Addon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class StringUtils {
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

    public static String formatAddon(Class<?> addon) {
        if (!addon.isAnnotationPresent(Addon.class)) throw new UnsupportedOperationException("Class provided is not an addon class.");

        return "addon_" + addon.getSimpleName().toLowerCase().replace("addon", "");
    }
}
