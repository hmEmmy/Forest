package me.emmy.hub.feature.hotbar.listener;

import me.emmy.hub.feature.hotbar.Hotbar;
import me.emmy.hub.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 20:41
 */
public class HotbarListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerState.isState(player, PlayerState.SPAWN)) {
            if (event.getItem() == null) {
                return;
            }

            Hotbar hotbarItem = Hotbar.getItem(event.getItem());
            if (hotbarItem == null) {
                return;
            }

            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            if (hotbarItem.getCommand() != null) {
                player.performCommand(hotbarItem.getCommand());
                Bukkit.getConsoleSender().sendMessage("Performing command: " + hotbarItem.getCommand());
            }

            event.setCancelled(true);
        }
    }
}