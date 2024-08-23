package me.emmy.hub.spawn;

import lombok.Getter;
import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 19:37
 */
@Getter
public class SpawnHandler {

    private Location location;

    public void loadLocation() {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");
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

    /**
     * Teleport a player to the spawn location
     *
     * @param player the player to teleport
     */
    public void teleportToLocation(Player player) {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        float yaw = (float) config.getDouble("spawn.yaw");
        float pitch = (float) config.getDouble("spawn.pitch");

        String worldName = config.getString("spawn.world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4[" + Forest.getInstance().getDescription().getName() + "] &cThe world " + worldName + " does not exist!"));
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
    public void saveLocation(Location location) {
        this.location = location;
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");
        config.set("spawn.world", location.getWorld().getName());
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.yaw", location.getYaw());
        config.set("spawn.pitch", location.getPitch());
        Forest.getInstance().getConfigHandler().saveConfig(Forest.getInstance().getConfigHandler().getConfigFile("settings.yml"), config);
    }
}
