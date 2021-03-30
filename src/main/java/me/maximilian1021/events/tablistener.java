package me.maximilian1021.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class tablistener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();


        if (p.hasPermission("tab.admin")) {
            e.getPlayer().setPlayerListName("§4Admin §8| §b" + p.getName());
        } else if (p.hasPermission("tab.gast")) {
            p.setPlayerListName("§7Gast §8| §b" + p.getName());
        } else if (p.hasPermission("tab.spieler")) {
            p.setPlayerListName("§6Spieler §8| §b" + p.getName());
        } else if (p.hasPermission("tab.team")) {
            p.setPlayerListName("§aTeam §8| §b" + p.getName());
        }

    }
}
