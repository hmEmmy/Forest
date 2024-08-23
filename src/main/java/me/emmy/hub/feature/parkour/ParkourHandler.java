package me.emmy.hub.feature.parkour;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Emmy
 * @project Forest
 * @date 21/08/2024 - 19:21
 */
@Getter
@Setter
public class ParkourHandler {

    //TODO: If I add a mongo database in future, save the fastest time the player has done the parkour

    private Map<Player, Parkour> parkour = new HashMap<>();
    private Set<Player> lastCheckPoint = new HashSet<>();

    private Location startLocation;
    private Location endLocation;

    //private long fastestTime;

    public void startParkour(Player player) {
        parkour.put(player, new Parkour());
        player.teleport(startLocation);
        player.sendMessage(CC.translate("&aYou have started the parkour!"));
    }

    public void endParkour(Player player) {
        Parkour parkour = this.parkour.get(player);
        parkour.setEndTime(System.currentTimeMillis());
        parkour.setTimeTaken(parkour.getEndTime() - parkour.getStartTime());
        player.teleport(endLocation);
        player.sendMessage(CC.translate("&aYou have completed the parkour in " + getFormattedTime(player) + "."));
    }

    public String getFormattedTime(Player player) {
        long time = parkour.get(player).getTimeTaken();
        return String.format("%02d:%02d", time / 60000, (time / 1000) % 60);
    }

    public Parkour getParkour(Player player) {
        return parkour.get(player);
    }

    public boolean isOnParkour(Player player) {
        return parkour.containsKey(player);
    }

    public void setStartLocation(Location location) {
        this.startLocation = location;

        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");

        config.set("parkour.start.x", location.getX());
        config.set("parkour.start.y", location.getY());
        config.set("parkour.start.z", location.getZ());
        config.set("parkour.start.yaw", location.getYaw());
        config.set("parkour.start.pitch", location.getPitch());
        config.set("parkour.start.world", location.getWorld().getName());

        Forest.getInstance().getConfigHandler().saveConfig(Forest.getInstance().getConfigHandler().getConfigFile("settings.yml"), config);
    }

    public void loadStartLocation() {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");

        if (config.contains("parkour.start.world")) {
            double x = config.getDouble("parkour.start.x");
            double y = config.getDouble("parkour.start.y");
            double z = config.getDouble("parkour.start.z");
            float yaw = (float) config.getDouble("parkour.start.yaw");
            float pitch = (float) config.getDouble("parkour.start.pitch");

            this.startLocation = new Location(Forest.getInstance().getServer().getWorld(config.getString("parkour.start.world")), x, y, z, yaw, pitch);
        }
    }

    public void teleportToStartLocation(Player player) {
        player.teleport(startLocation);
    }

    public void setEndLocation(Location location) {
        this.endLocation = location;

        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");

        config.set("parkour.end.x", location.getX());
        config.set("parkour.end.y", location.getY());
        config.set("parkour.end.z", location.getZ());
        config.set("parkour.end.yaw", location.getYaw());
        config.set("parkour.end.pitch", location.getPitch());
        config.set("parkour.end.world", location.getWorld().getName());

        Forest.getInstance().getConfigHandler().saveConfig(Forest.getInstance().getConfigHandler().getConfigFile("settings.yml"), config);
    }

    public void loadEndLocation() {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("settings.yml");

        if (config.contains("parkour.end.world")) {
            double x = config.getDouble("parkour.end.x");
            double y = config.getDouble("parkour.end.y");
            double z = config.getDouble("parkour.end.z");
            float yaw = (float) config.getDouble("parkour.end.yaw");
            float pitch = (float) config.getDouble("parkour.end.pitch");

            this.endLocation = new Location(Forest.getInstance().getServer().getWorld(config.getString("parkour.end.world")), x, y, z, yaw, pitch);
        }
    }

    public void teleportToEndLocation(Player player) {
        player.teleport(endLocation);
    }
}
