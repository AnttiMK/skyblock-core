package net.motimaa.skyblockcore.menus.npc.banker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.motimaa.skyblockcore.menus.AbstractInventory;
import net.motimaa.skyblockcore.protocol.SignGUIFactory;
import net.motimaa.skyblockcore.providers.VaultProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class BankerMenu extends AbstractInventory {

    private static final Component CHAT_PREFIX = Component.text("Pankki ")
            .decorate(TextDecoration.BOLD).color(NamedTextColor.YELLOW)
            .append(Component.text("» ").color(NamedTextColor.DARK_GRAY));

    private final VaultProvider vault;
    private final SignGUIFactory signGUIFactory;

    public BankerMenu(VaultProvider vault, SignGUIFactory signGUIFactory) {
        super(36, Component.text("Pankki", NamedTextColor.YELLOW, TextDecoration.BOLD));
        this.vault = vault;
        this.signGUIFactory = signGUIFactory;
        this.setFiller(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        // Double clicks cause three events to fire, and this is the action type for the third one
        if (event.getAction().equals(InventoryAction.NOTHING)) return;
        event.getWhoClicked().sendMessage(Component.text("clicked! action: " + event.getAction()));
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

    @Override
    protected void onClose(InventoryCloseEvent event) {
    }

    private void setItems(Player target) {
        this.setItem(11, this.depositItem(target), event -> {
            SignGUIFactory.SignGUI gui = signGUIFactory.builder(Arrays.asList("", "^^^^^^^^", "Kuinka paljon", "haluat tallettaa?"))
                    .reopenIfFail(false)
                    .response((player, strings) -> {
                        if (!this.isDouble(strings[0])) {
                            player.sendMessage(CHAT_PREFIX.append(Component.text("Lukua ei tunnistettu! ").color(NamedTextColor.RED))
                                    .append(Component.text("Vinkki: käytä desimaalierottimena pistettä!").color(NamedTextColor.GRAY)));
                            return false;
                        }
                        // lisätään tässä raha tilille ja poistetaan lompakosta
                        player.sendMessage(CHAT_PREFIX.append(Component.text("Lisätty tilille " + strings[0])));
                        return true;
                    });

            gui.open(target);
        });


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
        ItemStack deposit = new ItemStack(Material.CHEST);
        ItemMeta depositMeta = deposit.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        depositMeta.displayName(Component.text("Talleta").color(TextColor.color(102, 255, 113))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true));

        lore.add(Component.text("Tilin saldo: ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(vault.getEconomy().getBalance(player)).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)));
        depositMeta.lore(lore);
        deposit.setItemMeta(depositMeta);

        return deposit;
    }

}
