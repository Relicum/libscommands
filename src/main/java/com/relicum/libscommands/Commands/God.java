package com.relicum.libscommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.relicum.libscommands.LibsCommands;

public class God implements CommandExecutor {
    public String description = "Toggle invincibility";
    private LibsCommands lib;

    public God(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.god")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.YELLOW + "Can't find player");
                    return true;
                }
            }
            if (lib.god.contains(p.getUniqueId()))
            {
                lib.god.remove(p.getUniqueId());
                sender.sendMessage(ChatColor.YELLOW + p.getName() + " is no longer in god mode");
            } else {
                lib.god.add(p.getUniqueId());
                sender.sendMessage(ChatColor.YELLOW + p.getName() + " is now in god mode");
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}
