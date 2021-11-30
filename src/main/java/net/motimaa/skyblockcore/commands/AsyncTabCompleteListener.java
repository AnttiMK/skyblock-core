package net.motimaa.skyblockcore.commands;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class AsyncTabCompleteListener implements Listener {

    private final JavaPlugin plugin;
    private final CommandManager commandManager;

    @Inject
    public AsyncTabCompleteListener(JavaPlugin plugin, CommandManager commandManager) {
        this.plugin = plugin;
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onAsyncTabComplete(AsyncTabCompleteEvent event) {
        if (!event.isCommand()) return;
        String commandName = event.getBuffer().split(" ")[0];

        SkyblockCommand command = commandManager.forName(commandName);
        if (command != null) {
            event.setCompletions(command.getTabCompletions());
        }
    }

}
