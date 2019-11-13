package me.piggypiglet.framework.minecraft.versions.material;

public interface Material {
    int getId();

    default int getData() {
        return -1;
    }
}
