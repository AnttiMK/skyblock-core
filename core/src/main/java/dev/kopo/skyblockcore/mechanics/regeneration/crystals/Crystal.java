package dev.kopo.skyblockcore.mechanics.regeneration.crystals;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.mechanics.regeneration.RegenLocation;
import dev.kopo.skyblockcore.util.items.SkullGenerator;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Crystal {

    private final Location location;
    private final SkyblockCore plugin;
    private final CrystalTask tickingTask;
    private final List<ProtectedRegion> regions;
    private ArmorStand armorStand;

    public Crystal(Location location, SkyblockCore plugin) {
        this.location = location;
        this.plugin = plugin;
        this.tickingTask = new CrystalTask(this, plugin);
        this.regions = new ArrayList<>();
        this.spawn();
        this.addRegion("farm");
    }

    public boolean addRegion(String name) {
        // TODO move impl to command class for feedback
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));
        if (regionManager == null) {
            throw new RuntimeException("Region manager was null for world " + location.getWorld().toString());
        }

        ProtectedRegion region = regionManager.getRegion(name);
        if (region == null) {
            plugin.getLogger().info("No region found with name " + name);
            return false;
        }

        if (this.regions.contains(region)) {
            plugin.getLogger().info("Region " + name + " is already added");
            return false;
        }

        if (region instanceof GlobalProtectedRegion) {
            plugin.getLogger().info("Region " + name + " is global, skipping");
            return false;
        }
        this.regions.add(region);
        return true;
    }

    public boolean onBlockBreak(BlockBreakEvent event) {
        for (ProtectedRegion region : this.regions) {
            if (region.contains(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ())) {
                event.getBlock().getState()
                this.tickingTask.queueRegrow(new RegenLocation(event.getBlock().getLocation(), event.getBlock().getBlockData().clone()));
                return true;
            }
        }
        return false;
    }

    public void spawn() {
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class, CreatureSpawnEvent.SpawnReason.CUSTOM);
        armorStand.setAI(false);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);

        armorStand.setItem(EquipmentSlot.HEAD, skullItem());
    }

    private ItemStack skullItem() {
        return SkullGenerator.fromTextures("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTEyMjQwODY2MmFmNWIxYWRmN2FkNTkyMTEzMjgyYzM0Zjc3MTQxOGVlZWVkNGZmOTJlYTE4MTQwZjk5MWMxZCJ9fX0=");
    }

    public void runTask() {
        try {
            this.tickingTask.runTaskTimer(this.plugin, 0, 1);
        } catch (IllegalStateException ignored) {
        }
    }

    public void pauseTask() {
        try {
            this.tickingTask.cancel();
        } catch (IllegalStateException ignored) {
        }
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
