package dev.kopo.skyblockcore.listener.impl.npc;

import dev.kopo.skyblockcore.mechanics.regeneration.crystals.CrystalTracker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class FarmBlockListener implements Listener {

    private final CrystalTracker crystalTracker;

    @Inject
    public FarmBlockListener(CrystalTracker crystalTracker) {
        this.crystalTracker = crystalTracker;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!(block.getType().equals(Material.WHEAT))) {
            return;
        }

        if (crystalTracker.onBlockBreak(event)) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            block.setType(Material.AIR);
            player.getInventory().addItem(new ItemStack(block.getType()));
        }
    }
}
