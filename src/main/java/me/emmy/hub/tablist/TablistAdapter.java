package me.emmy.hub.tablist;

import me.emmy.hub.tablist.api.TabColumn;
import me.emmy.hub.tablist.api.TabLayout;
import me.emmy.hub.tablist.api.TabProvider;
import me.emmy.hub.tablist.api.skin.Skin;
import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.TablistUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Forest
 */
public class TablistAdapter implements TabProvider {
    private final Forest plugin = Forest.getInstance();

    @Override
    public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layoutSet = new HashSet<>();
        String tablistType = plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("TABLIST.TYPE");
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

                    String prefix = plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("TABLIST.PLAYER_PREFIX");

                    layoutSet.add(new TabLayout(TabColumn.getColumn(column++), row)
                            .setText(prefix + online.getName())
                            .setSkin(Skin.getSkin(online))
                            .setPing(TablistUtil.getPing(online)));

                    if (column == 4) {
                        column = 0;
                        row++;
                    }
                }
            } else if (tablistType.equals("CUSTOM")) {
                layoutSet.add(new TabLayout(TabColumn.LEFT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("LEFT", i, "TEXT"))))
                        .setSkin(getSkin(player, getLines("LEFT", i, "HEAD")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.MIDDLE, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("MIDDLE", i, "TEXT"))))
                        .setSkin(getSkin(player, getLines("MIDDLE", i, "HEAD")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.RIGHT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("RIGHT", i, "TEXT"))))
                        .setSkin(getSkin(player, getLines("RIGHT", i, "HEAD")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("TABLIST.PING")));
                layoutSet.add(new TabLayout(TabColumn.FAR_RIGHT, i)
                        .setText(CC.translate(applyPlaceholders(player, getLines("FAR_RIGHT", i, "TEXT"))))
                        .setSkin(getSkin(player, getLines("FAR_RIGHT", i, "HEAD")))
                        .setPing(plugin.getConfigHandler().getConfig("providers/tablist.yml").getInt("TABLIST.PING")));
            }
        }

        return layoutSet;
    }

    @Override
    public List<String> getFooter(Player player) {
        return headerFooterList(plugin.getConfigHandler().getConfig("providers/tablist.yml").getStringList("TABLIST.FOOTER"), player);
    }

    @Override
    public List<String> getHeader(Player player) {
        List<String> headerList = plugin.getConfigHandler().getConfig("providers/tablist.yml").getStringList("TABLIST.HEADER");

        headerList.replaceAll(line -> CC.translate(line).replace("%version%", plugin.getDescription().getVersion()));

        return headerFooterList(headerList, player);
    }

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

    private List<String> headerFooterList(List<String> path, Player player) {
        List<String> list = new ArrayList<>();

        for (String str : path) {
            list.add(CC.translate(str));
        }
        return list;
    }

    private String getLines(String column, int position, String textOrHead) {
        return plugin.getConfigHandler().getConfig("providers/tablist.yml").getString("TABLIST.LINES." + column + "." + position + "." + textOrHead);
    }

    private String applyPlaceholders(Player player, String line) {
        return PlaceholderAPI.setPlaceholders(player, CC.translate(line));
    }

}
