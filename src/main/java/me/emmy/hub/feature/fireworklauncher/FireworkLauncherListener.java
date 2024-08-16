package me.emmy.hub.feature.fireworklauncher;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class FireworkLauncherListener implements Listener {

    private final Forest plugin = Forest.getInstance();

    private Firework currentFirework = null;
    private BukkitTask followEffectTask = null;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (PlayerState.isState(event.getPlayer(), PlayerState.SPAWN)) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (item != null && item.getType() == Material.FIREWORK) {
                    if (currentFirework != null) {
                        currentFirework.remove();
                        followEffectTask.cancel();
                    }

                    currentFirework = player.getWorld().spawn(player.getLocation(), Firework.class);

                    FireworkEffect effect = FireworkEffect.builder()
                            .withColor(Color.RED, Color.GREEN, Color.BLUE)
                            .withFade(Color.YELLOW)
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .trail(true)
                            .flicker(true)
                            .build();

                    FireworkMeta meta = currentFirework.getFireworkMeta();
                    meta.addEffect(effect);
                    meta.setPower(2);
                    currentFirework.setFireworkMeta(meta);

                    currentFirework.setPassenger(player);

                    if (plugin.getConfig("listeners.yml").getBoolean("fireworklauncher.sound_enabled")) {
                        String sound = plugin.getConfig("listeners.yml").getString("fireworklauncher.sound");
                        player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                    }

                    if (plugin.getConfig("listeners.yml").getBoolean("fireworklauncher.effect_enabled")) {
                        String effectValue = plugin.getConfig("listeners.yml").getString("fireworklauncher.effect");
                        followEffectTask = (new BukkitRunnable() {
                            public void run() {
                                if (!player.isOnline() || currentFirework.isDead() || !currentFirework.isValid() || !currentFirework.getPassenger().equals(player)) {
                                    currentFirework.remove();
                                    cancel();
                                }
                                player.getWorld().playEffect(player.getLocation(), Effect.valueOf(effectValue), 20);
                            }
                        }).runTaskTimer(plugin, 1L, 1L);
                    }
                }
            }
        }
    }
}
