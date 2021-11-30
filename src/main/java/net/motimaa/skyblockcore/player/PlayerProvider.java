package net.motimaa.skyblockcore.player;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerProvider {

    private final Map<UUID, SkyblockPlayer> players = new ConcurrentHashMap<>();
    private final JavaPlugin plugin;

    @Inject
    public PlayerProvider(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public SkyblockPlayer player(Player player) {
        return player(player.getUniqueId()).orElseThrow(() -> new IllegalStateException("Player not available!"));
    }

    public Optional<SkyblockPlayer> player(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public void addPlayer(Player player) {
        this.players.put(player.getUniqueId(), new SkyblockPlayer(player));
    }

    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
    }

}
