package dev.kopo.skyblockcore.api;

import dev.kopo.skyblockcore.menus.AbstractInventory;
import dev.kopo.skyblockcore.menus.InventoryType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface SkyblockPlayer {
    Map<InventoryType, AbstractInventory> getInventories();

    @NotNull Player player();
}
