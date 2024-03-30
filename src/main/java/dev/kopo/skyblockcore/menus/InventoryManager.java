package dev.kopo.skyblockcore.menus;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.SubSystem;
import dev.kopo.skyblockcore.api.SkyblockPlayer;
import dev.kopo.skyblockcore.menus.npc.banker.BankerMenu;
import dev.kopo.skyblockcore.menus.player.MainMenu;
import dev.kopo.skyblockcore.player.PlayerProvider;
import dev.kopo.skyblockcore.protocol.SignGUIFactory;
import dev.kopo.skyblockcore.providers.VaultProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InventoryManager implements SubSystem {

    private final SkyblockCore plugin;
    private final PlayerProvider playerProvider;
    private final VaultProvider vaultProvider;
    private final SignGUIFactory signGUIFactory;

    @Inject
    public InventoryManager(
            SkyblockCore plugin,
            PlayerProvider playerProvider,
            VaultProvider vaultProvider,
            SignGUIFactory signGUIFactory
    ) {
        this.plugin = plugin;
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
                .forEach(Player::closeInventory);
    }

    public void openInventory(Player player, InventoryType type) {
        SkyblockPlayer sbPlayer = playerProvider.get(player);
        switch (type) {
            case BANKER ->
                    player.openInventory(sbPlayer.getInventories().computeIfAbsent(type, k -> new BankerMenu(plugin, vaultProvider, signGUIFactory)).getInventory());
            case MAIN_MENU ->
                    player.openInventory(sbPlayer.getInventories().computeIfAbsent(type, k -> new MainMenu()).getInventory());
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
