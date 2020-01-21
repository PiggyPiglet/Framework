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
                    case "show_text":
                        value = Text.of((String) value);
                        extra.put("hoverEvent", map);
                        break;

                    default:
                        extra.put("clickEvent", map);
                        break;
                }

                map.put("action", type);
                map.put("value", value);
            }

            final char[] codes = key.replace("&", "").toCharArray();

            Component color = null;
            Component style = null;

            for (int i = codes.length - 1; i >= 0; --i) {
                final Component component = Component.from(codes[i]);

                if (component instanceof Color) {
                    color = component;
                } else if (component instanceof Style) {
                    style = component;
                }

                if (style != null || color != null) break;
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
