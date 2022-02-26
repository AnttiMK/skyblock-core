package net.motimaa.skyblockcore;

import net.motimaa.skyblockcore.listener.ListenerSystem;
import net.motimaa.skyblockcore.menus.InventoryManager;
import net.motimaa.skyblockcore.npcs.NPCManager;
import net.motimaa.skyblockcore.player.PlayerProvider;
import net.motimaa.skyblockcore.settings.locale.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.logging.Logger;

/**
 * Main system of the plugin, in charge of enabling all subsystems
 */
@Singleton
public class MainSystem implements SubSystem {

    private static final long ENABLE_TIMER_START = System.currentTimeMillis();

    private final SkyblockCore plugin;
    private final ListenerSystem listenerSystem;
    private final Locale locale;
    private final NPCManager npcManager;
    private final PlayerProvider playerProvider;
    private final InventoryManager inventoryManager;

    @Inject
    public MainSystem(
            SkyblockCore plugin,
            Logger logger,
            ListenerSystem listenerSystem,
            Locale locale,
            NPCManager npcManager,
            PlayerProvider playerProvider,
            InventoryManager inventoryManager
    ) {
        this.plugin = plugin;
        this.listenerSystem = listenerSystem;
        this.locale = locale;
        this.npcManager = npcManager;
        this.playerProvider = playerProvider;
        this.inventoryManager = inventoryManager;

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
                locale,
                npcManager
        );
    }

    @Override
    public void disable() {

        disableSystems(
                listenerSystem,
                locale,
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

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
