package net.motimaa.skyblockcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class SkyblockCommand extends Command {

    protected SkyblockCommand(@NotNull String name) {
        super(name);
    }

    protected SkyblockCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public List<String> getTabCompletions(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
