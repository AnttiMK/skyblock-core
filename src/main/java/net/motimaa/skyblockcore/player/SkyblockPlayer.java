package net.motimaa.skyblockcore.player;

import net.motimaa.skyblockcore.menus.AbstractInventory;
import net.motimaa.skyblockcore.menus.InventoryType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SkyblockPlayer {

    private final Player player;
    private final Map<InventoryType, AbstractInventory> inventories = new HashMap<>();

    public SkyblockPlayer(Player player) {
        this.player = player;
    }

    public Map<InventoryType, AbstractInventory> getInventories() {
        return inventories;
    }
}
