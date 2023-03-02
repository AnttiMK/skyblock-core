package dev.kopo.skyblockcore.npcs.crystals;

import com.google.common.collect.ImmutableList;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.kopo.skyblockcore.SkyblockCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.StreamSupport;

class CrystalAnimation extends BukkitRunnable {

    private final Crystal crystal;
    private final SkyblockCore plugin;

    public CrystalAnimation(Crystal crystal, SkyblockCore plugin) {
        this.crystal = crystal;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (ProtectedRegion region : crystal.getRegions()) {
            if (!(region instanceof ProtectedCuboidRegion reg)) {
                plugin.getLogger().warning(region.getId() + " wasn't a cuboid, removing from crystal!");
                crystal.getRegions().remove(region);
                continue;
            }

            CuboidRegion weRegion = new CuboidRegion(BukkitAdapter.adapt(crystal.getLocation().getWorld()), reg.getMinimumPoint(), reg.getMaximumPoint());
            StreamSupport.stream(weRegion.spliterator(), true)
                    .map(bv -> BukkitAdapter.adapt(crystal.getLocation().getWorld(), bv))
                    .filter(location -> location.getChunk().isLoaded())
                    .map(Location::getBlock)
                    .filter(block -> block.getType() == Material.FARMLAND)
                    .map(block -> block.getRelative(BlockFace.UP));
            List<BlockVector3> test = ImmutableList.copyOf(weRegion);
            for (BlockVector3 bv : weRegion) {
                Location loc = BukkitAdapter.adapt(crystal.getLocation().getWorld(), bv);
                if (!loc.getChunk().isLoaded()) continue;
                Block block = loc.getBlock();
                if (!block.getType().equals(Material.FARMLAND)) continue;
                Block blockOnTop = block.getRelative(BlockFace.UP);
                if (!blockOnTop.getType().equals(Material.AIR)) continue;

                blockOnTop.setType(Material.WHEAT);
                BlockData blockData = blockOnTop.getBlockData();
                if (blockData instanceof Ageable data) {
                    data.setAge(data.getMaximumAge());
                    blockOnTop.setBlockData(data);
                }

                playAnimation(crystal.getLocation().toVector(), blockOnTop.getLocation().toCenterLocation().toVector(), crystal.getLocation().getWorld(), 0.5D);
                return;
            }
        }

    }

    private void playAnimation(Vector startLocation, Vector endLocation, World world, double interval) {
        Vector direction = endLocation.clone().subtract(crystal.getLocation().toVector()).normalize();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (double i = interval; i * i < startLocation.distanceSquared(endLocation); i += interval) {
                    Location position = getPointOnRayTrace(startLocation, direction, i).toLocation(world);
                    world.spawnParticle(Particle.FIREWORKS_SPARK, position, 1, 0, 0, 0, 0);
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

    /**
     * Gets a point on the ray trace
     *
     * @param distance The distance from the original location
     * @return The position as a vector
     */
    private Vector getPointOnRayTrace(Vector startLocation, Vector direction, double distance) {
        return startLocation.clone().add(direction.clone().multiply(distance));
    }
}
