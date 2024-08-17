package me.emmy.hub.player;

import lombok.Getter;
import me.emmy.hub.Forest;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 20:02
 */
@Getter
public enum PlayerState {
    SPAWN,
    FIGHTING

    ;

    /**
     * Set player state with Player and PlayerState object
     *
     * @param player Player
     * @param state  PlayerState
     */
    public static void setState(Player player, PlayerState state) {
        Forest.getInstance().getPvpModeRepository().getPlayerState().put(player, state);
    }

    /**
     * Get player state with player object
     *
     * @param player Player
     * @return PlayerState
     */
    public static PlayerState getState(Player player) {
        return Forest.getInstance().getPvpModeRepository().getPlayerState().get(player);
    }

    /**
     * Check if player is in a specific state
     *
     * @param player Player
     * @param state  PlayerState
     * @return boolean
     */
    public static boolean isState(Player player, PlayerState state) {
        return getState(player) == state;
    }
}
