package me.emmy.flowerhub.listener;

import me.emmy.flowerhub.FlowerHub;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: github.com/Emmiesa
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
        ConfigurationSection configSection = FlowerHub.getInstance().getConfig("listeners.yml").getConfigurationSection("sneak_rocket");

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

        if (enabled && player.getGameMode().name().equalsIgnoreCase("SURVIVAL") && event.isSneaking()) {
            player.setVelocity(player.getLocation().getDirection().multiply(velocityMultiplier).setY(1));

            if (soundEnabled) {
                player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
            }
        }
    }
}