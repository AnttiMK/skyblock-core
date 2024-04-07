package dev.kopo.skyblockcore.listener.impl.general;

import dev.kopo.skyblockcore.player.PlayerProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerLoginListener implements Listener {

    private final PlayerProvider playerProvider;

    @Inject
    public PlayerLoginListener(PlayerProvider playerProvider) {
        this.playerProvider = playerProvider;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerJoinEvent event) {
        playerProvider.addPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerProvider.removePlayer(event.getPlayer().getUniqueId());
    }
}
