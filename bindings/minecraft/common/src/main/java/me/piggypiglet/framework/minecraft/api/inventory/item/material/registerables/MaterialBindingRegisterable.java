package me.piggypiglet.framework.minecraft.api.inventory.item.material.registerables;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialEnum;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialVersion;
import me.piggypiglet.framework.minecraft.api.versions.Versions;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationRules;

import javax.inject.Inject;

public final class MaterialBindingRegisterable extends StartupRegisterable {
    @Inject private Scanner scanner;
    @Inject private Versions version;

    @SuppressWarnings("unchecked")
    @Override
    protected void execute() {
        final Class<? extends MaterialEnum> materialEnum = (Class<? extends MaterialEnum>) scanner.getClassesAnnotatedWith(AnnotationRules.builder()
                .hasAnnotation(MaterialVersion.class)
                .customRule(element -> element.getAnnotation(MaterialVersion.class).value() == version)
                .build()).stream()
                .filter(MaterialEnum.class::isAssignableFrom)
                .findAny().orElseThrow(() -> new AssertionError("Material enum for this version not found."));

        materialEnum.getEnumConstants();
    }
}
