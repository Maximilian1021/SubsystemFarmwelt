package me.maximilian1021.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();


        /* Header */
        p.setPlayerListHeader("§k!i!i   §cDas Klassenzimmer - Farming   §r§k!i!i!");
        p.setPlayerListFooter("§7+++ §bNetwork hosted by §6§lMaximilian1021  §7+++");

        /* Join Message */
        e.setJoinMessage("§a+ §b " + e.getPlayer().getName());

    }
}
