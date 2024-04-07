package dev.kopo.skyblockcore.listener;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.SubSystem;
import dev.kopo.skyblockcore.commands.AsyncTabCompleteListener;
import dev.kopo.skyblockcore.listener.impl.building.BlockPhysicsListener;
import dev.kopo.skyblockcore.listener.impl.general.InventoryListener;
import dev.kopo.skyblockcore.listener.impl.general.MenuItemListener;
import dev.kopo.skyblockcore.listener.impl.general.PlayerLoginListener;
import dev.kopo.skyblockcore.listener.impl.npc.FarmBlockListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import javax.inject.Inject;

public class ListenerSystem implements SubSystem {

    private final SkyblockCore plugin;
    private final AsyncTabCompleteListener asyncTabCompleteListener;
    private final InventoryListener inventoryListener;
    private final PlayerLoginListener playerLoginListener;
    private final MenuItemListener menuItemListener;
    private final BlockPhysicsListener blockPhysicsListener;
    private final FarmBlockListener farmBlockListener;

    @Inject
    public ListenerSystem(
            SkyblockCore plugin,
            AsyncTabCompleteListener asyncTabCompleteListener,
            InventoryListener inventoryListener,
            PlayerLoginListener playerLoginListener,
            MenuItemListener menuItemListener,
            BlockPhysicsListener blockPhysicsListener,
            FarmBlockListener farmBlockListener
    ) {
        this.plugin = plugin;
        this.asyncTabCompleteListener = asyncTabCompleteListener;
        this.inventoryListener = inventoryListener;
        this.playerLoginListener = playerLoginListener;
        this.menuItemListener = menuItemListener;
        this.blockPhysicsListener = blockPhysicsListener;
        this.farmBlockListener = farmBlockListener;
    }

    @Override
    public void enable() {
        this.registerListeners(
                asyncTabCompleteListener,
                inventoryListener,
                playerLoginListener,
                menuItemListener,
                blockPhysicsListener,
                farmBlockListener
        );
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this.plugin);
    }

    private void registerListeners(Object... listeners) {
        for (Object listener : listeners) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener can not be null!");
            } else if (!(listener instanceof Listener list)) {
                throw new IllegalArgumentException("Listener needs to be of type " + listener.getClass().getName() + ", but was " + listener.getClass());
            } else {
                this.plugin.getServer().getPluginManager().registerEvents(list, this.plugin);
            }
        }
    }

}
