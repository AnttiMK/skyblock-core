package dev.kopo.skyblockcore.listener.impl.general;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.menus.AbstractInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import javax.inject.Inject;

public class InventoryListener implements Listener {

    private final SkyblockCore plugin;

    @Inject
    public InventoryListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
//        plugin.getLogger().info("");
//        plugin.getLogger().info(String.valueOf(e.getInventory().getHolder() instanceof AbstractInventory));
//        plugin.getLogger().info(String.valueOf(e.getClickedInventory() != null));
        if (e.getInventory().getHolder() instanceof AbstractInventory inv && e.getClickedInventory() != null) {
            boolean wasCancelled = e.isCancelled();
            e.setCancelled(true);
            inv.handleClick(e);

            // This prevents un-canceling the event if another plugin canceled it before
            if (!wasCancelled && !e.isCancelled()) {
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof AbstractInventory inv) {
            inv.handleOpen(e);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof AbstractInventory inv) {
            if (inv.handleClose(e)) {
                Bukkit.getScheduler().runTask(this.plugin, () -> inv.open((Player) e.getPlayer()));
            }
        }
    }

}
