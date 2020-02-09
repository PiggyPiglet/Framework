/*
 * MIT License
 *
 * Copyright (c) 2019-2020 MiniDigger
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

import net.kyori.text.Component;
import net.kyori.text.ComponentBuilders;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {
    // regex group names
    private static final String START = "start";
    private static final String TOKEN = "token";
    private static final String INNER = "inner";
    private static final String END = "end";
    // https://regex101.com/r/8VZ7uA/5
    private static final Pattern PATTERN = Pattern.compile("((?<start>(?<!\\\\)<)(?<token>([^<>]+)|([^<>]+\"(?<inner>[^\"]+)\"))(?<end>(?<!\\\\)>))+?");
    private static final Pattern JAVA_PATTERN = Pattern.compile("<(click|hover):.*\".*\">", Pattern.DOTALL);
    private static final Pattern END_PATTERN = Pattern.compile("</.*>");
    private static final Pattern START_PATTERN = Pattern.compile("<(.*)>");

    private static final String CLICK = "click";
    private static final String HOVER = "hover";

    private static final String CLOSE_TAG = "/";
    private static final String SEPARATOR = ":";

    public static String escapeTokens(@Nonnull final String richMessage) {
        final StringBuilder sb = new StringBuilder();
        final Matcher matcher = PATTERN.matcher(richMessage);
        int lastEnd = 0;

        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();

            if (startIndex > lastEnd) {
                sb.append(richMessage, lastEnd, startIndex);
            }

            lastEnd = endIndex;

            final String start = matcher.group(START);
            final String inner = matcher.group(INNER);
            final String end = matcher.group(END);
            String token = matcher.group(TOKEN);

            // also escape inner
            if (inner != null) {
                token = token.replace(inner, escapeTokens(inner));
            }

            sb.append("\\").append(start).append(token).append("\\").append(end);
        }

        if (richMessage.length() > lastEnd) {
            sb.append(richMessage.substring(lastEnd));
        }

        return sb.toString();
    }

    public static String stripTokens(@Nonnull final String richMessage) {
        final StringBuilder sb = new StringBuilder();
        final Matcher matcher = PATTERN.matcher(richMessage);
        int lastEnd = 0;

        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();

            if (startIndex > lastEnd) {
                sb.append(richMessage, lastEnd, startIndex);
            }

            lastEnd = endIndex;
        }

        if (richMessage.length() > lastEnd) {
            sb.append(richMessage.substring(lastEnd));
        }

        return sb.toString();
    }

    public static String stripJavaTokens(@Nonnull final String richMessage) {
        return JAVA_PATTERN.matcher(richMessage).replaceAll("");
    }

    public static String legacy(@Nonnull String richMessage) {
        richMessage = stripJavaTokens(richMessage);
        richMessage = END_PATTERN.matcher(richMessage).replaceAll("");

        final Matcher codes = START_PATTERN.matcher(richMessage);

        while (codes.find()) {
            final String name = codes.group(1);

            try {
                richMessage = richMessage.replace("<" + name + ">", "&" + LegacyCodes.valueOf(name.toUpperCase()).getCode());
            } catch (Exception ignored) {}
        }

        return richMessage;
    }

    public static Component parseFormat(@Nonnull final String richMessage) {
        TextComponent.Builder builder = null;

        final Stack<ClickEvent> clickEvents = new Stack<>();
        final Stack<HoverEvent> hoverEvents = new Stack<>();
        final Stack<TextColor> colors = new Stack<>();
        final EnumSet<TextDecoration> decorations = EnumSet.noneOf(TextDecoration.class);

        final Matcher matcher = PATTERN.matcher(richMessage);
        int lastEnd = 0;

        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();

            String msg = null;

            if (startIndex > lastEnd) {
                msg = richMessage.substring(lastEnd, startIndex).replace("\\<", "<").replace("\\>", ">");
            }

            lastEnd = endIndex;

            // handle message
            if (msg != null && msg.length() != 0) {
                // append message
                if (builder == null) {
                    builder = ComponentBuilders.text(msg);
                    style(clickEvents, hoverEvents, colors, decorations, builder);
                } else {
                    builder.append(msg, b -> style(clickEvents, hoverEvents, colors, decorations, b));
                }
            }

            final String token = matcher.group(TOKEN);
            final String inner = matcher.group(INNER);

            Optional<TextDecoration> deco;
            Optional<TextColor> color;

            // click
            if (token.startsWith(CLICK + SEPARATOR)) {
                clickEvents.push(handleClick(token));
            } else if (token.equals(CLOSE_TAG + CLICK)) {
                clickEvents.pop();
            }
            // hover
            else if (token.startsWith(HOVER + SEPARATOR)) {
                hoverEvents.push(handleHover(token, inner));
            } else if (token.equals(CLOSE_TAG + HOVER)) {
                hoverEvents.pop();
            }
            // decoration
            else if ((deco = resolveDecoration(token)).isPresent()) {
                decorations.add(deco.get());
            } else if (token.startsWith(CLOSE_TAG) && (deco = resolveDecoration(token.replace(CLOSE_TAG, ""))).isPresent()) {
                decorations.remove(deco.get());
            }
            // color
            else if ((color = resolveColor(token)).isPresent()) {
                colors.push(color.get());
            } else if (token.startsWith(CLOSE_TAG) && resolveColor(token.replace(CLOSE_TAG, "")).isPresent()) {
                colors.pop();
            }
        }

        // handle last message part
        if (richMessage.length() > lastEnd) {
            final String msg = richMessage.substring(lastEnd).replace("\\<", "<").replace("\\>", ">");
            // append message
            if (builder == null) {
                builder = ComponentBuilders.text(msg);
                style(clickEvents, hoverEvents, colors, decorations, builder);
            } else {
                builder.append(msg, b -> style(clickEvents, hoverEvents, colors, decorations, b));
            }
        }

        if (builder == null) {
            // lets just return an empty component
            builder = ComponentBuilders.text("");
        }

        return builder.build();
    }

    private static void style(@Nonnull final Stack<ClickEvent> clickEvents, @Nonnull final Stack<HoverEvent> hoverEvents,
                              @Nonnull final Stack<TextColor> colors, @Nonnull final EnumSet<TextDecoration> decorations,
                              @Nonnull final TextComponent.Builder builder) {
        if (clickEvents.size() > 0) builder.clickEvent(clickEvents.peek());
        if (hoverEvents.size() > 0) builder.hoverEvent(hoverEvents.peek());
        if (colors.size() > 0) builder.color(colors.peek());
        builder.decorations(new HashSet<>(Arrays.asList(TextDecoration.values())), false);
        if (decorations.size() > 0) {
            for (TextDecoration decor : decorations) {
                builder.decoration(decor, true);
            }
        }
    }

    private static ClickEvent handleClick(@Nonnull final String token) {
        final String[] args = token.split(SEPARATOR);

        if (args.length < 2) {
            throw new RuntimeException("Can't parse click action (too few args) " + token);
        }

        final ClickEvent.Action action = ClickEvent.Action.valueOf(args[1].toUpperCase());
        return ClickEvent.of(action, token.replace(CLICK + SEPARATOR + args[1] + SEPARATOR, ""));
    }

    private static HoverEvent handleHover(@Nonnull final String token, @Nonnull final String inner) {
        final String[] args = token.split(SEPARATOR);

        if (args.length < 2) {
            throw new RuntimeException("Can't parse hover action (too few args) " + token);
        }

        final HoverEvent.Action action = HoverEvent.Action.valueOf(args[1].toUpperCase());
        return HoverEvent.of(action, parseFormat(inner));
    }

    private static Optional<TextColor> resolveColor(@Nonnull final String token) {
        try {
            return Optional.of(TextColor.valueOf(token.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private static Optional<TextDecoration> resolveDecoration(@Nonnull final String token) {
        try {
            return Optional.of(TextDecoration.valueOf(token.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}