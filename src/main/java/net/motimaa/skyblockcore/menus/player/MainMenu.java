package net.motimaa.skyblockcore.menus.player;

import net.kyori.adventure.text.Component;
import net.motimaa.skyblockcore.menus.AbstractInventory;
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
