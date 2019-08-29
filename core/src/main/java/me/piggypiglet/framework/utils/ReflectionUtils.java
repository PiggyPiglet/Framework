package me.piggypiglet.framework.utils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ReflectionUtils {
    /**
     * Get a classes generic, a bit iffy, might not work in all scenarios
     * @param clazz Class to get the generic from
     * @param <T> Type
     * @return Class of the generic type, with the type as the classes generic
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGeneric(Class clazz) {
        try {
            return (Class<T>) Class.forName(clazz.getGenericInterfaces()[0].getTypeName().split("<")[1].replace(">", ""));
        } catch (Exception e) {
            throw new RuntimeException("Something fucked up really badly.");
        }
    }
}
