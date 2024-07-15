package me.emmy.flowerhub.listener;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.menu.hubselector.HubSelectorMenu;
import me.emmy.flowerhub.menu.serverselector.ServerSelectorMenu;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: github.com/Emmiesa
 */

public class PlayerListener implements Listener {

	private final FlowerHub plugin = FlowerHub.getInstance();

	private final ItemStack enderbutt, selector, hubselector, fireworklauncher;

	public PlayerListener() {
		String selectorMaterial = plugin.getConfig("hotbar.yml").getString("items.server_selector.material");
		String hubselectorMaterial = plugin.getConfig("hotbar.yml").getString("items.hub_selector.material");

		this.selector = new ItemBuilder(Material.matchMaterial(selectorMaterial)).lore(plugin.getConfig("hotbar.yml").getStringList("items.server_selector.lore")).name(plugin.getConfig("hotbar.yml").getString("items.server_selector.name")).build();
		this.hubselector = new ItemBuilder(Material.matchMaterial(hubselectorMaterial)).lore(plugin.getConfig("hotbar.yml").getStringList("items.hub_selector.lore")).name(plugin.getConfig("hotbar.yml").getString("items.hub_selector.name")).build();
		this.enderbutt = new ItemBuilder(Material.ENDER_PEARL).lore(plugin.getConfig("hotbar.yml").getStringList("items.enderbutt.lore")).name(plugin.getConfig("hotbar.yml").getString("items.enderbutt.name")).build();
		this.fireworklauncher = new ItemBuilder(Material.FIREWORK).lore(plugin.getConfig("hotbar.yml").getStringList("items.firework.lore")).name(plugin.getConfig("hotbar.yml").getString("items.firework.name")).build();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();

		player.setGameMode(GameMode.SURVIVAL);

		String slottyyslotString = plugin.getConfig("listeners.yml").getString("players.on-join_selected-slot");
		int slot;

		try {
			slot = Integer.parseInt(slottyyslotString);
		} catch (NumberFormatException e) {
			slot = 0;
		}

		player.getInventory().setHeldItemSlot(slot);

		if (plugin.getConfig("listeners.yml").getBoolean("double_jump.enabled", true)) {
			player.setAllowFlight(true);
		}

		if (plugin.getConfig("hotbar.yml").getBoolean("items.enderbutt.enabled", true)) {
			player.getInventory().setItem(plugin.getConfig("hotbar.yml").getInt("items.enderbutt.slot"), enderbutt);
		}

		if (plugin.getConfig("hotbar.yml").getBoolean("items.firework.enabled", true)) {
			player.getInventory().setItem(plugin.getConfig("hotbar.yml").getInt("items.firework.slot"), fireworklauncher);
		}

		if (plugin.getConfig("hotbar.yml").getBoolean("items.server_selector.enabled", true)) {
			player.getInventory().setItem(plugin.getConfig("hotbar.yml").getInt("items.server_selector.slot"), selector);
		}

		if (plugin.getConfig("hotbar.yml").getBoolean("items.hub_selector.enabled", true)) {
			player.getInventory().setItem(plugin.getConfig("hotbar.yml").getInt("items.hub_selector.slot"), hubselector);
		}

		// dynamic / custom added items getting it from the string with the following name.
		ConfigurationSection dynamicItemsConfig = plugin.getConfig("hotbar.yml").getConfigurationSection("dynamic_items");
		if (dynamicItemsConfig != null) {
			for (String itemName : dynamicItemsConfig.getKeys(false)) {
				if (dynamicItemsConfig.getBoolean(itemName + ".enabled", true)) {
					ItemStack hotBarItem = new ItemBuilder(Material.valueOf(dynamicItemsConfig.getString(itemName + ".material")))
							.name(dynamicItemsConfig.getString(itemName + ".name"))
							.lore(dynamicItemsConfig.getStringList(itemName + ".lore"))
							.build();
					player.getInventory().setItem(dynamicItemsConfig.getInt(itemName + ".slot"), hotBarItem);
				}
			}
		}

		if (plugin.getConfig("listeners.yml").getBoolean("welcome_message.enabled", true)) {
			for (String message : plugin.getConfig("listeners.yml").getStringList("welcome_message.message"))
				player.sendMessage(CC.translate(message));
		}

		Location spawnLocation = plugin.getLocation();

		if (spawnLocation != null) {
			event.getPlayer().teleport(spawnLocation);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (item != null && item.equals(selector)) {
				boolean commandEnabled = plugin.getConfig("hotbar.yml").getBoolean("items.server_selector.execute_command.enabled", true);

				if (commandEnabled) {
					String command = plugin.getConfig("hotbar.yml").getString("items.server_selector.execute_command.command");
					player.performCommand(command);
				} else {
					new ServerSelectorMenu().openMenu(player);
				}

				event.setCancelled(true);
			}

			if (item != null && item.equals(hubselector)) {
				boolean commandEnabled = plugin.getConfig("hotbar.yml").getBoolean("items.hub_selector.execute_command.enabled", true);

				if (commandEnabled) {
					String command = plugin.getConfig("hotbar.yml").getString("items.hub_selector.execute_command.command");
					player.performCommand(command);
				} else {
					new HubSelectorMenu().openMenu(player);
				}

				event.setCancelled(true);
			}

			for (String itemName : plugin.getConfig("hotbar.yml").getConfigurationSection("dynamic_items").getKeys(false)) {
				if (item != null && item.equals(getHotBarItem(itemName))) {

					if (plugin.getConfig("hotbar.yml").getBoolean("dynamic_items." + itemName + ".execute_command.enabled", true)) {
						player.performCommand(plugin.getConfig("hotbar.yml").getString("dynamic_items." + itemName + ".execute_command.command"));
					} else {
						event.setCancelled(true);
					}
					break;
				}
			}

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
		if (plugin.getConfig("listeners.yml").getBoolean("players.disable_fall-damage", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (plugin.getConfig("listeners.yml").getBoolean("players.disable_pick-up-item", true)) {
			if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
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
	public void onMoveItem(InventoryClickEvent e) {
		if (e.getClickedInventory() != null && e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {

			if (e.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
				e.setCancelled(true);
			}
		}
	}

	private ItemStack getHotBarItem(String itemName) {
		ConfigurationSection config = plugin.getConfig("hotbar.yml").getConfigurationSection("dynamic_items." + itemName);
		ConfigurationSection dynamicItemsConfig = plugin.getConfig("hotbar.yml").getConfigurationSection("dynamic_items");
		if (config != null && config.getBoolean("enabled", true)) {
			return new ItemBuilder(Material.valueOf(config.getString("material")))
					.name(config.getString("name"))
					.lore(dynamicItemsConfig.getStringList(itemName + ".lore"))
					.build();
		}
		return null;
	}
}