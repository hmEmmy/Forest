package me.emmy.hub.visual.tablist;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.hub.Forest;
import me.emmy.hub.api.tab.TabColumn;
import me.emmy.hub.api.tab.TabLayout;
import me.emmy.hub.api.tab.TabProvider;
import me.emmy.hub.api.tab.skin.Skin;
import me.emmy.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Forest
 */
public class TabVisualizer implements TabProvider {
    private final Forest plugin = Forest.getInstance();

    @Override
    public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layoutSet = new HashSet<>();
        String tablistType = plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("tablist.type");
        List<UUID> sorted = Bukkit.getOnlinePlayers().stream().map(
                        Player::getUniqueId)
                .collect(Collectors.toList());

        for (int i = 1; i <= 20; i++) {
            if (tablistType.equals("VANILLA")) {
                int playerSize = 0;
                int column = 0;
                int row = 1;

                for (UUID uuid : sorted) {
                    Player online = Bukkit.getPlayer(uuid);
                    playerSize++;
                    if (playerSize >= 60) break;

                    String prefix = plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("tablist.player_prefix");

                    layoutSet.add(new TabLayout(TabColumn.getColumn(column++), row)
                            .setText(prefix + online.getName())
                            .setSkin(Skin.getSkin(online))
                            .setPing(getPing(online)));

                    if (column == 4) {
                        column = 0;
                        row++;
                    }
                }
            } else if (tablistType.equals("CUSTOM")) {
                layoutSet.add(new TabLayout(TabColumn.LEFT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("left", i, "text"))))
                        .setSkin(getSkin(player, getLines("left", i, "head")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("tablist.ping")));
                layoutSet.add(new TabLayout(TabColumn.MIDDLE, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("middle", i, "text"))))
                        .setSkin(getSkin(player, getLines("middle", i, "head")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("tablist.ping")));
                layoutSet.add(new TabLayout(TabColumn.RIGHT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("right", i, "text"))))
                        .setSkin(getSkin(player, getLines("right", i, "head")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("tablist.ping")));
                layoutSet.add(new TabLayout(TabColumn.FAR_RIGHT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("far_right", i, "text"))))
                        .setSkin(getSkin(player, getLines("far_right", i, "head")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("tablist.ping")));
            }
        }

        return layoutSet;
    }

    @Override
    public List<String> getFooter(Player player) {
        return headerFooterList(plugin.getConfigHandler().getConfig("providers/tablist.yml").getStringList("tablist.footer"));
    }

    @Override
    public List<String> getHeader(Player player) {
        List<String> headerList = plugin.getConfigHandler().getConfig("providers/tablist.yml").getStringList("tablist.header");

        headerList.replaceAll(line -> CC.translate(line).replace("%version%", plugin.getDescription().getVersion()));

        return headerFooterList(headerList);
    }

    /**
     * Get the skin for the player.
     *
     * @param player  the player
     * @param skinTab the skin tab
     * @return the skin
     */
    public Skin getSkin(Player player, String skinTab) {
        Skin skinDefault = Skin.DEFAULT;

        if (skinTab.contains("%PLAYER%")) {
            skinDefault = Skin.getSkin(player);
        }
        if (skinTab.contains("%DISCORD%")) {
            skinDefault = Skin.DISCORD_SKIN;
        }
        if (skinTab.contains("%YOUTUBE%")) {
            skinDefault = Skin.YOUTUBE_SKIN;
        }
        if (skinTab.contains("%TWITTER%")) {
            skinDefault = Skin.TWITTER_SKIN;
        }
        if (skinTab.contains("%FACEBOOK%")) {
            skinDefault = Skin.FACEBOOK_SKIN;
        }
        if (skinTab.contains("%STORE%")) {
            skinDefault = Skin.STORE_SKIN;
        }
        return skinDefault;
    }

    /**
     * Header footer array list.
     *
     * @param path the path
     * @return the list
     */
    private List<String> headerFooterList(List<String> path) {
        List<String> list = new ArrayList<>();

        for (String str : path) {
            list.add(CC.translate(str));
        }
        return list;
    }

    /**
     * Get the lines for the tablist.
     *
     * @param column      the column
     * @param position    the position
     * @param textOrHead  the text or head
     * @return the lines
     */
    private String getLines(String column, int position, String textOrHead) {
        return plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("tablist.lines." + column + "." + position + "." + textOrHead);
    }

    /**
     * Apply placeholders to a line.
     *
     * @param player the player
     * @param line   the line
     * @return the line with placeholders
     */
    private String applyPlaceholders(Player player, String line) {
        return PlaceholderAPI.setPlaceholders(player, CC.translate(line));
    }

    /**
     * Get the ping of a player.
     *
     * @param player the player
     * @return the ping
     */
    private int getPing(Player player) {
        try {
            String a = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> b = Class.forName("org.bukkit.craftbukkit." + a + ".entity.CraftPlayer");
            Object c = b.getMethod("getHandle").invoke(player);
            return (int) c.getClass().getDeclaredField("ping").get(c);
        }
        catch (Exception ex) {
            return 0;
        }
    }
}
