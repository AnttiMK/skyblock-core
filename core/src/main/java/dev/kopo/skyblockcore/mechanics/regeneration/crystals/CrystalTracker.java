package dev.kopo.skyblockcore.mechanics.regeneration.crystals;

import dev.kopo.skyblockcore.SkyblockCore;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Singleton
public class CrystalTracker {

    private final SkyblockCore plugin;
    private final List<Crystal> crystals;

    @Inject
    public CrystalTracker(SkyblockCore plugin) {
        this.plugin = plugin;
        this.crystals = new ArrayList<>();
    }

    public void createCrystal(Location location) throws IllegalArgumentException {
        if (existsAtLocation(location)) {
            throw new IllegalArgumentException("A crystal already exists at this location!");
        }
        Crystal crystal = new Crystal(location, plugin);
        this.crystals.add(crystal);
    }

    public boolean onBlockBreak(BlockBreakEvent event) {
        for (Crystal crystal : this.crystals) {
            if (crystal.getLocation().getWorld().equals(event.getBlock().getWorld())) {
                return crystal.onBlockBreak(event);
            }
        }
        return false;
    }

    public void reset() {
        Iterator<Crystal> iterator = this.crystals.listIterator();
        while (iterator.hasNext()) {
            Crystal crystal = iterator.next();
            crystal.pauseTask();
            crystal.getArmorStand().remove();
            iterator.remove();
        }
    }

    public List<Crystal> getCrystals() {
        return this.crystals;
    }

    public boolean existsAtLocation(Location location) {
        for (Crystal crystal : this.crystals) {
            if (crystal.getLocation().getBlockX() == location.getBlockX() &&
                    crystal.getLocation().getBlockY() == location.getBlockY() &&
                    crystal.getLocation().getBlockZ() == location.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public void runAll() {
        for (Crystal crystal : this.crystals) {
            crystal.runTask();
        }
    }

    public void stopAll() {
        for (Crystal crystal : this.crystals) {
            crystal.pauseTask();
        }
    }
}
