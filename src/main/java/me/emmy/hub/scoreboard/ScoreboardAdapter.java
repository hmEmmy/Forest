package me.emmy.hub.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardAdapter implements AssembleAdapter {

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
        for (String line : plugin.getConfigHandler().getConfig("providers/scoreboard.yml").getStringList("scoreboard.lines")) {
            String replacedLine = PlaceholderAPI.setPlaceholders(player, line);
            replacedLine = replacedLine.replaceAll("%sidebar%", "&7&m----------------------------")
                    .replaceAll("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replaceAll("%max-online%", String.valueOf(Bukkit.getMaxPlayers()));
            toReturn.add(CC.translate(replacedLine));
        }
        return toReturn;
    }
}