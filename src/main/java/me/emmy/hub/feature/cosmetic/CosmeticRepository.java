package me.emmy.hub.feature.cosmetic;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.util.ItemBuilder;
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
        cosmetics.add(new Cosmetic("Angry Villager", "forest.cosmetic.angry", "Angry particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.BLAZE_ROD));
        cosmetics.add(new Cosmetic("Snow", "forest.cosmetic.snow", "Snow particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.SNOW_BALL));
        cosmetics.add(new Cosmetic("Snow Storm", "forest.cosmetic.snowstorm", "Snow particles appear behind the player when moving.", CosmeticType.PARTICLE, Material.SNOW));

        cosmetics.add(new Cosmetic("Astronaut", "forest.cosmetic.astronaut", "Astronaut suit.", CosmeticType.HAT, Material.GLASS));
        cosmetics.add(new Cosmetic("Miner", "forest.cosmetic.miner", "Player receives a Helmet made with Diamonds.", CosmeticType.HAT, Material.DIAMOND_HELMET));
        cosmetics.add(new Cosmetic("Pumpkin", "forest.cosmetic.pumpkin", "Pumpkin on your head.", CosmeticType.HAT, Material.PUMPKIN));
        cosmetics.add(new Cosmetic("TNT", "forest.cosmetic.tnt", "TNT on your head.", CosmeticType.HAT, Material.TNT));
        cosmetics.add(new Cosmetic("Hay", "forest.cosmetic.hay", "Hay on your head.", CosmeticType.HAT, Material.HAY_BLOCK));
        cosmetics.add(new Cosmetic("Melon", "forest.cosmetic.melon", "Melon on your head.", CosmeticType.HAT, Material.MELON_BLOCK));
        cosmetics.add(new Cosmetic("Cactus", "forest.cosmetic.cactus", "Cactus on your head.", CosmeticType.HAT, Material.CACTUS));
        cosmetics.add(new Cosmetic("Snow Block", "forest.cosmetic.snowblock", "Snow block on your head.", CosmeticType.HAT, Material.SNOW_BLOCK));

        //cannot have more than 21 cosmetics due to the CosmeticsMenu.
    }

    /**
     * Get a cosmetic by its name.
     *
     * @param name The name of the cosmetic.
     * @return The cosmetic.
     */
    public Cosmetic getCosmetic(String name) {
        for (Cosmetic cosmetic : cosmetics) {
            if (cosmetic.getName().equalsIgnoreCase(name)) {
                return cosmetic;
            }
        }
        return null;
    }

    /**
     * Get a cosmetic by its type.
     *
     * @param type The type of the cosmetic.
     * @return The cosmetic.
     */
    public Cosmetic getCosmetic(CosmeticType type) {
        for (Cosmetic cosmetic : cosmetics) {
            if (cosmetic.getType() == type) {
                return cosmetic;
            }
        }
        return null;
    }

    /**
     * Get all the cosmetics.
     *
     * @return The cosmetics.
     */
    public Set<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    /**
     * Check if a player has a cosmetic.
     *
     * @param player The player to check.
     * @param cosmetic The cosmetic to check.
     * @return If the player has the cosmetic.
     */
    public boolean hasCosmetic(Player player, Cosmetic cosmetic) {
        return player.hasPermission(cosmetic.getPermission());
    }

    /**
     * Check if a player has a cosmetic.
     *
     * @param player The player to check.
     * @return If the player has the cosmetic.
     */
    public boolean hasEquipped(Player player) {
        return equippedCosmetics.containsKey(player);
    }

    /**
     * Equip a cosmetic to a player.
     *
     * @param player The player to equip the cosmetic to.
     * @param cosmetic The cosmetic to equip.
     */
    public void equipCosmetic(Player player, Cosmetic cosmetic) {
        if (hasCosmetic(player, cosmetic)) {
            equippedCosmetics.put(player, cosmetic);
        }

        if (cosmetic.getType() == CosmeticType.HAT) {
            player.getInventory().setHelmet(new ItemBuilder(cosmetic.getMaterial()).name(CC.translate("&d&l" + cosmetic.getName())).build());
        }
    }

    /**
     * Unequip a cosmetic from a player.
     *
     * @param player The player to unequip the cosmetic from.
     */
    public void unEquipCosmetic(Player player) {
        equippedCosmetics.remove(player);

        if (Forest.getInstance().getCosmeticRepository().getCosmetics().stream().anyMatch(cosmetic -> cosmetic.getType() == CosmeticType.HAT)) {
            player.getInventory().setHelmet(null);
        }
    }

    /**
     * Check if a player has a cosmetic equipped.
     *
     * @param player The player to check.
     * @param cosmetic The cosmetic to check.
     * @return If the player has the cosmetic equipped.
     */
    public boolean isEquipped(Player player, Cosmetic cosmetic) {
        return equippedCosmetics.get(player) == cosmetic;
    }
}
