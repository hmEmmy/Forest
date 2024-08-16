package me.emmy.hub.tablist.api.nms;

import me.emmy.hub.tablist.api.PlayerTablist;
import me.emmy.hub.tablist.api.TabColumn;
import me.emmy.hub.tablist.api.TabEntry;
import me.emmy.hub.tablist.api.skin.Skin;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */
public interface TabNMS {

    TabEntry createEntry(
            PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot
    );

    void updateFakeName(
            PlayerTablist playerTablist, TabEntry tabEntry, String text
    );

    void updateLatency(
            PlayerTablist playerTablist, TabEntry tabEntry, Integer latency
    );

    void updateSkin(
            PlayerTablist playerTablist, TabEntry tabEntry, Skin skin
    );

    void updateHeaderAndFooter(
            Player player, List<String> header, List<String> footer
    );

    Skin getSkin(
            Player player);

}