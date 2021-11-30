package net.motimaa.skyblockcore.menus.npc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.motimaa.skyblockcore.menus.AbstractInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class BankerMenu extends AbstractInventory {

    private final Player player;

    public BankerMenu(Player player) {
        super(27, Component.text("Pankki", NamedTextColor.YELLOW, TextDecoration.BOLD));
        this.player = player;
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        event.getWhoClicked().sendMessage(Component.text("clicked!"));
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
    }

}
