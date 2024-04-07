package dev.kopo.skyblockcore.mechanics.regeneration;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public record RegenLocation(Location location, BlockData blockData, long brokenAt) {

}
