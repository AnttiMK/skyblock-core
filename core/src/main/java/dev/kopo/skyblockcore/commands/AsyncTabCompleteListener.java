package dev.kopo.skyblockcore.commands;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import dev.kopo.skyblockcore.SkyblockCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;
import java.util.Arrays;

public class AsyncTabCompleteListener implements Listener {

    private final SkyblockCore plugin;
    private final CommandManager commandManager;

    @Inject
    public AsyncTabCompleteListener(SkyblockCore plugin, CommandManager commandManager) {
        this.plugin = plugin;
        this.commandManager = commandManager;
    }

    @EventHandler
    public void onAsyncTabComplete(AsyncTabCompleteEvent event) {
        if (!event.isCommand()) return;
        String[] buffer = event.getBuffer().split(" ");
        String commandName = buffer[0];

        SkyblockCommand command = commandManager.forName(commandName);
        if (command != null) {
            event.setCompletions(command.getTabCompletions(event.getSender(), Arrays.copyOfRange(buffer, 1, buffer.length)));
        }
    }

}
