package me.piggypiglet.framework.utils;

import java.lang.reflect.AccessibleObject;

public final class ReflectionUtils {
    public static <T extends AccessibleObject> T getAccessible(T type) throws Exception {
        type.setAccessible(true);
        return type;
    }
}
