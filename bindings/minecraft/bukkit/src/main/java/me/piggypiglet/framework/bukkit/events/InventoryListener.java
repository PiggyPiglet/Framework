package me.piggypiglet.framework.bukkit.events;

import com.google.inject.Inject;
import me.piggypiglet.framework.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class InventoryListener implements Listener {
    @Inject private Logger logger;

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        logger.info(e.getItem().getType().name());
        logger.info(Arrays.stream(e.getDestination().getContents()).map(ItemStack::getType).map(Enum::name).collect(Collectors.joining(", ")));
    }
}
