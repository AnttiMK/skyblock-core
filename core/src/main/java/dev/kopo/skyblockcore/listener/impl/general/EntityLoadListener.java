package dev.kopo.skyblockcore.listener.impl.general;

import dev.kopo.skyblockcore.mechanics.regeneration.crystals.Crystal;
import dev.kopo.skyblockcore.mechanics.regeneration.crystals.CrystalTracker;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;

import javax.inject.Inject;
import java.util.List;

public class EntityLoadListener implements Listener {

    private final CrystalTracker crystalTracker;

    @Inject
    public EntityLoadListener(CrystalTracker crystalTracker) {
        this.crystalTracker = crystalTracker;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityLoad(EntitiesLoadEvent event) {
        List<Entity> entities = event.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof ArmorStand) {
                for (Crystal crystal : crystalTracker.getCrystals()) {
                    if (crystal.getArmorStand().getUniqueId().equals(entity.getUniqueId())) {

                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityUnload(EntitiesUnloadEvent event) {
    }
}
