package me.emmy.hub;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.commands.ForestCommand;
import me.emmy.hub.commands.essential.SuggestServerCommand;
import me.emmy.hub.feature.menus.hubselector.command.HubSelectorCommand;
import me.emmy.hub.feature.menus.serverselector.command.ServerSelectorCommand;
import me.emmy.hub.feature.menus.subselectors.subselectorone.command.SubSelectorOneCommand;
import me.emmy.hub.feature.menus.subselectors.subselectortwo.command.SubSelectorTwoCommand;
import me.emmy.hub.commands.staff.ReloadCommand;
import me.emmy.hub.feature.pvpmode.command.admin.SetPvPSpawnCommand;
import me.emmy.hub.feature.spawn.command.SetSpawnCommand;
import me.emmy.hub.feature.spawn.command.SpawnCommand;
import me.emmy.hub.config.ConfigHandler;
import me.emmy.hub.feature.cosmetic.CosmeticRepository;
import me.emmy.hub.feature.cosmetic.command.CosmeticsCommand;
import me.emmy.hub.feature.cosmetic.listener.CosmeticListener;
import me.emmy.hub.feature.doublejump.DoubleJumpListener;
import me.emmy.hub.feature.enderbutt.EnderButtListener;
import me.emmy.hub.feature.fireworklauncher.FireworkLauncherListener;
import me.emmy.hub.feature.hotbar.listener.HotbarListener;
import me.emmy.hub.feature.pvpmode.PvPModeRepository;
import me.emmy.hub.feature.pvpmode.command.JoinPvPModeCommand;
import me.emmy.hub.feature.pvpmode.command.LeavePvPModeCommand;
import me.emmy.hub.feature.pvpmode.listener.PvPModeListener;
import me.emmy.hub.feature.sneakrocket.SneakRocketListener;
import me.emmy.hub.feature.spawn.SpawnHandler;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
	private CosmeticRepository cosmeticRepository;
	private SpawnHandler spawnHandler;
	private PvPModeRepository pvpModeRepository;

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
		this.spawnHandler = new SpawnHandler();
		this.spawnHandler.loadLocation();

		if (this.getConfig("listeners.yml").getBoolean("world.disable-weather", true)) {
			WeatherUtil.disableWeatherInAllWorlds();
		}
	}

	private void registerRepositories() {
		this.cosmeticRepository = new CosmeticRepository();
		this.pvpModeRepository = new PvPModeRepository();
		this.pvpModeRepository.loadPvPSpawn();
	}

	private void registerListeners() {
		List<Listener> listeners = Arrays.asList(
				new MenuListener(),
				new PlayerListener(),
				new PvPModeListener(),
				new EnderButtListener(),
				new DoubleJumpListener(),
				new FireworkLauncherListener(),
				new SneakRocketListener(),
				new CosmeticListener(),
				new HotbarListener()
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
		new JoinPvPModeCommand();
		new LeavePvPModeCommand();
		new SetPvPSpawnCommand();
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