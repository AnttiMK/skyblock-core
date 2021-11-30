package net.motimaa.skyblockcore.commands.general;

import net.motimaa.skyblockcore.commands.SkyblockCommand;
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
    public List<String> getTabCompletions() {
        return super.getTabCompletions();
    }
}
