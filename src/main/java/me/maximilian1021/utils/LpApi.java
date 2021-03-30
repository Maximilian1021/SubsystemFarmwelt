package me.maximilian1021.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

public class LpApi {
    private static LuckPerms api;

    private static boolean getInstance() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {
            api = provider.getProvider();
            return true;
        }
        return false;
    }



    private static User getUser(String pName) {
        if (getInstance())
            return api.getUserManager().getUser(pName);
        return null;
    }


    public static Timestamp getTimeEnd(String perm, String pName) {
        Timestamp endTime = null;

        Collection<Node> perms = getUser(pName).getNodes();

        for(Node n : perms) {
            if (n.getKey().equalsIgnoreCase(perm))
            {
                Instant t = n.getExpiry();
                endTime = Timestamp.from(t);
            }
        }

        return endTime;
    }
}