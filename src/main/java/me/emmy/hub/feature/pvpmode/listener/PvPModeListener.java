package me.emmy.hub.feature.pvpmode.listener;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 20:56
 */
public class PvPModeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (PlayerState.isState(event.getEntity(), PlayerState.FIGHTING)) {
            player.spigot().respawn();
            Forest.getInstance().getPvpModeRepository().teleportToPvPSpawn(player);
            event.setDeathMessage(CC.translate("&c&l" + player.getName() + " &7was killed" + (event.getEntity().getKiller() != null ? " by &c&l" + event.getEntity().getKiller().getName() : ".")));
            event.getDrops().clear();
            Forest.getInstance().getServer().getScheduler().runTaskLater(Forest.getInstance(), () -> {
                Forest.getInstance().getPvpModeRepository().applyItems(player);
            }, 5L);
        }
    }
}