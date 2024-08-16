package me.emmy.hub.tablist.api;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */

public class TabListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Tab.getInstance().getPlayerTablist().put(player.getUniqueId(), new PlayerTablist(event.getPlayer()));
    }


    private void handleDisconnect(Player player) {
        Team team = player.getScoreboard().getTeam("\\u000181");
        if (team != null) {
            team.unregister();
        }

        Tab.getInstance().getPlayerTablist().remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleDisconnect(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        handleDisconnect(event.getPlayer());
    }


}
