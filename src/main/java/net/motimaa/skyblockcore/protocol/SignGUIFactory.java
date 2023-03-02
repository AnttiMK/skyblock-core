package net.motimaa.skyblockcore.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import net.kyori.adventure.text.Component;
import net.motimaa.skyblockcore.SkyblockCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Singleton
public final class SignGUIFactory {

    private final SkyblockCore plugin;
    private final Map<Player, SignGUI> openGUIs;
    private final ProtocolManager protocolManager;

    @Inject
    public SignGUIFactory(SkyblockCore plugin) {
        this.plugin = plugin;
        this.openGUIs = new HashMap<>();
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        this.registerListeners();
    }

    public SignGUI builder(List<String> text) {
        return new SignGUI(text);
    }

    private void registerListeners() {
        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                SignGUI signGUI = openGUIs.remove(player);

                if (signGUI == null) return;
                event.setCancelled(true);

                boolean success = signGUI.response.test(player, event.getPacket().getStringArrays().read(0));
                if (!success && signGUI.reopenIfFail && !signGUI.forceClose) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> signGUI.open(player), 2L);
                }

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (player.isOnline()) {
                        player.sendBlockChange(signGUI.location, signGUI.location.getBlock().getBlockData());
                    }
                }, 2L);
            }
        });


    }

    public final class SignGUI {

        private final List<String> text;
        private boolean reopenIfFail;
        private BiPredicate<Player, String[]> response;
        private boolean forceClose;
        private Location location;

        public SignGUI(List<String> text) {
            this.text = text;
        }

        public SignGUI reopenIfFail(boolean value) {
            this.reopenIfFail = value;
            return this;
        }

        public SignGUI response(BiPredicate<Player, String[]> response) {
            this.response = response;
            return this;
        }

        public void open(Player player) {
            Objects.requireNonNull(player, "Player must not be null!");
            if (!player.isOnline()) return;

            this.location = player.getLocation();
            this.location.setY(location.getBlockY() - 4);

            player.sendBlockChange(this.location, Material.OAK_SIGN.createBlockData());
            player.sendSignChange(location, text.stream().map(Component::text).collect(Collectors.toList()));

            PacketContainer openSign = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            openSign.getBlockPositionModifier().write(0, position);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, openSign);
            openGUIs.put(player, this);
        }

        public void close(Player player, boolean force) {
            this.forceClose = force;
            if (player.isOnline()) {
                player.closeInventory();
            }
        }

        public void close(Player player) {
            close(player, false);
        }

        private String color(String input) {
            return ChatColor.translateAlternateColorCodes('&', input);
        }
    }
}
