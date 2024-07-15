package me.emmy.flowerhub.utils;

import me.emmy.flowerhub.FlowerHub;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by Emmy
 * Project: FlowerHub-main
 * Date: 17/03/2024 - 22:00
 * GitHub: https://github.com/Emmiesa
 */

public class FireworkUtil {

    public static void giveFirework(Player player) {
        ItemStack fireworkItem = new ItemStack(Material.FIREWORK);
        FireworkMeta meta = (FireworkMeta) fireworkItem.getItemMeta();
        if (meta != null) {
            meta.addEffect(FireworkEffect.builder()
                    .withColor(Color.RED, Color.GREEN, Color.BLUE)
                    .withFade(Color.YELLOW)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .flicker(true)
                    .build());
            meta.setPower(2);

            fireworkItem.setItemMeta(meta);

            player.getInventory().addItem(fireworkItem);
        } else {
            FlowerHub.getInstance().getLogger().warning("Failed to create firework item meta.");
        }
    }
}
