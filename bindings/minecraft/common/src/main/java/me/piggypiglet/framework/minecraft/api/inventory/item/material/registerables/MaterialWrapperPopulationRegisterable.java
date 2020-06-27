package me.piggypiglet.framework.minecraft.api.inventory.item.material.registerables;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.Material;
import me.piggypiglet.framework.registerables.StartupRegisterable;

public final class MaterialWrapperPopulationRegisterable extends StartupRegisterable {
    @Override
    protected void execute() {
        Material.VALUES.forEach(material -> material.getWrapper().populate());
    }
}
