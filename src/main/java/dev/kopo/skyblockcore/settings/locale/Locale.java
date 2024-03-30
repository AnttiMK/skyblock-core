package dev.kopo.skyblockcore.settings.locale;

import dev.kopo.skyblockcore.SkyblockCore;
import dev.kopo.skyblockcore.SubSystem;
import dev.kopo.skyblockcore.storage.FileSystem;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.logging.Level;

@Singleton
public class Locale implements SubSystem {

    private final SkyblockCore plugin;
    private final YamlConfigurationLoader loader;
    private CommentedConfigurationNode rootNode;

    @Inject
    public Locale(
            SkyblockCore plugin,
            FileSystem fileSystem
    ) {
        this.plugin = plugin;
        this.loader = YamlConfigurationLoader.builder()
                .path(fileSystem.getLocaleFile().toPath()).build();
    }

    public String getString(Lang key) {
        Object[] path = key.getKey().split("\\.");
        ConfigurationNode loaded = rootNode.node(path);
        return loaded.getString(key.getDefault());
    }

    @Override
    public void enable() {
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load the locale!", e);
        }
    }

    @Override
    public void disable() {

    }
}
