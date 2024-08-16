package me.emmy.hub.feature.cosmetic;

import lombok.Getter;
import lombok.Setter;
import me.emmy.hub.feature.cosmetic.enums.CosmeticType;
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

    public Cosmetic(String name, String permission, String description, CosmeticType type, Material material) {
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.type = type;
        this.material = material;
    }
}
