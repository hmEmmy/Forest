package me.emmy.hub;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.command.ForestCommand;
import me.emmy.hub.command.debug.StateCommand;
import me.emmy.hub.command.essential.TogglePlayerVisibilityCommand;
import me.emmy.hub.feature.parkour.ParkourHandler;
import me.emmy.hub.feature.parkour.command.SetParkourLocationCommand;
import me.emmy.hub.feature.parkour.listener.ParkourListener;
import me.emmy.hub.menus.hubselector.command.HubSelectorCommand;
import me.emmy.hub.menus.serverselector.command.ServerSelectorCommand;
import me.emmy.hub.menus.subselectors.subselectorone.command.SubSelectorOneCommand;
import me.emmy.hub.menus.subselectors.subselectortwo.command.SubSelectorTwoCommand;
import me.emmy.hub.command.staff.ReloadCommand;
import me.emmy.hub.feature.pvpmode.command.admin.SetPvPSpawnCommand;
import me.emmy.hub.spawn.command.SetSpawnCommand;
import me.emmy.hub.spawn.command.SpawnCommand;
import me.emmy.hub.config.ConfigHandler;
import me.emmy.hub.feature.cosmetic.CosmeticRepository;
import me.emmy.hub.feature.cosmetic.command.CosmeticsCommand;
import me.emmy.hub.feature.cosmetic.listener.CosmeticListener;
import me.emmy.hub.listener.DoubleJumpListener;
import me.emmy.hub.listener.EnderButtListener;
import me.emmy.hub.listener.FireworkLauncherListener;
import me.emmy.hub.feature.hotbar.listener.HotbarListener;
import me.emmy.hub.feature.pvpmode.PvPModeRepository;
import me.emmy.hub.feature.pvpmode.command.JoinPvPModeCommand;
import me.emmy.hub.feature.pvpmode.command.LeavePvPModeCommand;
import me.emmy.hub.feature.pvpmode.listener.PvPModeListener;
import me.emmy.hub.listener.SneakRocketListener;
import me.emmy.hub.spawn.SpawnHandler;
import me.emmy.hub.player.PlayerListener;
import me.emmy.hub.visual.scoreboard.ScoreboardVisualizer;
import me.emmy.hub.visual.scoreboard.ScoreboardHandler;
import me.emmy.hub.visual.tablist.TabVisualizer;
import me.emmy.hub.api.tab.Tab;
import me.emmy.hub.util.CC;
import me.emmy.hub.util.WeatherUtil;
import me.emmy.hub.api.assemble.Assemble;
import me.emmy.hub.api.assemble.AssembleStyle;
import me.emmy.hub.api.command.CommandFramework;
import me.emmy.hub.api.menu.MenuListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Forest
 * @date 11/11/2023 - 11:56
 */
@Getter @Setter
public class Forest extends JavaPlugin {

	@Getter
	private static Forest instance;

	private ScoreboardHandler scoreboardHandler;
	private ConfigHandler configHandler;
	private CommandFramework commandFramework;
	private CosmeticRepository cosmeticRepository;
	private SpawnHandler spawnHandler;
	private PvPModeRepository pvpModeRepository;
	private ParkourHandler parkourHandler;

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

		long end = System.currentTimeMillis();
		long timeTaken = end - start;

		CC.sendEnableMessage(timeTaken);
	}

	@Override
	public void onDisable() {
		CC.sendDisableMessage();
	}

	private void registerHandlers() {
		this.configHandler = new ConfigHandler();
		this.commandFramework = new CommandFramework(this);
		this.scoreboardHandler = new ScoreboardHandler();
		this.spawnHandler = new SpawnHandler();
		this.spawnHandler.loadLocation();
		this.parkourHandler = new ParkourHandler();
		this.parkourHandler.loadEndLocation();
		this.parkourHandler.loadStartLocation();

		if (configHandler.getConfig("settings.yml").getBoolean("world.disable-weather", true)) {
			WeatherUtil.disableWeatherInAllWorlds();
		}
	}

	private void registerRepositories() {
		this.cosmeticRepository = new CosmeticRepository();
		this.pvpModeRepository = new PvPModeRepository();
		this.pvpModeRepository.loadPvPSpawn();
	}

	private void registerCommands() {
		if (configHandler.getConfig("settings.yml").getBoolean("commands.hubselector")) {
			new HubSelectorCommand();
		}

		if (configHandler.getConfig("settings.yml").getBoolean("commands.serverselector")) {
			new ServerSelectorCommand();
		}

		new SpawnCommand();
		new SetSpawnCommand();
		new ForestCommand();
		new SubSelectorOneCommand();
		new SubSelectorTwoCommand();
		new ReloadCommand();
		new CosmeticsCommand();
		new JoinPvPModeCommand();
		new LeavePvPModeCommand();
		new SetPvPSpawnCommand();
		new TogglePlayerVisibilityCommand();
		new SetParkourLocationCommand();
		new StateCommand();
	}

	private void registerListeners() {
		List<Listener> listeners = Arrays.asList(
				new EnderButtListener(),
				new MenuListener(),
				new PlayerListener(),
				new PvPModeListener(),
				new DoubleJumpListener(),
				new FireworkLauncherListener(),
				new SneakRocketListener(),
				new CosmeticListener(),
				new HotbarListener(),
				new ParkourListener()
		);
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private void loadScoreboard() {
		if (configHandler.getConfig("providers/scoreboard.yml").getBoolean("scoreboard.enabled")) {
			Assemble assemble = new Assemble(this, new ScoreboardVisualizer());
			assemble.setTicks(2);
			assemble.setAssembleStyle(AssembleStyle.MODERN);
		}
	}

	private void loadTablist() {
		if (configHandler.getConfig("providers/tablist.yml").getBoolean("tablist.enabled")) {
			new Tab(this, new TabVisualizer());
		}
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