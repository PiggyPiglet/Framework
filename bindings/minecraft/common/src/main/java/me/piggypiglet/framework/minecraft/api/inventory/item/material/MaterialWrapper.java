package me.piggypiglet.framework.minecraft.api.inventory.item.material;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialEnum;
import me.piggypiglet.framework.utils.annotations.internal.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.Map;

public final class MaterialWrapper implements MaterialEnum {
    private static final MaterialEnum UNKNOWN = new MaterialEnum() {
        @Override
        public int getId() {
            return -1;
        }

        @Override
        public int getData() {
            return -1;
        }

        @Nullable
        @Override
        public MaterialName getName() {
            return null;
        }
    };

    @Inject @Internal("material_handles") private static Map<MaterialName, MaterialEnum> handles;

    private final MaterialEnum handle;

    MaterialWrapper(@NotNull final MaterialName name) {
        this.handle = handles.get(name);
    }

    MaterialWrapper() {
        this.handle = UNKNOWN;
    }

    @Override
    public int getId() {
        return handle.getId();
    }

    @Override
    public int getData() {
        return handle.getData();
    }

    @Nullable
    @Override
    public MaterialName getName() {
        return handle.getName();
    }
}
