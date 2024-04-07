package dev.kopo.skyblockcore.util.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullGenerator {

    private SkullGenerator() {
    }

    public static ItemStack fromTextures(String textures) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty(
                "textures",
                textures
        ));
        meta.setPlayerProfile(profile);
        skull.setItemMeta(meta);
        return skull;
    }

    public static ItemStack fromPlayerName(String playerName) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

        // TODO

        return skull;
    }
}
