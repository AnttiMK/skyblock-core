package net.motimaa.skyblockcore.npcs;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitFactory;
import net.citizensnpcs.api.trait.TraitInfo;
import net.motimaa.skyblockcore.SkyblockCore;
import net.motimaa.skyblockcore.SubSystem;
import net.motimaa.skyblockcore.npcs.traits.Banker;
import net.motimaa.skyblockcore.npcs.traits.BaseTrait;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class NPCManager implements SubSystem {

    private final SkyblockCore plugin;
    private final Logger logger;
    private final TraitFactory traitFactory;
    private final List<TraitInfo> registeredTraits;

    @Inject
    public NPCManager(
            SkyblockCore plugin,
            Logger logger
    ) {
        this.plugin = plugin;
        this.logger = logger;
        this.traitFactory = CitizensAPI.getTraitFactory();
        this.registeredTraits = new ArrayList<>();
    }

    @Override
    public void enable() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("Citizens")) {
            this.logger.severe("Citizens not enabled, shutting down!");
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }

        logger.info("registering traits");
        registeredTraits.add(TraitInfo.create(BaseTrait.class).withSupplier((() -> new Banker(plugin))).withName("banker"));

        registeredTraits.forEach(traitFactory::registerTrait);
    }

    @Override
    public void disable() {
        plugin.getLogger().info("Unregistering traits...");
        registeredTraits.forEach(traitFactory::deregisterTrait);
    }
}
