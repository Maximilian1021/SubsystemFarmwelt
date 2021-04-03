package me.maximilian1021.events;

import me.maximilian1021.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();


        /* Header */
        p.setPlayerListHeader("§k!i!i   §cDas Klassenzimmer - Farming   §r§k!i!i!");
        p.setPlayerListFooter("§7+++ §bNetwork hosted by §6§lMaximilian1021  §7+++");

        /* Join Message */
        e.setJoinMessage("§a+ §b " + e.getPlayer().getName());

    }


    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e) {
        if (!e.getPlayer().spigot().getRawAddress().getAddress().toString().contains(Main.getPlugin().getConfig().getString("bungee-ip"))) {
            e.getPlayer().kickPlayer(Main.getPlugin().getConfig().getString("kick-message").replace("&", "§"));
        }
    }
}