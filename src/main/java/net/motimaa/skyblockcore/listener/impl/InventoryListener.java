package net.motimaa.skyblockcore.listener.impl;

import net.motimaa.skyblockcore.menus.AbstractInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class InventoryListener implements Listener {

    private final JavaPlugin plugin;

    @Inject
    public InventoryListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getInventory().getHolder() instanceof AbstractInventory inv) {
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
