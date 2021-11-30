package net.motimaa.skyblockcore.commands;

import net.motimaa.skyblockcore.commands.general.HelpCommand;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommandManager {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final HashMap<String, SkyblockCommand> commands = new HashMap<>();

    @Inject
    public CommandManager(
            JavaPlugin plugin,
            Logger logger,

            HelpCommand helpCommand
    ) {
        this.plugin = plugin;
        this.logger = logger;

        this.register(
                helpCommand
        );
    }

    public HashMap<String, SkyblockCommand> getCommands() {
        return commands;
    }

    public SkyblockCommand forName(String name) {
        return commands.get(name);
    }

    public void registerCommands() {
        this.commands.forEach((s, command) -> plugin.getServer().getCommandMap().register(plugin.getName(), command));
    }

    private void register(SkyblockCommand... commands) {
        for (SkyblockCommand command : commands) {
            this.commands.put(command.getName(), command);
        }
    }
}
