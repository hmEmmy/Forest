package me.emmy.hub.feature.pvpmode.listener;

import me.emmy.hub.Forest;
import me.emmy.hub.feature.pvpmode.PvPModeRepository;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.player.PlayerData;
import me.emmy.hub.util.CC;
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
        Player killer = event.getEntity().getKiller();
        if (PlayerState.isState(event.getEntity(), PlayerState.FIGHTING)) {
            player.spigot().respawn();

            PvPModeRepository pvpModeRepository = Forest.getInstance().getPvpModeRepository();
            pvpModeRepository.teleportToPvPSpawn(player);

            event.setDeathMessage(null);
            event.getDrops().clear();

            pvpModeRepository.broadcastPlayers(CC.translate("&c&l" + player.getName() + " &7was killed" + (event.getEntity().getKiller() != null ? " by &c&l" + event.getEntity().getKiller().getName() : ".")));

            if (killer != null) {
                PlayerData.incrementKills(killer);
            }

            if (killer != player) {
                PlayerData.incrementDeaths(player);
            }

            Forest.getInstance().getServer().getScheduler().runTaskLater(Forest.getInstance(), () -> Forest.getInstance().getPvpModeRepository().applyItems(player), 5L);
        }
    }
}