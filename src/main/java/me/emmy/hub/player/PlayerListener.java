package me.emmy.hub.player;

import me.emmy.hub.Forest;
import me.emmy.hub.feature.hubselector.HubSelectorMenu;
import me.emmy.hub.feature.serverselector.ServerSelectorMenu;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class PlayerListener implements Listener {

	private final Forest plugin = Forest.getInstance();

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
		player.getInventory().setArmorContents(null);

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
	private void onMoveItem(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory())) {
				event.setCancelled(true);
			}

			if (event.getSlotType() == InventoryType.SlotType.CRAFTING || event.isShiftClick() || event.getClick().isKeyboardClick()) {
				event.setCancelled(true);
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

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
}