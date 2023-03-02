package dev.kopo.skyblockcore.modules;

import dagger.Module;
import dagger.Provides;
import dev.kopo.skyblockcore.SkyblockCore;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;

@Module
public class ObjectProviderModule {

    @Provides
    @Singleton
    @Named("dataFolder")
    File provideDataFolder(SkyblockCore plugin) {
        return plugin.getDataFolder();
    }

}
