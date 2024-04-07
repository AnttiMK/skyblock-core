package dev.kopo.skyblockcore.menus.player;

import dev.kopo.skyblockcore.menus.AbstractInventory;
import dev.kopo.skyblockcore.util.FontKey;
import dev.kopo.skyblockcore.util.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MainMenu extends AbstractInventory {

    public MainMenu() {
        super(54, Component.text("\uF901\uF001").font(FontKey.GUI).color(NamedTextColor.WHITE));
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        if (event.isCancelled()) return;

        if (event.getPlayer() instanceof Player p) {
            this.setItems(p);
        } else {
            event.setCancelled(true);
        }
    }

    private void setItems(Player player) {
        this.setItem(0, new ItemBuilder(Material.PLAYER_HEAD).skullOwner(player).modelData(69).build());
    }
}
