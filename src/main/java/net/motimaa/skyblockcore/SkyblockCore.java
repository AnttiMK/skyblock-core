package net.motimaa.skyblockcore;

import net.milkbowl.vault.economy.Economy;
import net.motimaa.skyblockcore.commands.CommandManager;
import net.motimaa.skyblockcore.providers.EconomyProvider;
import net.motimaa.skyblockcore.storage.database.MySQLDB;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class SkyblockCore extends JavaPlugin {

    private static SkyblockCore instance;

    private MainSystem system;
    private Logger logger;
    private PluginManager pluginManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        this.logger = getLogger();
        this.pluginManager = getServer().getPluginManager();

        SkyblockCoreComponent component = DaggerSkyblockCoreComponent.builder()
                .plugin(this)
                .logger(logger)
                .build();

        getServer().getServicesManager().register(Economy.class, new EconomyProvider(this), this, ServicePriority.Highest);

        try {
            this.system = component.system();
            this.commandManager = component.commandManager();

            system.enable();

            commandManager.registerCommands();
            new MySQLDB().initialize();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> this.getClass().getSimpleName());
        }
    }

    @Override
    public void onDisable() {
        system.disable();
    }

    public MainSystem getSystem() {
        return system;
    }

}
