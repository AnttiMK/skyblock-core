package dev.kopo.skyblockcore.commands.admin;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.commands.SkyblockCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class SetSpawnCommand extends SkyblockCommand {

    private final SkyblockCore plugin;

    @Inject
    public SetSpawnCommand(SkyblockCore plugin) {
        super("setspawn");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Tämän komennon voi suorittaa vain pelissä."));
            return true;
        }
        if (player.hasPermission("motimaa.admin")) {
            plugin.getConfig().set("spawn", player.getLocation());
            plugin.saveConfig();

            player.sendMessage(Component.text("Spawnpoint asetettu!"));
            return true;
        }
        player.sendMessage(Component.text("Ei oikeutta."));
        return true;
    }

}
