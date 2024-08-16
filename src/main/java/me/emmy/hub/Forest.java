package me.emmy.hub;

import me.emmy.hub.commands.ForestCommand;
import me.emmy.hub.commands.essential.SuggestServerCommand;
import me.emmy.hub.commands.selectors.HubSelectorCommand;
import me.emmy.hub.commands.selectors.ServerSelectorCommand;
import me.emmy.hub.commands.selectors.SubSelectorOneCommand;
import me.emmy.hub.commands.selectors.SubSelectorTwoCommand;
import me.emmy.hub.commands.staff.ReloadCommand;
import me.emmy.hub.commands.staff.SetSpawnCommand;
import me.emmy.hub.commands.staff.SpawnCommand;
import me.emmy.hub.config.ConfigHandler;
import me.emmy.hub.feature.cosmetic.CosmeticRepository;
import me.emmy.hub.feature.cosmetic.command.CosmeticsCommand;
import me.emmy.hub.feature.cosmetic.listener.CosmeticListener;
import me.emmy.hub.feature.doublejump.DoubleJumpListener;
import me.emmy.hub.feature.enderbutt.EnderButtListener;
import me.emmy.hub.feature.fireworklauncher.FireworkLauncherListener;
import me.emmy.hub.feature.sneakrocket.SneakRocketListener;
import me.emmy.hub.player.PlayerListener;
import me.emmy.hub.scoreboard.ScoreboardAdapter;
import me.emmy.hub.scoreboard.handler.ScoreboardHandler;
import me.emmy.hub.tablist.TablistAdapter;
import me.emmy.hub.tablist.api.Tab;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.WeatherUtil;
import me.emmy.hub.utils.assemble.Assemble;
import me.emmy.hub.utils.assemble.AssembleStyle;
import me.emmy.hub.utils.command.CommandFramework;
import me.emmy.hub.utils.menu.MenuListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Setter;
import lombok.Getter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class Forest extends JavaPlugin {

	@Getter
	private static Forest instance;
	
	private ScoreboardHandler scoreboardHandler;
	private ConfigHandler configHandler;
	private CommandFramework commandFramework;
	private Location location;
	private CosmeticRepository cosmeticRepository;

	@Override
	public void onEnable() {
		instance = this;

		long start = System.currentTimeMillis();

		registerHandlers();
		registerRepositories();
		registerCommands();
		registerListeners();
		loadScoreboard();
		loadTablist();
		loadSpawnLocation();

		long end = System.currentTimeMillis();
		long timeTaken = end - start;

		CC.loadPlugin(timeTaken);
	}

	@Override
	public void onDisable() {
		CC.disablePlugin();
	}

	private void registerHandlers() {
		this.configHandler = new ConfigHandler();
		this.commandFramework = new CommandFramework(this);
		this.scoreboardHandler = new ScoreboardHandler();

		if (this.getConfig("listeners.yml").getBoolean("world.disable-weather", true)) {
			WeatherUtil.disableWeatherInAllWorlds();
		}
	}

	private void registerRepositories() {
		this.cosmeticRepository = new CosmeticRepository();
	}

	private void registerListeners() {
		List<Listener> listeners = Arrays.asList(
				new MenuListener(),
				new PlayerListener(),
				new EnderButtListener(),
				new DoubleJumpListener(),
				new FireworkLauncherListener(),
				new SneakRocketListener(),
				new CosmeticListener()
		);
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private void registerCommands() {
		if (this.getConfig("listeners.yml").getBoolean("commands.hubselector")) {
			new HubSelectorCommand();
		}

		if (this.getConfig("listeners.yml").getBoolean("commands.serverselector")) {
			new ServerSelectorCommand();
		}

		new SpawnCommand();
		new SetSpawnCommand();
		new ForestCommand();
		new SubSelectorOneCommand();
		new SubSelectorTwoCommand();
		new ReloadCommand();
		new SuggestServerCommand();
		new CosmeticsCommand();
	}

	private void loadSpawnLocation() {
		FileConfiguration config = configHandler.getConfig("listeners.yml");
		boolean enableSpawnTeleport = config.getBoolean("spawn.teleporting", true);

		if (enableSpawnTeleport && config.contains("spawn.world")) {
			World world = Bukkit.getWorld(config.getString("spawn.world"));
			double x = config.getDouble("spawn.x");
			double y = config.getDouble("spawn.y");
			double z = config.getDouble("spawn.z");
			float yaw = (float) config.getDouble("spawn.yaw");
			float pitch = (float) config.getDouble("spawn.pitch");

			this.location = new Location(world, x, y, z, yaw, pitch);
		}
	}

	private void loadTablist() {
		if (this.configHandler.getConfig("providers/tablist.yml").getBoolean("TABLIST.ENABLE")) {
			new Tab(this, new TablistAdapter());
		}
	}

	private void loadScoreboard() {
		if (this.configHandler.getConfig("providers/scoreboard.yml").getBoolean("scoreboard.enabled")) {
			Assemble assemble = new Assemble(this, new ScoreboardAdapter());
			assemble.setTicks(2);
			assemble.setAssembleStyle(AssembleStyle.MODERN);
		}
	}

    /**
     * Teleport a player to the spawn location
     *
     * @param player the player to teleport
     */
	public void teleportToSpawn(Player player) {
		FileConfiguration config = configHandler.getConfig("listeners.yml");
		double x = config.getDouble("spawn.x");
		double y = config.getDouble("spawn.y");
		double z = config.getDouble("spawn.z");
		float yaw = (float) config.getDouble("spawn.yaw");
		float pitch = (float) config.getDouble("spawn.pitch");

        String worldName = config.getString("spawn.world");
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			player.sendMessage("The spawn world is not loaded.");
            return;
		}

        Location spawnLocation = new Location(world, x, y, z, yaw, pitch);
        player.teleport(spawnLocation);
	}

    /**
     * Set the spawn location and save it to the listeners config
     *
     * @param location the location to set the spawn to
     */
	public void setLocation(Location location) {
		this.location = location;
		FileConfiguration config = configHandler.getConfig("listeners.yml");
		config.set("spawn.world", location.getWorld().getName());
		config.set("spawn.x", location.getX());
		config.set("spawn.y", location.getY());
		config.set("spawn.z", location.getZ());
		config.set("spawn.yaw", location.getYaw());
		config.set("spawn.pitch", location.getPitch());
		saveConfig();
	}


    /**
     * allows you to access a config file provided and reloads it automatically
     *
     * @param fileName the name of the config file
     * @return the config file
     */
	public FileConfiguration getConfig(String fileName) {
		File configFile = new File(getDataFolder(), fileName);
		return YamlConfiguration.loadConfiguration(configFile);
	}
}