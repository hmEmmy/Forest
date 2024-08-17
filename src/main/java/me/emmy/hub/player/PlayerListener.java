package me.emmy.hub.player;

import me.emmy.hub.Forest;
import me.emmy.hub.feature.hotbar.Hotbar;
import me.emmy.hub.util.CC;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class PlayerListener implements Listener {

	private final Forest plugin = Forest.getInstance();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().setArmorContents(null);

		PlayerState.setState(player, PlayerState.SPAWN);

		Hotbar.applyHotbarItems(player);

		int slot;

		try {
			slot = Integer.parseInt(plugin.getConfig("listeners.yml").getString("players.on-join_selected-slot"));
		} catch (NumberFormatException e) {
			slot = 0;
		}

		player.getInventory().setHeldItemSlot(slot);

		if (plugin.getConfig("listeners.yml").getBoolean("double_jump.enabled", true)) {
			player.setAllowFlight(true);
		}

		if (plugin.getConfig("listeners.yml").getBoolean("welcome_message.enabled", true)) {
			for (String message : plugin.getConfig("listeners.yml").getStringList("welcome_message.message"))
				player.sendMessage(CC.translate(message));
		}

		Location spawn = plugin.getSpawnHandler().getLocation();
		if (spawn == null) {
			Bukkit.getConsoleSender().sendMessage(CC.translate("&4[" + plugin.getDescription().getName() + "] &cThe spawn location is not set!"));
			return;
		}

		event.getPlayer().teleport(spawn);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (PlayerState.isState(player, PlayerState.SPAWN)) {
			if (player.getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (plugin.getConfig("listeners.yml").getBoolean("players.disable_item-drop", true)) {
			if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Player player = (Player) event.getEntity();
		if (PlayerState.getState(player) == PlayerState.FIGHTING) {
			return;
		}

		if (player.getLocation().getY() < 0) {
			Forest.getInstance().getSpawnHandler().teleportToLocation(player);
		}


		if (plugin.getConfig("listeners.yml").getBoolean("players.disable_fall-damage", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (PlayerState.getState(player) == PlayerState.FIGHTING) {
				return;
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onEntityByBlock(EntityDamageByBlockEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (PlayerState.getState(player) == PlayerState.FIGHTING) {
				return;
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (plugin.getConfig("listeners.yml").getBoolean("players.disable_pick-up-item", true)) {
			if (PlayerState.getState(player) == PlayerState.FIGHTING) {
				return;
			}

			if (player.getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (plugin.getConfig("listeners.yml").getBoolean("world.disable-weather", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (player.getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
				player.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	private void onMoveItem(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (PlayerState.getState(player) == PlayerState.FIGHTING) {
				return;
			}

			if (event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory())) {
				event.setCancelled(true);
			}

			if (event.getSlotType() == InventoryType.SlotType.CRAFTING || event.isShiftClick() || event.getClick().isKeyboardClick()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
}