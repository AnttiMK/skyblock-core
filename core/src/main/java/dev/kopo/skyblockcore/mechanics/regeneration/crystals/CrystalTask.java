package dev.kopo.skyblockcore.mechanics.regeneration.crystals;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.mechanics.regeneration.RegenLocation;
import io.papermc.paper.math.Rotations;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayDeque;
import java.util.Queue;

class CrystalTask extends BukkitRunnable {

    private static final int TICKS_PER_CYCLE = 40;
    private final Queue<RegenLocation> regrowQueue;
    private final Location animationLocation;
    private final double originalY;
    private final Crystal crystal;
    private final SkyblockCore plugin;
    private int ticker = 0;

    public CrystalTask(Crystal crystal, SkyblockCore plugin) {
        this.crystal = crystal;
        this.plugin = plugin;
        this.regrowQueue = new ArrayDeque<>();
        this.animationLocation = crystal.getLocation().clone();
        this.originalY = this.animationLocation.getY();
    }

    @Override
    public void run() {
        if (!this.crystal.getLocation().isChunkLoaded()) return;
        this.animationLocation.setY(originalY + Math.sin(2 * Math.PI * ticker / TICKS_PER_CYCLE));
        this.crystal.getArmorStand().teleport(animationLocation);
        this.crystal.getArmorStand().setHeadRotations(Rotations.ofDegrees(0, (double) (360 * ticker * 2) / TICKS_PER_CYCLE, 0));

        if (this.ticker == 0) {
            doRegrowTask();
        }

        this.ticker++;
        if (this.ticker == TICKS_PER_CYCLE) {
            this.ticker = 0;
        }
    }

    private void doRegrowTask() {
            /*
            for (ProtectedRegion region : crystal.getRegions()) {
                AbstractRegion weRegion;
                if (region instanceof ProtectedCuboidRegion reg) {
                    weRegion = new CuboidRegion(BukkitAdapter.adapt(crystal.getLocation().getWorld()), reg.getMinimumPoint(), reg.getMaximumPoint());
                } else {
                    weRegion = new Polygonal2DRegion(BukkitAdapter.adapt(crystal.getLocation().getWorld()), region.getPoints(), 0, 256);
                }

                for (BlockVector3 bv : weRegion) {
                    Location loc = BukkitAdapter.adapt(crystal.getLocation().getWorld(), bv);

             */
        if (regrowQueue.peek() == null) {
            return;
        }

        RegenLocation toRegrow = regrowQueue.poll();
        Location loc = toRegrow.location();
        if (!loc.getChunk().isLoaded()) return;

        Block block = loc.getBlock();
        block.setType(toRegrow.material());
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Ageable data) {
            data.setAge(data.getMaximumAge());
            block.setBlockData(data);
        }

        playPlaceAnimation(
                this.animationLocation.clone().add(0, 1.6875, 0).toVector(),
                block.getLocation().clone().add(0.5, 0, 0.5).toVector(),
                crystal.getLocation().getWorld(),
                0.5D
        );
    }

    private void playPlaceAnimation(Vector startLocation, Vector endLocation, World world, double blockInterval) {
        plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Vector direction = endLocation.clone().subtract(startLocation).normalize();
            for (double i = blockInterval; i * i < startLocation.distanceSquared(endLocation); i += blockInterval) {
                Location position = getPointOnRayTrace(startLocation, direction, i).toLocation(world);
                world.spawnParticle(Particle.FIREWORKS_SPARK, position, 1, 0, 0, 0, 0);
            }
        });
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

    public void queueRegrow(RegenLocation regrowLocation) {
        this.regrowQueue.add(regrowLocation);
    }
}
