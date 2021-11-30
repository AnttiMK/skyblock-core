package net.motimaa.skyblockcore.listener;

import net.motimaa.skyblockcore.SubSystem;
import net.motimaa.skyblockcore.commands.AsyncTabCompleteListener;
import net.motimaa.skyblockcore.listener.impl.InventoryListener;
import net.motimaa.skyblockcore.listener.impl.MenuItemListener;
import net.motimaa.skyblockcore.listener.impl.PlayerLoginListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class ListenerSystem implements SubSystem {

    private final JavaPlugin plugin;
    private final AsyncTabCompleteListener asyncTabCompleteListener;
    private final InventoryListener inventoryListener;
    private final PlayerLoginListener playerLoginListener;
    private final MenuItemListener menuItemListener;

    @Inject
    public ListenerSystem(
            JavaPlugin plugin,
            AsyncTabCompleteListener asyncTabCompleteListener,
            InventoryListener inventoryListener,
            PlayerLoginListener playerLoginListener,
            MenuItemListener menuItemListener
    ) {
        this.plugin = plugin;
        this.asyncTabCompleteListener = asyncTabCompleteListener;
        this.inventoryListener = inventoryListener;
        this.playerLoginListener = playerLoginListener;
        this.menuItemListener = menuItemListener;
    }

    @Override
    public void enable() {
        this.registerListeners(
                asyncTabCompleteListener,
                inventoryListener,
                playerLoginListener,
                menuItemListener
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
            } else if (!(listener instanceof Listener)) {
                throw new IllegalArgumentException("Listener needs to be of type " + listener.getClass().getName() + ", but was " + listener.getClass());
            } else {
                this.plugin.getServer().getPluginManager().registerEvents((Listener) listener, this.plugin);
            }
        }
    }

}
