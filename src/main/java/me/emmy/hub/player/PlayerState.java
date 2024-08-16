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

    public static void setState(Player player, PlayerState state) {
        Forest.getInstance().getPvpModeRepository().getPlayerState().put(player, state);
    }

    public static PlayerState getState(Player player) {
        return Forest.getInstance().getPvpModeRepository().getPlayerState().get(player);
    }

    public static boolean isState(Player player, PlayerState state) {
        return getState(player) == state;
    }
}
