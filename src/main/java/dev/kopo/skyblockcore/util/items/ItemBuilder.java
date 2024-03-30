package dev.kopo.skyblockcore.util.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class ItemBuilder {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final ItemStack item;

    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
    }

    public ItemStack build() {
        return item;
    }

    /**
     * @param name The name of the item, supports MiniMessage
     */
    public ItemBuilder name(final String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(miniMessage.deserialize(name).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder name(final Component name) {
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(final String text) {
        return lore(miniMessage.deserialize(text));
    }

    public ItemBuilder lore(final Component text) {
        return lore(List.of(text.decoration(TextDecoration.ITALIC, false)));
    }

    public ItemBuilder lore(final String... text) {
        return lore(Arrays.stream(text).map(miniMessage::deserialize).toList());
    }

    public ItemBuilder lore(final List<Component> text) {
        final ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.addAll(text);
        meta.lore(lore.stream().map(component -> component.decoration(TextDecoration.ITALIC, false)).toList());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFlags(boolean value, ItemFlag... flags) {
        final ItemMeta meta = item.getItemMeta();
        if (value) {
            meta.addItemFlags(flags);
        } else {
            meta.removeItemFlags(flags);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideAttributes(boolean value) {
        final ItemMeta meta = item.getItemMeta();
        if (value) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        } else {
            meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder unbreakable(boolean value) {
        final ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(value);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder glowing(boolean value) {
        if (value) {
            enchant(Enchantment.MENDING, 1, true);
        } else {
            item.removeEnchantment(Enchantment.MENDING);
            item.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder enchant(final Enchantment enchantment) {
        return enchant(enchantment, 1, false);
    }

    public ItemBuilder enchant(final Enchantment enchantment, final int level) {
        return enchant(enchantment, level, false);
    }

    public ItemBuilder enchant(final Enchantment enchantment, boolean hidden) {
        return enchant(enchantment, 1, hidden);
    }

    public ItemBuilder enchant(final Enchantment enchantment, final int level, final boolean hidden) {
        final ItemMeta meta = item.getItemMeta();
        item.addUnsafeEnchantment(enchantment, level);
        if (hidden) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder meta(Function<ItemMeta, ItemMeta> itemMeta) {
        item.setItemMeta(itemMeta.apply(item.getItemMeta()));
        return this;
    }

    public ItemBuilder skullOwner(Player player) {
        if (item.getType() != Material.PLAYER_HEAD && item.getType() != Material.PLAYER_WALL_HEAD) {
            throw new IllegalArgumentException("Item must be a skull");
        }
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder skullOwner(PlayerProfile playerProfile) {
        if (item.getType() != Material.PLAYER_HEAD && item.getType() != Material.PLAYER_WALL_HEAD) {
            throw new IllegalArgumentException("Item must be a skull");
        }
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(playerProfile);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder modelData(int modelData) {
        final ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
        return this;
    }

}
