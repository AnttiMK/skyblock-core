package net.motimaa.skyblockcore.player;

import net.motimaa.skyblockcore.api.SkyblockPlayer;
import net.motimaa.skyblockcore.menus.AbstractInventory;
import net.motimaa.skyblockcore.menus.InventoryType;
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

    @Override
    public Map<InventoryType, AbstractInventory> getInventories() {
        return inventories;
    }

    @Override
    public @NotNull Player player() {
        return player;
    }
}
