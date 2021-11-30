package net.motimaa.skyblockcore;

import net.motimaa.skyblockcore.commands.CommandManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class SkyblockCore extends JavaPlugin {

    private static SkyblockCore instance;

    private MainSystem system;
    private Logger logger;
    private PluginManager pluginManager;
    private CommandManager commandManager;

    public static SkyblockCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.logger = getLogger();
        this.pluginManager = getServer().getPluginManager();

        SkyblockCoreComponent component = DaggerSkyblockCoreComponent.builder()
                .plugin(this)
                .logger(logger)
                .build();

        try {
            this.system = component.system();
            this.commandManager = component.commandManager();

            system.enable();

            commandManager.registerCommands();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> this.getClass().getSimpleName());
        }
    }

    @Override
    public void onDisable() {

    }

    public MainSystem getSystem() {
        return system;
    }
}
