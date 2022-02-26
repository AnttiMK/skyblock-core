package net.motimaa.skyblockcore.commands.general;

import net.kyori.adventure.text.Component;
import net.motimaa.skyblockcore.SkyblockCore;
import net.motimaa.skyblockcore.commands.SkyblockCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpawnCommand extends SkyblockCommand {

    private final SkyblockCore plugin;
    private final FileConfiguration config;

    @Inject
    public SpawnCommand(SkyblockCore plugin) {
        super("spawn");
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            if (args.length == 0) {
                sender.sendMessage(Component.text("Käyttö: /spawn <pelaaja>"));
                return true;
            }
            handleTeleport(sender, Bukkit.getPlayer(args[0]));
            return true;
        }

        if (args.length == 0) {
            handleTeleport(player, player);
        } else if (args.length == 1) {
            if (!player.hasPermission("motimaa.mod")) {
                player.sendMessage(Component.text("Ei oikeutta."));
                return true;
            }
            handleTeleport(sender, Bukkit.getPlayer(args[0]));
        }
        return true;
    }

    private void handleTeleport(CommandSender sender, Player target) {
        if (target == null) {
            sender.sendMessage(Component.text("Pelaajaa ei löytynyt!"));
            return;
        }

        Location loc = config.getLocation("spawn");
        if (loc == null) {
            sender.sendMessage(Component.text("Spawnia ei ole asetettu!"));
        } else {
            target.teleport(loc);
            target.sendMessage(Component.text("Sinut teleportattiin spawnille!"));
            if (sender != target) {
                sender.sendMessage(Component.text("Teleporttasit pelaajan " + target.getName() + " spawnille!"));
            }

        }
    }

    @Override
    public List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        plugin.getLogger().info(Arrays.toString(args));
        return Collections.emptyList();
    }
}
