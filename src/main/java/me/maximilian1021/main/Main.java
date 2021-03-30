package me.maximilian1021.main;

import me.maximilian1021.events.events;
import me.maximilian1021.events.tablistener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {
    private static Main plugin;


    public static Main getPlugin() {
        return plugin;
    }


    public final boolean isPlaceholderAPIEnabled() {
        return this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    @Override
    public void onEnable() {

        registerEvents();
        Main.plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void registerEvents(){
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new Chatformat(), this);
        pm.registerEvents(new events(), this);
        pm.registerEvents(new tablistener(), this);

    }
}
