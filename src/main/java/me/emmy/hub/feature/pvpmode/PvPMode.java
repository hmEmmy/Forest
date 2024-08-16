package me.emmy.hub.feature.pvpmode;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 19:51
 */
@Getter
@Setter
public class PvPMode {
    private Location spawn;
    private ItemStack[] inventory;
    private ItemStack[] armor;

    /**
     * PvPMode constructor
     *
     * @param spawn Location
     * @param inventory ItemStack[]
     * @param armor ItemStack[]
     */
    public PvPMode(Location spawn, ItemStack[] inventory, ItemStack[] armor) {
        this.spawn = spawn;
        this.inventory = inventory;
        this.armor = armor;
    }
}
