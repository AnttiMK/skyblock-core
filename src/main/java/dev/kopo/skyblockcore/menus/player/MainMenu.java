package dev.kopo.skyblockcore.menus.player;

import dev.kopo.skyblockcore.menus.AbstractInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MainMenu extends AbstractInventory {

    public MainMenu(Player player) {
        super(54, Component.text("Päävalikko"));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {

    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {

    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }
}
