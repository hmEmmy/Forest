package me.emmy.flowerhub.config;

import lombok.Getter;
import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
public class ConfigHandler {

    private final FlowerHub plugin = FlowerHub.getInstance();
    private final Map<String, File> configFiles = new HashMap<>();
    private final Map<String, FileConfiguration> fileConfigurations = new HashMap<>();

    private final String[] configFileNames = {
            "config.yml", "hotbar.yml", "listeners.yml", "menus.yml", "messages.yml", "providers/tablist.yml", "providers/scoreboard.yml"
    };

    public ConfigHandler() {
        for (String fileName : configFileNames) {
            loadConfig(fileName);
        }
    }

    public void reloadConfigs() {
        for (String fileName : configFileNames) {
            loadConfig(fileName);
        }
    }

    /**
     * Load a config
     *
     * @param fileName the name of the config
     */
    private void loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        this.configFiles.put(fileName, configFile);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        this.fileConfigurations.put(fileName, config);
    }

    /**
     * Save a config
     *
     * @param configFile        the config file to save
     * @param fileConfiguration the config to save
     */
    public void saveConfig(File configFile, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(configFile);
            fileConfiguration.load(configFile);
        } catch (Exception e) {
            CC.sendError("Error occurred while saving config: " + configFile.getName());
        }
    }

    /**
     * Get a config by its name
     *
     * @param fileName the name of the config
     * @return the config
     */
    public FileConfiguration getConfigByName(String fileName) {
        return this.fileConfigurations.get(fileName);
    }

    /**
     * Get a config file by its name
     *
     * @param fileName the name of the config
     * @return the config file
     */
    public File getConfigFileByName(String fileName) {
        return this.configFiles.get(fileName);
    }
}