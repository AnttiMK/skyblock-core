package net.motimaa.skyblockcore.api;

import net.motimaa.skyblockcore.menus.AbstractInventory;
import net.motimaa.skyblockcore.menus.InventoryType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface SkyblockPlayer {
    Map<InventoryType, AbstractInventory> getInventories();

    @NotNull Player player();
}
