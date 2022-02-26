package net.motimaa.skyblockcore.listener.impl.general;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.motimaa.skyblockcore.menus.InventoryManager;
import net.motimaa.skyblockcore.menus.InventoryType;
import net.motimaa.skyblockcore.player.PlayerProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import java.util.ArrayList;

public class MenuItemListener implements Listener {

    private final ItemStack menuItem;
    private final PlayerProvider playerProvider;
    private final InventoryManager inventoryManager;

    @Inject
    public MenuItemListener(
            PlayerProvider playerProvider,
            InventoryManager inventoryManager
    ) {
        this.playerProvider = playerProvider;
        this.inventoryManager = inventoryManager;
        this.menuItem = item();
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        PlayerInventory inventory = event.getPlayer().getInventory();
        inventory.setItem(8, menuItem);
    }

    @EventHandler
    public void onItemRightClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (menuItem.isSimilar(mainHand)) {
            inventoryManager.openInventory(player, InventoryType.MAIN_MENU);
        }
    }

    @EventHandler
    public void onItemInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (menuItem.isSimilar(event.getCurrentItem())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (menuItem.isSimilar(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().forEach(item -> {
            if (menuItem.isSimilar(item)) event.getEntity().getInventory().remove(item);
        });
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().setItem(8, menuItem);
    }

    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event) {
        if (menuItem.isSimilar(event.getItem())) event.setCancelled(true);
    }

    private ItemStack item() {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        meta.displayName(Component.text("Päävalikko").color(TextColor.color(102, 255, 113)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
        lore.add(Component.text("Tarkista edistymisesi Skyblockin päävalikosta!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Klikkaa avataksesi!").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.YELLOW));
        meta.lore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
