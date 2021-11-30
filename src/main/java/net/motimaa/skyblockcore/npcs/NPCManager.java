package net.motimaa.skyblockcore.npcs;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitFactory;
import net.citizensnpcs.api.trait.TraitInfo;
import net.motimaa.skyblockcore.SubSystem;
import net.motimaa.skyblockcore.npcs.traits.Banker;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.logging.Logger;

public class NPCManager implements SubSystem {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final TraitFactory traitFactory;

    @Inject
    public NPCManager(
            JavaPlugin plugin,
            Logger logger
    ) {
        this.plugin = plugin;
        this.logger = logger;
        this.traitFactory = CitizensAPI.getTraitFactory();
    }

    @Override
    public void enable() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("Citizens")) {
            this.logger.severe("Citizens not enabled, shutting down!");
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }

        logger.info("registering traits");
        traitFactory.registerTrait(TraitInfo.create(Banker.class));
    }

    @Override
    public void disable() {

    }
}
