package dev.kopo.skyblockcore.npcs.traits;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.api.inventory.InventoryType;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@TraitName("banker")
public class Banker extends Trait {

    private final SkyblockCore plugin;

    public Banker(SkyblockCore plugin) {
        super("banker");
        this.plugin = plugin;
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        if (event.getNPC() != this.getNPC()) return;

        Player player = event.getClicker();
        player.sendMessage(Component.text("Opening bank GUI..."));
        plugin.getSystem().getInventoryManager().openInventory(player, InventoryType.BANKER);
    }
}
