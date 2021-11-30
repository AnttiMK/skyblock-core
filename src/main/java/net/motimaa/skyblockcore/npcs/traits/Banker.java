package net.motimaa.skyblockcore.npcs.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.kyori.adventure.text.Component;
import net.motimaa.skyblockcore.menus.InventoryManager;
import net.motimaa.skyblockcore.menus.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@TraitName("banker")
public class Banker extends Trait {

    public Banker() {
        super("banker");
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        if (event.getNPC() != this.getNPC()) return;

        Player player = event.getClicker();
        player.sendMessage(Component.text("Opening bank GUI..."));
        InventoryManager.openInventory(player, InventoryType.BANKER);
    }
}
