package dev.kopo.skyblockcore.player;

import dev.kopo.skyblockcore.api.inventory.InventoryType;
import dev.kopo.skyblockcore.api.player.SkyblockPlayer;
import dev.kopo.skyblockcore.menus.AbstractInventory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SkyblockPlayerImpl implements SkyblockPlayer {

    private final Player player;
    private final Map<InventoryType, AbstractInventory> inventories = new HashMap<>();

    public SkyblockPlayerImpl(Player player) {
        this.player = player;
    }

    public Map<InventoryType, AbstractInventory> getInventories() {
        return inventories;
    }

    @Override
    public @NotNull Player player() {
        return player;
    }
}
