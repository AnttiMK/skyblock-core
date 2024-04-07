package dev.kopo.skyblockcore.commands.general;

import dev.kopo.skyblockcore.commands.SkyblockCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.List;

public class HelpCommand extends SkyblockCommand {

    @Inject
    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    @Override
    public List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        return super.getTabCompletions(sender, args);
    }
}
