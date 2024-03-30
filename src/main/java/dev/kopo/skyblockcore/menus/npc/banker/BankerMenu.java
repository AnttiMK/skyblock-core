package dev.kopo.skyblockcore.menus.npc.banker;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.menus.AbstractInventory;
import dev.kopo.skyblockcore.protocol.SignGUIFactory;
import dev.kopo.skyblockcore.providers.VaultProvider;
import dev.kopo.skyblockcore.util.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class BankerMenu extends AbstractInventory {

    private static final Component CHAT_PREFIX = Component.text("Pankki ")
            .decorate(TextDecoration.BOLD).color(NamedTextColor.YELLOW)
            .append(Component.text("» ").color(NamedTextColor.DARK_GRAY));

    private final SkyblockCore plugin;
    private final VaultProvider vault;
    private final SignGUIFactory signGUIFactory;

    public BankerMenu(SkyblockCore plugin, VaultProvider vault, SignGUIFactory signGUIFactory) {
        super(36, Component.text("Pankki", NamedTextColor.YELLOW, TextDecoration.BOLD));
        this.plugin = plugin;
        this.vault = vault;
        this.signGUIFactory = signGUIFactory;
        this.setFiller(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
    }

    @Override
    protected boolean onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        // Double clicks cause three events to fire, and this is the action type for the third one
        if (event.getAction().equals(InventoryAction.NOTHING)) return false;
        event.getWhoClicked().sendMessage(Component.text("clicked! action: " + event.getAction()));
        return true;
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        if (event.isCancelled()) return;

        if (event.getPlayer() instanceof Player p) {
            this.setItems(p);
        } else {
            event.setCancelled(true);
        }
    }

    private void setItems(Player target) {
        this.setItem(11, this.depositItem(target), event -> signGUIFactory.builder()
                .text("", "", "Kuinka paljon", "haluat tallettaa?")
                .line(1, Component.text("                  ").decorate(TextDecoration.STRIKETHROUGH))
                .reopenIfFail(false)
                .response((player, strings) -> {
                    if (!this.isDouble(strings[0])) {
                        player.sendMessage(CHAT_PREFIX.append(Component.text("Lukua ei tunnistettu! ").color(NamedTextColor.RED))
                                .append(Component.text("Vinkki: käytä desimaalierottimena pistettä!").color(NamedTextColor.GRAY)));
                        return false;
                    }
                    // TODO balancen tarkistus -> lisätään raha tilille ja poistetaan lompakosta
                    player.sendMessage(CHAT_PREFIX.append(Component.text("Lisätty tilille " + strings[0])));
                    return true;
                })
                .open(target)
        );
        this.setItem(15, this.withdrawItem(target), event -> signGUIFactory.builder()
                .text("", "", "Kuinka paljon", "haluat nostaa?")
                .line(1, Component.text("                  ").decorate(TextDecoration.STRIKETHROUGH))
                .reopenIfFail(false)
                .response((player, strings) -> {
                    if (!this.isDouble(strings[0])) {
                        player.sendMessage(CHAT_PREFIX.append(Component.text("Lukua ei tunnistettu! ").color(NamedTextColor.RED))
                                .append(Component.text("Vinkki: käytä desimaalierottimena pistettä!").color(NamedTextColor.GRAY)));
                        return false;
                    }
                    // TODO balancen tarkistus -> lisätään lompakkoon ja poistetaan tililtä
                    player.sendMessage(CHAT_PREFIX.append(Component.text("Nostettu tililtä " + strings[0])));
                    return true;
                })
                .open(target)
        );
        this.setItem(31, this.exitItem(), event -> Bukkit.getScheduler().runTaskLater(plugin, () -> target.closeInventory(), 1L));
    }

    private boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private ItemStack depositItem(Player player) {
        return new ItemBuilder(Material.CHEST)
                .name(Component.text("Talleta").color(TextColor.color(102, 255, 113))
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true))
                .lore(Component.text("Tilin saldo: ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(vault.getEconomy().getBalance(player)).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)))
                .build();
    }

    private ItemStack withdrawItem(Player player) {
        // TODO mikä item tähän?
        return new ItemBuilder(Material.CHEST)
                .name(Component.text("Nosta").color(TextColor.color(0, 149, 255))
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true))
                .lore(Component.text("Tilin saldo: ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                        .append(Component.text(vault.getEconomy().getBalance(player)).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)))
                .build();
    }

    private ItemStack exitItem() {
        return new ItemBuilder(Material.BARRIER)
                .name(Component.text("Poistu").color(TextColor.color(255, 102, 102))
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true))
                .build();
    }

}
