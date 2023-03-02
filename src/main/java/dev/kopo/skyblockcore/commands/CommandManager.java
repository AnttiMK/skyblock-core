package dev.kopo.skyblockcore.commands;

import dev.kopo.skyblockcore.commands.admin.CrystalCommand;
import dev.kopo.skyblockcore.commands.admin.SetSpawnCommand;
import dev.kopo.skyblockcore.commands.general.HelpCommand;
import dev.kopo.skyblockcore.commands.general.SpawnCommand;
import dev.kopo.skyblockcore.SkyblockCore;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommandManager {

    private final SkyblockCore plugin;
    private final Logger logger;
    private final HashMap<String, SkyblockCommand> commands = new HashMap<>();

    @Inject
    public CommandManager(
            SkyblockCore plugin,
            Logger logger,

            HelpCommand helpCommand,
            SpawnCommand spawnCommand,

            SetSpawnCommand setSpawnCommand,
            CrystalCommand crystalCommand
    ) {
        this.plugin = plugin;
        this.logger = logger;

        this.register(
                helpCommand,
                spawnCommand,
                setSpawnCommand,
                crystalCommand
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
