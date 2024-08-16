package me.emmy.hub.feature.sneakrocket;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SneakRocketListener implements Listener {

    private boolean enabled;
    private boolean soundEnabled;
    private Sound sound;
    private double velocityMultiplier;

    public SneakRocketListener() {
        loadConfig();
    }

    private void loadConfig() {
        ConfigurationSection configSection = Forest.getInstance().getConfig("listeners.yml").getConfigurationSection("sneak_rocket");

        if (configSection != null) {
            enabled = configSection.getBoolean("enabled", true);
            soundEnabled = configSection.getBoolean("sound_enabled", true);
            sound = Sound.valueOf(configSection.getString("sound", "FIREWORK_LAUNCH"));
            velocityMultiplier = configSection.getDouble("velocity_multiplier", 1.5);
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (PlayerState.isState(player, PlayerState.SPAWN)) {
            if (enabled && player.getGameMode().name().equalsIgnoreCase("SURVIVAL") && event.isSneaking()) {
                player.setVelocity(player.getLocation().getDirection().multiply(velocityMultiplier).setY(1));

                if (soundEnabled) {
                    player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
                }
            }
        }
    }
}