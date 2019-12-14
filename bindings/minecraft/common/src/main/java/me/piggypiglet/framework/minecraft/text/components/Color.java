package me.piggypiglet.framework.minecraft.text.components;

public enum Color implements Component {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_CYAN('3'),
    DARK_RED('4'),
    PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    BRIGHT_GREEN('a'),
    CYAN('b'),
    RED('c'),
    PINK('d'),
    YELLOW('e'),
    WHITE('f');

    private final char code;

    Color(char code) {
        this.code = code;
    }

    @Override
    public char getCode() {
        return code;
    }

    public static Color from(char code) {
        for (Color color : values()) {
            if (color.code == code) {
                return color;
            }
        }

        return null;
    }
}
