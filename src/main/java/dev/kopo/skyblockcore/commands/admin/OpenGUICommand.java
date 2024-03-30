package dev.kopo.skyblockcore.commands.admin;

import dev.kopo.skyblockcore.commands.SkyblockCommand;
import dev.kopo.skyblockcore.menus.InventoryManager;
import dev.kopo.skyblockcore.menus.InventoryType;
import dev.kopo.skyblockcore.menus.npc.banker.BankerMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.List;

public class OpenGUICommand extends SkyblockCommand {

    private final InventoryManager inventoryManager;

    @Inject
    public OpenGUICommand(InventoryManager inventoryManager) {
        super("opengui");
        this.inventoryManager = inventoryManager;
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
        // /opengui banker
        if ("banker".equalsIgnoreCase(args[0])) {
            player.openInventory(new BankerMenu());
            inventoryManager.openInventory(player, InventoryType.BANKER);
            return true;
        }

        if ("main".equalsIgnoreCase(args[0])) {
            inventoryManager.openInventory(player, InventoryType.MAIN_MENU);
            return true;
        }

        return true;
    }

    @Override
    public List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        return List.of("banker", "main");
    }
}
