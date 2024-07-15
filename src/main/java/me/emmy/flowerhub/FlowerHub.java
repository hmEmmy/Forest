package me.emmy.flowerhub;

import me.emmy.flowerhub.tablist.api.Tab;
import lombok.Getter;
import lombok.Setter;
import me.emmy.flowerhub.commands.FlowerHubCommand;
import me.emmy.flowerhub.commands.selectors.HubSelectorCommand;
import me.emmy.flowerhub.commands.selectors.ServerSelectorCommand;
import me.emmy.flowerhub.commands.selectors.SubSelectorOneCommand;
import me.emmy.flowerhub.commands.selectors.SubSelectorTwoCommand;
import me.emmy.flowerhub.commands.socials.*;
import me.emmy.flowerhub.commands.staff.FlowerHubReloadCommand;
import me.emmy.flowerhub.commands.staff.SetSpawnCommand;
import me.emmy.flowerhub.commands.staff.SpawnCommand;
import me.emmy.flowerhub.config.ConfigHandler;
import me.emmy.flowerhub.scoreboard.handler.ScoreboardHandler;
import me.emmy.flowerhub.listener.*;
import me.emmy.flowerhub.scoreboard.ScoreboardAdapter;
import me.emmy.flowerhub.tablist.TablistAdapter;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.WeatherUtil;
import me.emmy.flowerhub.utils.assemble.Assemble;
import me.emmy.flowerhub.utils.assemble.AssembleStyle;
import me.emmy.flowerhub.utils.command.CommandFramework;
import me.emmy.flowerhub.utils.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FlowerHub extends JavaPlugin {

	@Getter
	public static FlowerHub instance;
	private ScoreboardHandler scoreboardHandler;
	private CommandFramework commandFramework;
	private ConfigHandler configHandler;
	private Location location;

	@Override
	public void onEnable() {
		instance = this;

		long start = System.currentTimeMillis();

		registerHandlers();
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

	private void registerListeners() {
		List<Listener> listeners = Arrays.asList(
				new MenuListener(),
				new PlayerListener(),
				new EnderButtListener(),
				new DoubleJumpListener(),
				new FireworkLauncherListener(),
				new SneakRocketListener()
		);
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private void registerCommands() {
		if (this.getConfig("commands.yml").getBoolean("features.links.register-links")) {
			new StoreCommand();
			new TikTokCommand();
			new DiscordCommand();
			new WebsiteCommand();
			new TwitterCommand();
			new YouTubeCommand();
			new LinksCommand();
		}

		if (this.getConfig("listeners.yml").getBoolean("hubselector")) {
			new HubSelectorCommand();
		}

		if (this.getConfig("listeners.yml").getBoolean("serverselector")) {
			new ServerSelectorCommand();
		}

		new SpawnCommand();
		new SetSpawnCommand();
		new FlowerHubCommand();
		new SubSelectorOneCommand();
		new SubSelectorTwoCommand();
		new FlowerHubReloadCommand();
	}

	private void loadSpawnLocation() {
		FileConfiguration config = getConfig();
		boolean enableSpawnTeleport = config.getBoolean("enable-teleport", true);

		if (enableSpawnTeleport && config.contains("spawnLocation.world")) {
			World world = Bukkit.getWorld(config.getString("spawnLocation.world"));
			double x = config.getDouble("spawnLocation.x");
			double y = config.getDouble("spawnLocation.y");
			double z = config.getDouble("spawnLocation.z");
			float yaw = (float) config.getDouble("spawnLocation.yaw");
			float pitch = (float) config.getDouble("spawnLocation.pitch");

			this.location = new Location(world, x, y, z, yaw, pitch);
		}
	}

	private void loadTablist() {
		if (this.configHandler.getConfigByName("providers/tablist.yml").getBoolean("TABLIST.ENABLE")) {
			new Tab(this, new TablistAdapter());
		}
	}

	private void loadScoreboard() {
		if (this.configHandler.getConfigByName("providers/scoreboard.yml").getBoolean("scoreboard.enabled")) {
			Assemble assemble = new Assemble(this, new ScoreboardAdapter());
			assemble.setTicks(2);
			assemble.setAssembleStyle(AssembleStyle.MODERN);
		}
	}

	public void teleportToSpawn(Player player) {
		FileConfiguration config = this.getConfig();
		String worldName = config.getString("spawnLocation.world");
		double x = config.getDouble("spawnLocation.x");
		double y = config.getDouble("spawnLocation.y");
		double z = config.getDouble("spawnLocation.z");
		float yaw = (float) config.getDouble("spawnLocation.yaw");
		float pitch = (float) config.getDouble("spawnLocation.pitch");

		World world = Bukkit.getWorld(worldName);
		if (world != null) {
			Location spawnLocation = new Location(world, x, y, z, yaw, pitch);
			player.teleport(spawnLocation);
		} else {
			player.sendMessage("The spawn world is not loaded.");
		}
	}

	public void setLocation(Location location) {
		this.location = location;
		FileConfiguration config = this.getConfig();

		config.set("spawnLocation.world", location.getWorld().getName());
		config.set("spawnLocation.x", location.getX());
		config.set("spawnLocation.y", location.getY());
		config.set("spawnLocation.z", location.getZ());
		config.set("spawnLocation.yaw", location.getYaw());
		config.set("spawnLocation.pitch", location.getPitch());

		saveConfig();
	}

	public FileConfiguration getConfig(String fileName) {
		File configFile = new File(getDataFolder(), fileName);
		return YamlConfiguration.loadConfiguration(configFile);
	}
}