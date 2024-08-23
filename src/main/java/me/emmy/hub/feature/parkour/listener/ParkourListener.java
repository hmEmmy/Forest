package me.emmy.hub.feature.parkour.listener;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 21/08/2024 - 19:23
 */
public class ParkourListener implements Listener {

    @EventHandler
    private void onPressurePlate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.PHYSICAL) {
            if (PlayerState.getState(player) != PlayerState.SPAWN) return;

            if (!event.getClickedBlock().getType().toString().contains("PRESSURE_PLATE")) return;

            if (!player.getLocation().equals(Forest.getInstance().getParkourHandler().getStartLocation())) return;

            //TODO: Make this start with an hotbar item instead of a pressure plate

            Forest.getInstance().getParkourHandler().startParkour(player);
        }
    }
}
