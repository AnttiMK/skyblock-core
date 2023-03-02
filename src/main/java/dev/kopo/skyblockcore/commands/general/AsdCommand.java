package dev.kopo.skyblockcore.commands.general;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class AsdCommand extends Command {

    public AsdCommand() {
        super(
                "asd",
                "This command does asd.",
                "/asd",
                Collections.singletonList("qwerty")
        );
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }
}
