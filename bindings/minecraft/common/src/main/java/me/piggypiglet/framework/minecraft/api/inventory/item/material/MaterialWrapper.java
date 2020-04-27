package me.piggypiglet.framework.minecraft.api.inventory.item.material;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialEnum;
import me.piggypiglet.framework.utils.annotations.internal.Internal;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Map;

public final class MaterialWrapper implements MaterialEnum {
    @Inject @Internal("material_handles") private static Map<String, MaterialEnum> handles;

    private final MaterialEnum handle;

    MaterialWrapper(@NotNull final String handle) {
        this.handle = handles.get(handle);
    }

    @Override
    public int getId() {
        return handle.getId();
    }

    @Override
    public int getData() {
        return handle.getData();
    }
}
