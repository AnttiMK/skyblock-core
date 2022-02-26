package net.motimaa.skyblockcore.player;

import net.motimaa.skyblockcore.SkyblockCore;
import net.motimaa.skyblockcore.SubSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerProvider implements SubSystem {

    private final Map<UUID, SkyblockPlayer> players = new ConcurrentHashMap<>();
    private final SkyblockCore plugin;

    @Inject
    public PlayerProvider(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    public SkyblockPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public SkyblockPlayer get(UUID uuid) {
        return players.computeIfAbsent(uuid, k -> new SkyblockPlayer(Bukkit.getPlayer(k)));
    }

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new SkyblockPlayer(player));
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    @Override
    public void enable() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            players.put(player.getUniqueId(), new SkyblockPlayer(player));
        }
    }

    @Override
    public void disable() {
        players.clear();
    }
}
