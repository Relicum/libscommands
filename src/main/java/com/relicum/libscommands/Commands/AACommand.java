package com.relicum.libscommands.Commands;

import com.relicum.libscommands.LibsCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AACommand implements CommandExecutor {
    private LibsCommands lib;

    public AACommand(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.")) {
        } else sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}
