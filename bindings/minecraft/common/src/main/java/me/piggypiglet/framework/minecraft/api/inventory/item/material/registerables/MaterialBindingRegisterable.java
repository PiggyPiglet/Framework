package me.piggypiglet.framework.minecraft.api.inventory.item.material.registerables;

import com.google.inject.TypeLiteral;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.Material;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.MaterialName;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialEnum;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialVersion;
import me.piggypiglet.framework.minecraft.api.versions.Versions;
import me.piggypiglet.framework.registerables.StartupRegisterable;
import me.piggypiglet.framework.scanning.framework.Scanner;
import me.piggypiglet.framework.utils.annotations.internal.InternalAnnotations;
import me.piggypiglet.framework.utils.annotations.wrapper.AnnotationRules;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
        final MaterialEnum[] implementedMaterials = materialEnum.getEnumConstants();
        final Map<MaterialName, MaterialEnum> bindings = new EnumMap<>(MaterialName.class);

        for (final MaterialEnum material : implementedMaterials) {
            bindings.put(material.getName(), material);
        }

        final List<MaterialName> unimplemented = Arrays.asList(MaterialName.values());
        unimplemented.removeAll(bindings.keySet());

        unimplemented.forEach(name -> bindings.put(name, Material.UNKNOWN));

        addAnnotatedBinding(
                new TypeLiteral<Map<MaterialName, MaterialEnum>>(){},
                InternalAnnotations.internal("material_handles"),
                bindings
        );
    }
}
