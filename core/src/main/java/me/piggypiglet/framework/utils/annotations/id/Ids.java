package me.piggypiglet.framework.utils.annotations.id;

import com.google.auto.value.AutoAnnotation;

public final class Ids {
    @AutoAnnotation
    public static ID id(String value) {
        return new AutoAnnotation_Ids_id(value);
    }
}
