package me.emmy.hub.feature.cosmetic;

import com.comphenix.protocol.PacketType;
import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.feature.cosmetic.enums.CosmeticType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 16:42
 */
@Getter
@Setter
public class CosmeticRepository {

    private HashSet<Cosmetic> cosmetics = new HashSet<>();
    private HashMap<Player, Cosmetic> equippedCosmetics = new HashMap<>();

    public CosmeticRepository() {
        cosmetics.add(new Cosmetic("Smoke", "forest.cosmetic.smoke", "Smoke particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.COAL));
        cosmetics.add(new Cosmetic("Flame", "forest.cosmetic.flame", "Flame particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.BLAZE_POWDER));
        cosmetics.add(new Cosmetic("Heart", "forest.cosmetic.heart", "Heart particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.REDSTONE));
        cosmetics.add(new Cosmetic("Water", "forest.cosmetic.water", "Water particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.WATER_BUCKET));
        cosmetics.add(new Cosmetic("Cloud", "forest.cosmetic.cloud", "Cloud particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.GHAST_TEAR));
    }

    public Cosmetic getCosmetic(String name) {
        for (Cosmetic cosmetic : cosmetics) {
            if (cosmetic.getName().equalsIgnoreCase(name)) {
                return cosmetic;
            }
        }
        return null;
    }

    public Cosmetic getCosmetic(CosmeticType type) {
        for (Cosmetic cosmetic : cosmetics) {
            if (cosmetic.getType() == type) {
                return cosmetic;
            }
        }
        return null;
    }

    public Set<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    public boolean hasCosmetic(Player player, Cosmetic cosmetic) {
        return player.hasPermission(cosmetic.getPermission());
    }

    public boolean hasEquipped(Player player) {
        return equippedCosmetics.containsKey(player);
    }

    public void equipCosmetic(Player player, Cosmetic cosmetic) {
        if (hasCosmetic(player, cosmetic)) {
            equippedCosmetics.put(player, cosmetic);
        }
    }

    public void unEquipCosmetic(Player player) {
        equippedCosmetics.remove(player);
    }

    public boolean isEquipped(Player player, Cosmetic cosmetic) {
        return equippedCosmetics.get(player) == cosmetic;
    }
}
