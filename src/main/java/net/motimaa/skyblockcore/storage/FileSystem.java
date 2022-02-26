package net.motimaa.skyblockcore.storage;

import net.motimaa.skyblockcore.SubSystem;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.nio.file.Path;

public class FileSystem implements SubSystem {

    private final File dataFolder;

    @Inject
    public FileSystem(@Named("dataFolder") File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public Path getDataFolderPath() {
        return dataFolder.toPath();
    }

    public File getFileFromPluginFolder(String name) {
        return new File(dataFolder, name.replace("/", File.separator));
    }

    public File getLocaleFile() {
        return getFileFromPluginFolder("locale.yml");
    }

    @Override
    public void enable() {
        // ignored
    }

    @Override
    public void disable() {
        // ignored
    }
}
