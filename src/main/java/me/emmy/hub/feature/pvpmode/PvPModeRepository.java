package me.emmy.hub.feature.pvpmode;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.Forest;
import me.emmy.hub.feature.hotbar.Hotbar;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.utils.CC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 19:52
 */
@Getter
@Setter
public class PvPModeRepository {

    private Set<Player> players = new HashSet<>();
    private HashMap<Player, PlayerState> playerState = new HashMap<>();
    private Location spawn;

    /**
     * Add player to the game and make them ready to fight
     *
     * @param player Player
     */
    public void addPlayer(Player player) {
        players.add(player);

        PlayerState.setState(player, PlayerState.FIGHTING);
        Forest.getInstance().getCosmeticRepository().unEquipCosmetic(player);

        player.getInventory().clear();
        this.applyItems(player);
        player.updateInventory();

        teleportToPvPSpawn(player);
    }

    /**
     * Apply PvP items to the player
     *
     * @param player Player
     */
    public void applyItems(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

        player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        player.getInventory().setItem(1,new ItemStack(Material.FISHING_ROD));
        player.getInventory().setItem(2,new ItemStack(Material.BOW));

        player.getInventory().setItem(3,new ItemStack(Material.GOLDEN_APPLE, 5));
        player.getInventory().setItem(8, new ItemStack(Material.ARROW, 64));
        player.updateInventory();
    }

    /**
     * Remove player from the game and teleport them to the spawn
     *
     * @param player Player
     */
    public void removePlayer(Player player) {
        PlayerState.setState(player, PlayerState.SPAWN);

        players.remove(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        Forest.getInstance().getSpawnHandler().teleportToLocation(player);
        Hotbar.applyHotbarItems(player);

        player.updateInventory();
    }

    /**
     * Check if the player is in the game
     *
     * @param player Player
     * @return boolean
     */
    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    /**
     * Set the PvP spawn location
     *
     * @param location Location
     */
    public void setPvPSpawn(Location location) {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("listeners.yml");
        World world = location.getWorld();

        config.set("pvpmode.spawn.world", world.getName());
        config.set("pvpmode.spawn.x", location.getX());
        config.set("pvpmode.spawn.y", location.getY());
        config.set("pvpmode.spawn.z", location.getZ());
        config.set("pvpmode.spawn.yaw", location.getYaw());
        config.set("pvpmode.spawn.pitch", location.getPitch());

        Forest.getInstance().getConfigHandler().saveConfig(Forest.getInstance().getConfigHandler().getConfigFile("listeners.yml"), config);
        this.spawn = location;
    }

    /**
     * Load the PvP spawn location
     */
    public void loadPvPSpawn() {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("listeners.yml");

        World world = Forest.getInstance().getServer().getWorld(config.getString("pvpmode.spawn.world"));
        if (world == null) {
            Forest.getInstance().getServer().getConsoleSender().sendMessage(CC.translate("&4[" + Forest.getInstance().getDescription().getName() + "] &cThe world " + config.getString("pvpmode.spawn.world") + " does not exist!"));
            return;
        }

        double x = config.getDouble("pvpmode.spawn.x");
        double y = config.getDouble("pvpmode.spawn.y");
        double z = config.getDouble("pvpmode.spawn.z");
        float yaw = (float) config.getDouble("pvpmode.spawn.yaw");
        float pitch = (float) config.getDouble("pvpmode.spawn.pitch");

        spawn = new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Teleport the player to the PvP spawn location
     *
     * @param player Player
     */
    public void teleportToPvPSpawn(Player player) {
        FileConfiguration config = Forest.getInstance().getConfigHandler().getConfig("listeners.yml");
        double x = config.getDouble("pvpmode.spawn.x");
        double y = config.getDouble("pvpmode.spawn.y");
        double z = config.getDouble("pvpmode.spawn.z");
        float yaw = (float) config.getDouble("pvpmode.spawn.yaw");
        float pitch = (float) config.getDouble("pvpmode.spawn.pitch");

        String worldName = config.getString("pvpmode.spawn.world");
        World world = Forest.getInstance().getServer().getWorld(worldName);
        if (world == null) {
            Forest.getInstance().getServer().getConsoleSender().sendMessage(CC.translate("&4[" + Forest.getInstance().getDescription().getName() + "] &cThe world " + worldName + " does not exist!"));
            return;
        }

        Location spawnLocation = new Location(world, x, y, z, yaw, pitch);
        player.teleport(spawnLocation);
    }
}
