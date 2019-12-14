package me.piggypiglet.framework.minecraft.text;

import me.piggypiglet.framework.minecraft.text.components.Color;
import me.piggypiglet.framework.minecraft.text.components.Component;
import me.piggypiglet.framework.minecraft.text.components.Style;
import me.piggypiglet.framework.utils.map.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Text {
    private static final Pattern TRIGGERS;
    private static final Pattern JAVA_TRIGGERS;

    static {
        final String javaTriggers = "&(" + String.join("|", "open_url", "show_text", "suggest_command", "run_command") + ")=<.*?>";

        TRIGGERS = Pattern.compile(
                "(" + javaTriggers +
                        "|(&([a-f]|[k-o]|r|[0-9])))+"
        );

        JAVA_TRIGGERS = Pattern.compile(javaTriggers);
    }

    public static Map<String, Object> of(String input) {
        final Matcher matcher = TRIGGERS.matcher(input);
        final List<Map<String, Object>> extras = new ArrayList<>();

        boolean start = false;

        while (matcher.find()) {
            if (matcher.start() == 0) start = true;

            String key = matcher.group(0);
            final Map<String, Object> extra = Maps.of(new HashMap<String, Object>())
                    .key("text").value(TRIGGERS.split(input.substring(matcher.end()))[0])
                    .build();

            final Matcher javaMatcher = JAVA_TRIGGERS.matcher(key);

            while (javaMatcher.find()) {
                final String[] parts = javaMatcher.group(0).replaceFirst("&", "").split("=");
                final String type = parts[0];
                Object value = parts[1].replace("<", "").replace(">", "");

                key = key.replace("&" + type + "=" + parts[1], "");

                final Map<String, Object> map = new HashMap<>();

                switch (type) {
                    case "suggest_command":
                    case "run_command":
                    case "open_url":
                        extra.put("clickEvent", map);
                        break;

                    case "show_text":
                        value = Text.of((String) value);
                        extra.put("hoverEvent", map);
                        break;
                }

                map.put("action", type);
                map.put("value", value);
            }

            final char[] codes = key.replaceAll("&", "").toCharArray();

            Component color = null;
            Component style = null;

            for (int i = codes.length - 1; i >= 0; --i) {
                final Component component = Component.from(codes[i]);

                if (component instanceof Color && color == null) {
                    color = component;

                    if (style != null) break;
                } else if (component instanceof Style && style == null) {
                    style = component;

                    if (color != null) break;
                }
            }

            if (style == Style.RESET) {
                extras.add(extra);
                continue;
            }

            if (color != null) extra.put("color", color.getName());
            if (style != null) extra.put(style.getName(), "true");

            extras.add(extra);
        }

        final Map<String, Object> parent = new HashMap<>();

        parent.put("text", "");

        if (extras.isEmpty()) {
            parent.put("text", input);
        } else {
            parent.put("extra", extras);
        }

        if (!start) parent.put("text", TRIGGERS.split(input)[0]);

        return parent;
    }

    public static String redactJavaTriggers(String input) {
        return JAVA_TRIGGERS.matcher(input).replaceAll("");
    }
}
