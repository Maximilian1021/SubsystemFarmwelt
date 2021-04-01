package me.maximilian1021.main;

import me.maximilian1021.commands.CMDSpawn;
import me.maximilian1021.events.Events;
import me.maximilian1021.events.Join;
import me.maximilian1021.events.Tablistener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Main extends JavaPlugin {
    private static Main plugin;


    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        registerEvents();
        registerCommands();
        Main.plugin = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new Chatformat(), this);
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new Tablistener(), this);
        pm.registerEvents(new Join(), this);
    }


    private void registerCommands() {

        Objects.requireNonNull(getCommand("Spawn")).setExecutor(new CMDSpawn());
    }

    public final boolean isPlaceholderAPIEnabled() {
        return this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

}
