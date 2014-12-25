package com.relicum.libscommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Hub implements CommandExecutor {
    public String[] aliases = new String[]{"lobby"};
    public String commandToRun = "server lobby";
    public String description = "Sends you back to the hub";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Bukkit.dispatchCommand(sender, commandToRun);
        return true;
    }
}
