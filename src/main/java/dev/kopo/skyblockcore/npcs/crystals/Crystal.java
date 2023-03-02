package dev.kopo.skyblockcore.npcs.crystals;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.kopo.skyblockcore.SkyblockCore;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Crystal {

    private final Location location;
    private final SkyblockCore plugin;
    private final BukkitRunnable task;
    private final List<ProtectedRegion> regions;
    private ArmorStand armorStand;

    public Crystal(Location location, SkyblockCore plugin) {
        this.location = location;
        this.plugin = plugin;
        this.task = new CrystalAnimation(this, plugin);
        this.regions = new ArrayList<>();
        this.spawn();
        this.addRegion("farm");
    }

    public boolean addRegion(String name) {
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));
        if (regionManager == null) {
            throw new RuntimeException("Region manager was null for world " + location.getWorld().toString());
        }
        if (!regionManager.hasRegion(name)) {
            plugin.getLogger().info("No region found with name " + name);
            return false;
        }

        this.regions.add(regionManager.getRegion(name));
        return true;
    }

    public void spawn() {
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        armorStand.setGravity(false);
    }

    public void runTask() {
        try {
            this.task.runTaskTimer(this.plugin, 0, 40);
        } catch (IllegalStateException ignored) {
        }
    }

    public void pauseTask() {
        try {
            this.task.cancel();
        } catch (IllegalStateException ignored) {
        }
        ;
    }

    public Location getLocation() {
        return location;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public List<ProtectedRegion> getRegions() {
        return regions;
    }
}
