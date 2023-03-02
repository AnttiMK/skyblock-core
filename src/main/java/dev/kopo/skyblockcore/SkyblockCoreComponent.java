package dev.kopo.skyblockcore;

import dagger.BindsInstance;
import dagger.Component;
import dev.kopo.skyblockcore.commands.CommandManager;
import dev.kopo.skyblockcore.modules.ObjectProviderModule;

import javax.inject.Singleton;
import java.util.logging.Logger;

@Singleton
@Component(modules = {
        ObjectProviderModule.class
})
public interface SkyblockCoreComponent {

    CommandManager commandManager();

    MainSystem system();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder plugin(SkyblockCore plugin);

        @BindsInstance
        Builder logger(Logger logger);

        SkyblockCoreComponent build();
    }
}
