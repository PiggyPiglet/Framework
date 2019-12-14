package me.piggypiglet.framework.minecraft.text.components;

public interface Component {
    default String getName() {
        return toString().toLowerCase();
    }

    char getCode();

    static Component from(char code) {
        final Color color = Color.from(code);
        final Style style = Style.from(code);

        return color == null ? style : color;
    }
}
