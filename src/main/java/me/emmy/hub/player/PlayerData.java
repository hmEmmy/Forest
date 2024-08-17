package me.emmy.hub.player;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * @author Emmy
 * @project Forest
 * @date 17/08/2024 - 13:06
 */
@Getter
@UtilityClass
public class PlayerData {

    private HashMap<Player, Integer> kills = new HashMap<>();
    private HashMap<Player, Integer> deaths = new HashMap<>();

    /**
     * Increment player kills by 1 and add to the map
     *
     * @param player Player
     */
    public void incrementKills(Player player) {
        kills.put(player, kills.getOrDefault(player, 0) + 1);
    }

    /**
     * Increment player deaths by 1 and add to the map
     *
     * @param player Player
     */
    public void incrementDeaths(Player player) {
        deaths.put(player, deaths.getOrDefault(player, 0) + 1);
    }

    /**
     * Get player kills count
     *
     * @param player Player
     * @return int
     */
    public int getKills(Player player) {
        return kills.getOrDefault(player, 0);
    }

    /**
     * Get player deaths count
     *
     * @param player Player
     * @return int
     */
    public int getDeaths(Player player) {
        return deaths.getOrDefault(player, 0);
    }

    /**
     * Reset player kills to 0
     *
     * @param player Player
     */
    public void reset(Player player) {
        kills.remove(player);
        deaths.remove(player);
    }
}
