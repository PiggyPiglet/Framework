package me.piggypiglet.framework.utils.builder;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class BuilderUtils {
    private BuilderUtils() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static void checkVars(String builderName, Object... vars) {
        final String unsetVars = Arrays.stream(vars).filter(o -> {
            try {
                return ((String) o).startsWith("d-");
            } catch (Exception e) {
                return false;
            }
        }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

        if (!unsetVars.isEmpty()) throw new UnsetVarsException("These required vars weren't set in your " + builderName + ": " + unsetVars);
    }
}
