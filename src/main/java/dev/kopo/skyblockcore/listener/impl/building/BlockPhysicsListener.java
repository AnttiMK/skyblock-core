package dev.kopo.skyblockcore.listener.impl.building;

import dev.kopo.skyblockcore.SkyblockCore;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import javax.inject.Inject;

public class BlockPhysicsListener implements Listener {

    private final SkyblockCore plugin;

    @Inject
    public BlockPhysicsListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        final Block block = event.getBlock();
        if ("world".equalsIgnoreCase(block.getWorld().getName()) && block.getType().hasGravity()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            final Block block = event.getBlock();
            if ("world".equalsIgnoreCase(block.getWorld().getName()) && block.getType().hasGravity()) {
                event.setCancelled(true);
            }
        }
    }

}
