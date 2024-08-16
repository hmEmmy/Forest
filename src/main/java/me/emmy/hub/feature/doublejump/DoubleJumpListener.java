package me.emmy.hub.feature.doublejump;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class DoubleJumpListener implements Listener {

    private boolean enabled;
    private boolean soundEnabled;
    private Sound sound;
    private double velocityMultiplier;

    public DoubleJumpListener() {
        loadConfig();
    }

    private void loadConfig() {
        ConfigurationSection configSection = Forest.getInstance().getConfig("listeners.yml").getConfigurationSection("double_jump");

        if (configSection != null) {
            enabled = configSection.getBoolean("enabled", true);
            soundEnabled = configSection.getBoolean("sound_enabled", true);
            sound = Sound.valueOf(configSection.getString("sound", "FIREWORK_LAUNCH"));
            velocityMultiplier = configSection.getDouble("velocity_multiplier", 1.5);
        }
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (PlayerState.isState(player, PlayerState.SPAWN)) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                if (enabled && player.getGameMode().name().equalsIgnoreCase("SURVIVAL") && !player.isFlying()) {
                    event.setCancelled(true);

                    player.setFlying(false);
                    player.setAllowFlight(false);

                    player.setVelocity(player.getLocation().getDirection().multiply(velocityMultiplier).setY(1));

                    if (soundEnabled) {
                        player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
                    }

                    player.setAllowFlight(true);
                }
            }
        }
    }
}
