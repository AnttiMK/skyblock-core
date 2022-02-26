package net.motimaa.skyblockcore.npcs.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.kyori.adventure.text.Component;
import net.motimaa.skyblockcore.SkyblockCore;
import net.motimaa.skyblockcore.menus.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Banker extends BaseTrait {

    private final SkyblockCore plugin;

    public Banker(SkyblockCore plugin) {
        super();
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
