package me.emmy.hub.feature.cosmetic;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 16:41
 */
@Getter
@Setter
public class Cosmetic {
    private String name;
    private String permission;
    private String description;
    private CosmeticType type;
    private Material material;

    /**
     * @param name        The name of the cosmetic.
     * @param permission  The permission required to use the cosmetic.
     * @param description The description of the cosmetic.
     * @param type        The type of the cosmetic.
     * @param material    The material of the cosmetic.
     */
    public Cosmetic(String name, String permission, String description, CosmeticType type, Material material) {
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.type = type;
        this.material = material;
    }
}
