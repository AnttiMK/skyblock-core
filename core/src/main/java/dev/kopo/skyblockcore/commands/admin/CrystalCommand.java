package dev.kopo.skyblockcore.commands.admin;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.commands.SkyblockCommand;
import dev.kopo.skyblockcore.mechanics.regeneration.crystals.CrystalTracker;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CrystalCommand extends SkyblockCommand implements Listener {

    private final CrystalTracker crystalTracker;
    private final SkyblockCore plugin;
    private final Set<UUID> awaitingClick;

    @Inject
    public CrystalCommand(CrystalTracker crystalTracker, SkyblockCore plugin) {
        super("crystal");
        this.crystalTracker = crystalTracker;
        this.plugin = plugin;
        this.awaitingClick = new HashSet<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("In-game only command!");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("Not enough arguments!");
            return true;
        }
        if ("create".equalsIgnoreCase(args[0])) {
            player.sendMessage("Place block where you want to create crystal");
            this.awaitingClick.add(player.getUniqueId());
            return true;
        }
        if ("reset".equalsIgnoreCase(args[0])) {
            crystalTracker.reset();
            player.sendMessage("Reset crystals!");
            return true;
        }
        if ("run".equalsIgnoreCase(args[0])) {
            crystalTracker.runAll();
            player.sendMessage("Running all tasks");
            return true;
        }
        if ("stop".equalsIgnoreCase(args[0])) {
            crystalTracker.stopAll();
            player.sendMessage("Stopped all tasks");
            return true;
        }
        player.sendMessage("Unknown subcommand!");
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!awaitingClick.contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
        awaitingClick.remove(event.getPlayer().getUniqueId());

        Location location = event.getBlockPlaced().getLocation();
        if (crystalTracker.existsAtLocation(location)) {
            event.getPlayer().sendMessage("Crystal already exists here!");
        }
        crystalTracker.createCrystal(location.clone().set(location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5));
        event.getPlayer().sendMessage("Crystal created!");
    }


}
