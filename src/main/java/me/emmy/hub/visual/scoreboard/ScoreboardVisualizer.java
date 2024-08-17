package me.emmy.hub.visual.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.player.PlayerData;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Forest
 * @date 27/03/2024 - 14:23
 */
public class ScoreboardVisualizer implements AssembleAdapter {

    Forest plugin = Forest.getInstance();

    /**
     * Get the title for the player
     *
     * @param player the player
     * @return the title
     */
    @Override
    public String getTitle(Player player) {
        return CC.translate(Forest.getInstance().getScoreboardHandler().getText()
                .replaceAll("%server-name%", Bukkit.getServerName())
        );
    }

    /**
     * Get the lines for the player
     *
     * @param player the player
     * @return the lines
     */
    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();
        PlayerState state = PlayerState.getState(player);
        if (state == PlayerState.FIGHTING) {
            for (String line : plugin.getConfigHandler().getConfig("providers/scoreboard.yml").getStringList("scoreboard.lines.pvpmode")) {
                String replacedLine = PlaceholderAPI.setPlaceholders(player, line);
                replacedLine = replacedLine.replaceAll("%sidebar%", "&7&m----------------------------")
                        .replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("%kills%", String.valueOf(PlayerData.getKills(player)))
                        .replace("%deaths%", String.valueOf(PlayerData.getDeaths(player)))
                        .replace("%max-online%", String.valueOf(Bukkit.getMaxPlayers()));
                toReturn.add(CC.translate(replacedLine));
            }
            return toReturn;
        } else {
            for (String line : plugin.getConfigHandler().getConfig("providers/scoreboard.yml").getStringList("scoreboard.lines.spawn")) {
                String replacedLine = PlaceholderAPI.setPlaceholders(player, line);
                replacedLine = replacedLine.replaceAll("%sidebar%", "&7&m----------------------------")
                        .replaceAll("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replaceAll("%max-online%", String.valueOf(Bukkit.getMaxPlayers()));
                toReturn.add(CC.translate(replacedLine));
            }
            return toReturn;
        }
    }
}