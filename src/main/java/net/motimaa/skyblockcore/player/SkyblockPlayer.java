package net.motimaa.skyblockcore.player;

import net.motimaa.skyblockcore.menus.AbstractInventory;
import net.motimaa.skyblockcore.menus.InventoryType;
import net.motimaa.skyblockcore.menus.npc.BankerMenu;
import net.motimaa.skyblockcore.menus.player.MainMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SkyblockPlayer {

    private final Player player;
    private final Map<InventoryType, AbstractInventory> inventories = new HashMap<>();

    public SkyblockPlayer(Player player) {
        this.player = player;
    }

    public void openInventory(InventoryType type) {
        switch (type) {
            case BANKER -> this.player.openInventory(inventories.computeIfAbsent(type, k -> new BankerMenu(player)).getInventory());
            case MAIN_MENU -> this.player.openInventory(inventories.computeIfAbsent(type, k -> new MainMenu(player)).getInventory());
        }
    }

}
