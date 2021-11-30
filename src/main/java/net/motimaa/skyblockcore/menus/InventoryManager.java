package net.motimaa.skyblockcore.menus;

import net.motimaa.skyblockcore.SkyblockCore;
import net.motimaa.skyblockcore.SubSystem;
import net.motimaa.skyblockcore.menus.npc.BankerMenu;
import net.motimaa.skyblockcore.player.PlayerProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class InventoryManager implements SubSystem {

    private final Map<UUID, BankerMenu> bankerGUIMap = new HashMap<>();
    private final PlayerProvider playerProvider;

    @Inject
    public InventoryManager(PlayerProvider playerProvider) {
        this.playerProvider = playerProvider;
    }

    public static void openInventory(Player player, InventoryType type) {
        SkyblockCore.getInstance().getSystem().getPlayerProvider().player(player).openInventory(type);
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
        closeAll();
    }

    /**
     * Close all open inventories.
     */
    private void closeAll() {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getOpenInventory().getTopInventory().getHolder() instanceof AbstractInventory)
                .forEach(Player::closeInventory);
    }
}
