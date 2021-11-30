package net.motimaa.skyblockcore;

import dagger.BindsInstance;
import dagger.Component;
import net.motimaa.skyblockcore.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;
import java.util.logging.Logger;

@Singleton
@Component
public interface SkyblockCoreComponent {

    CommandManager commandManager();

    MainSystem system();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder plugin(JavaPlugin plugin);

        @BindsInstance
        Builder logger(Logger logger);

        SkyblockCoreComponent build();
    }
}
