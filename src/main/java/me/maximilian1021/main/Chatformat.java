package me.maximilian1021.main;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chatformat implements Listener {

    private String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(final String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = hexPattern.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    private String getPrefix(final Player player) {
        final String prefix = playerMeta(player).getPrefix();

        return prefix != null ? prefix : "";
    }

    private String getSuffix(final Player player) {
        final String suffix = playerMeta(player).getSuffix();

        return suffix != null ? suffix : "";
    }

    private String getPrefixes(final Player player) {
        final SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        final StringBuilder prefixes = new StringBuilder();

        for (final String prefix : map.values())
            prefixes.append(prefix);

        return prefixes.toString();
    }

    private String getSuffixes(final Player player) {
        final SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        final StringBuilder suffixes = new StringBuilder();

        for (final String prefix : map.values())
            suffixes.append(prefix);

        return suffixes.toString();
    }

    private String getColorPerGroup(final String group, final String key) {
        final String colorPerGroup = groupMeta(group).getMetaValue(key);

        return colorPerGroup != null ? colorPerGroup : "&r";
    }

    private CachedMetaData playerMeta(final Player player) {
        return loadUser(player).getCachedData().getMetaData(getApi().getContextManager().getQueryOptions(player));
    }

    private CachedMetaData groupMeta(final String group) {
        return loadGroup(group).getCachedData().getMetaData(getApi().getContextManager().getStaticQueryOptions());
    }

    private User loadUser(final Player player) {
        if (!player.isOnline())
            throw new IllegalStateException("Player is offline!");

        return getApi().getUserManager().getUser(player.getUniqueId());
    }

    private Group loadGroup(final String group) {
        return getApi().getGroupManager().getGroup(group);
    }

    private LuckPerms getApi() {
        final RegisteredServiceProvider<LuckPerms> provider = Main.getPlugin().getServer().getServicesManager().getRegistration(LuckPerms.class);
        Validate.notNull(provider);
        return provider.getProvider();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String group = loadUser(player).getPrimaryGroup();
        final String message = event.getMessage();

        String format = Objects.requireNonNull(getConfig().getString(getConfig().getString("group-formats." + group) != null ? "group-formats." + group : "chat-format"))
                .replace("{world}", player.getWorld().getName())
                .replace("{prefix}", getPrefix(player))
                .replace("{prefixes}", getPrefixes(player))
                .replace("{username}", player.getName())
                .replace("{display_name}", player.getDisplayName())
                .replace("{suffix}", getSuffix(player))
                .replace("{suffixes}", getSuffixes(player))
                .replace("{username-color}", getColorPerGroup(group, "username-color"))
                .replace("{message-color}", getColorPerGroup(group, "message-color"));

        format = translateHexColorCodes(colorize(Main.getPlugin().isPlaceholderAPIEnabled() ? PlaceholderAPI.setPlaceholders(player, format) : format));

        event.setFormat(format.replace("{message}", player.hasPermission("lpc.colorcodes") && player.hasPermission("lpc.rgbcodes")
                ? translateHexColorCodes(colorize(message)) : player.hasPermission("lpc.colorcodes") ? colorize(message) : player.hasPermission("lpc.rgbcodes")
                ? translateHexColorCodes(message) : message).replace("%", "%%"));
    }

    private FileConfiguration getConfig() {
        return Main.getPlugin().getConfig();
    }



}
