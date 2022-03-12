package net.motimaa.skyblockcore.menus;

import net.motimaa.skyblockcore.SubSystem;
import net.motimaa.skyblockcore.api.SkyblockPlayer;
import net.motimaa.skyblockcore.menus.npc.banker.BankerMenu;
import net.motimaa.skyblockcore.menus.player.MainMenu;
import net.motimaa.skyblockcore.player.PlayerProvider;
import net.motimaa.skyblockcore.protocol.SignGUIFactory;
import net.motimaa.skyblockcore.providers.VaultProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InventoryManager implements SubSystem {

    private final PlayerProvider playerProvider;
    private final VaultProvider vaultProvider;
    private final SignGUIFactory signGUIFactory;

    @Inject
    public InventoryManager(
            PlayerProvider playerProvider,
            VaultProvider vaultProvider,
            SignGUIFactory signGUIFactory
    ) {
        this.playerProvider = playerProvider;
        this.vaultProvider = vaultProvider;
        this.signGUIFactory = signGUIFactory;
    }

    /**
     * Close all open inventories.
     */
    private void closeAll() {
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getOpenInventory().getTopInventory().getHolder() instanceof AbstractInventory)
                .forEach(HumanEntity::closeInventory);
    }

    public void openInventory(Player player, InventoryType type) {
        SkyblockPlayer sbPlayer = playerProvider.get(player);
        switch (type) {
            case BANKER -> player.openInventory(sbPlayer.getInventories().computeIfAbsent(type, k -> new BankerMenu(this.vaultProvider, this.signGUIFactory)).getInventory());
            case MAIN_MENU -> player.openInventory(sbPlayer.getInventories().computeIfAbsent(type, k -> new MainMenu(player)).getInventory());
        }
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
        this.closeAll();
    }
}
