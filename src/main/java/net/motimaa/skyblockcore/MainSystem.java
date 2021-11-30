package net.motimaa.skyblockcore;

import net.motimaa.skyblockcore.listener.ListenerSystem;
import net.motimaa.skyblockcore.npcs.NPCManager;
import net.motimaa.skyblockcore.player.PlayerProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Logger;

/**
 * Main system of the plugin, in charge of enabling all subsystems
 */
@Singleton
public class MainSystem implements SubSystem {

    private static final long ENABLE_TIMER_START = System.currentTimeMillis();

    private final JavaPlugin plugin;
    private final ListenerSystem listenerSystem;
    private final NPCManager npcManager;
    private final PlayerProvider playerProvider;

    @Inject
    public MainSystem(
            JavaPlugin plugin,
            Logger logger,
            ListenerSystem listenerSystem,
            NPCManager npcManager,
            PlayerProvider playerProvider
    ) {
        this.plugin = plugin;
        this.listenerSystem = listenerSystem;
        this.npcManager = npcManager;
        this.playerProvider = playerProvider;

        logger.info("");
        logger.info("   _____ ____  ______                   _____");
        logger.info("  / ___// __ )/ ____/___  ________     |__  /");
        logger.info("  \\__ \\/ __  / /   / __ \\/ ___/ _ \\     /_ < ");
        logger.info(" ___/ / /_/ / /___/ /_/ / /  /  __/   ___/ / ");
        logger.info("/____/_____/\\____/\\____/_/   \\___/   /____/  ");
        logger.info("");
        logger.info("Author: Kopo942");
    }

    @Override
    public void enable() {

        enableSystems(
                listenerSystem,
                npcManager
        );
    }

    @Override
    public void disable() {

        disableSystems(
                listenerSystem,
                npcManager
        );
    }

    public void enableSystems(SubSystem... systems) {
        for (SubSystem subSystem : systems) {
            subSystem.enable();
        }
    }

    public void disableSystems(SubSystem... systems) {
        for (SubSystem subSystem : systems) {
            subSystem.disable();
        }
    }

    public PlayerProvider getPlayerProvider() {
        return playerProvider;
    }
}
