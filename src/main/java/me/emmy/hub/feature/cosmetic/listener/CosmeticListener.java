package me.emmy.hub.feature.cosmetic.listener;

import me.emmy.hub.Forest;
import me.emmy.hub.feature.cosmetic.Cosmetic;
import me.emmy.hub.feature.cosmetic.CosmeticRepository;
import me.emmy.hub.utils.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 17:18
 */
public class CosmeticListener implements Listener {

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        CosmeticRepository cosmeticRepository = Forest.getInstance().getCosmeticRepository();

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Smoke"))) {
            ParticleEffect.SMOKE_NORMAL.display(0.5F, 0.5F, 0.5F, 0.1F, 10, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Flame"))) {
            ParticleEffect.FLAME.display(0.5F, 0.5F, 0.5F, 0.1F, 10, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Heart"))) {
            ParticleEffect.HEART.display(0.5F, 0.5F, 0.5F, 0.1F, 1, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Water"))) {
            ParticleEffect.WATER_SPLASH.display(0.5F, 0.5F, 0.5F, 0.1F, 5, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Cloud"))) {
            ParticleEffect.CLOUD.display(0.5F, 0.5F, 0.5F, 0.1F, 1, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Angry Villager"))) {
            ParticleEffect.VILLAGER_ANGRY.display(0.5F, 0.5F, 0.5F, 0.1F, 1, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Snow"))) {
            ParticleEffect.SNOWBALL.display(0.5F, 0.5F, 0.5F, 0.1F, 3, player.getLocation(), 100);
        }

        if (cosmeticRepository.isEquipped(player, cosmeticRepository.getCosmetic("Snow Storm"))) {
            ParticleEffect.SNOW_SHOVEL.display(0.5F, 0.5F, 0.5F, 0.1F, 10, player.getLocation(), 100);
        }
    }
}
