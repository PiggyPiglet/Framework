package me.piggypiglet.framework.minecraft.text.components;

public enum Style implements Component {
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    private final char code;

    Style(char code) {
        this.code = code;
    }

    @Override
    public char getCode() {
        return code;
    }

    public static Style from(char code) {
        for (Style style : values()) {
            if (style.code == code) {
                return style;
            }
        }

        return null;
    }
}
