package me.maximilian1021.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;



public class CMDSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        World world = Bukkit.getWorld("world");
        Player p = (Player) sender;
        Location loc = new Location(world, -33.5, 71, 255.5);

        p.teleport(loc);
        p.sendMessage("§aFarmwelt §7| §bDu wurdest zum Spawn teleportiert");
        return false;
    }
}
