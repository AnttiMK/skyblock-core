package dev.kopo.skyblockcore.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import dev.kopo.skyblockcore.SkyblockCore;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.function.BiPredicate;

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

    public SignGUI builder() {
        return new SignGUI();
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

        private final List<Component> text;
        private boolean reopenIfFail;
        private BiPredicate<Player, String[]> response;
        private boolean forceClose;
        private Location location;

        private SignGUI() {
            this.text = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                this.text.add(Component.empty());
            }
        }

        public SignGUI text(String... text) {
            if (text.length > 4) {
                throw new IllegalArgumentException("Text must be 4 lines or less!");
            }

            this.text.clear();
            this.text.addAll(Arrays.stream(text).map(Component::text).toList());
            return this;
        }

        public SignGUI text(List<Component> text) {
            if (text.size() > 4) {
                throw new IllegalArgumentException("Text must be 4 lines or less!");
            }

            this.text.clear();
            this.text.addAll(text);
            return this;
        }

        public SignGUI line(int line, String text) {
            if (line < 0 || line > 3) {
                throw new IllegalArgumentException("Line must be between 0 and 3!");
            }

            this.text.set(line, Component.text(text));
            return this;
        }

        public SignGUI line(int line, Component text) {
            if (line < 0 || line > 3) {
                throw new IllegalArgumentException("Line must be between 0 and 3!");
            }

            this.text.set(line, text);
            return this;
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
            player.sendSignChange(location, text);

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
