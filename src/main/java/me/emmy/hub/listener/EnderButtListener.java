package me.emmy.hub.listener;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class EnderButtListener implements Listener {

    private BukkitTask followEffectTask;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();

        if (PlayerState.isState(event.getPlayer(), PlayerState.SPAWN)) {
            if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("enderbutt.enabled", true)) {
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
                        if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getString("enderbutt.type").equals("FOLLOW")) {
                            event.setUseItemInHand(Event.Result.DENY);
                            event.setUseInteractedBlock(Event.Result.DENY);

                            player.setVelocity(player.getLocation().getDirection().normalize().multiply(2.5F));

                            if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("enderbutt.sound_enabled")) {
                                String sound = Forest.getInstance().getConfigHandler().getConfig("settings.yml").getString("enderbutt.sound");
                                player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                            }

                            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.CREATIVE) {
                                player.updateInventory();
                            }

                        } else if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getString("enderbutt.type").equals("RIDE")) {
                            event.setCancelled(true);

                            if (player.isInsideVehicle()) {
                                player.getVehicle().remove();
                            }

                            event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
                            event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                            EnderPearl pearl = player.launchProjectile(EnderPearl.class);
                            pearl.setPassenger(player);
                            pearl.setVelocity(player.getLocation().getDirection().normalize().multiply(1.5F));
                            player.spigot().setCollidesWithEntities(false);

                            if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("enderbutt.sound_enabled")) {
                                String sound = Forest.getInstance().getConfigHandler().getConfig("settings.yml").getString("enderbutt.sound");
                                player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
                            }

                            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.CREATIVE) {
                                player.updateInventory();
                            }
                            if (Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("enderbutt.effect-ride.enable")) {
                                String effectValue = Forest.getInstance().getConfigHandler().getConfig("settings.yml").getString("enderbutt.effect-ride.effect");
                                followEffectTask = (new BukkitRunnable() {
                                    public void run() {
                                        if (!player.isOnline() || pearl.isDead() || pearl.isOnGround() || !pearl.isValid() || pearl.getPassenger() == null || !pearl.getPassenger().equals(player)) {
                                            pearl.remove();
                                            cancel();
                                        }
                                        player.getLocation().getWorld().playEffect(player.getLocation(), Effect.valueOf(effectValue), 20);
                                    }

                                }).runTaskTimer(Forest.getInstance(), 1L, 1L);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        event.getDismounted().remove();

        if (event.getDismounted() instanceof EnderPearl) {
            if (followEffectTask != null) {
                followEffectTask.cancel();
            }
        }

    }
}
