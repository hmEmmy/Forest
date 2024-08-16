package me.emmy.hub.feature.hotbar;

import lombok.Getter;
import me.emmy.hub.commands.essential.TogglePlayerVisibilityCommand;
import me.emmy.hub.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Forest
 *  * @date 16/08/2024 - 20:34
 *  */
@Getter
public enum Hotbar {

    PVP_MODE(Material.DIAMOND_SWORD, 0, "joinpvpmode", "&d» PvP Mode « &7(Right Click)", 0,
            Arrays.asList(
                    "",
                    "&7Right click to join the PvP mode!",
                    ""
            )
    ),

    ENDER_BUTT(Material.ENDER_PEARL, 0, "&d» EnderButt « &7(Right Click)", 1,
            Arrays.asList(
                    "",
                    "&7Right click to teleport!",
                    ""
            )
    ),

    SERVER_SELECTOR(Material.COMPASS, 0, "serverselector", "&d» Server Selector « &7(Right Click)", 4,
            Arrays.asList(
                    "",
                    "&7Right click to open the server selector!",
                    ""
            )
    ),

    TOGGLE_PLAYER_VISIBILITY(Material.INK_SACK, 8, "tpv", "&d» Toggle Player Visibility  « &7(Right Click)", 6,
            Arrays.asList(
                    "",
                    "&7Right click to hide players!",
                    ""
            )
    ),

    FIRE_WORK(Material.FIREWORK, 0, "firework", "&d» Firework « &7(Right Click)", 7,
            Arrays.asList(
                    "",
                    "&7Right click to launch yourself into the air!",
                    ""
            )
    ),

    COSMETICS(Material.CHEST, 0, "cosmetics", "&d» Cosmetics « &7(Right Click)", 8,
            Arrays.asList(
                    "",
                    "&7Right click to open the cosmetics menu!",
                    ""
            )
    ),

    /*HUB_SELECTOR(Material.BED, 0, "hubselector", "&d» Hub Selector « &7(Right Click)", 0,
            Arrays.asList(
                    "",
                    "&7Right click to open the hub selector!",
                    ""
            )
    )*/

    ;

    private final Material material;
    private final int durability;
    private final String command;
    private final String name;
    private final int slot;
    private final List<String> lore;

    /**
     * Constructor for hotbar items (Instantiates a new hotbar item)
     *
     * @param material   the material of the hotbar item
     * @param durability the durability of the hotbar item
     * @param command    the command of the hotbar item
     * @param name       the name of the hotbar item
     * @param slot       the slot of the hotbar item
     */
    Hotbar(Material material, int durability, String command, String name, int slot, List<String> lore) {
        this.material = material;
        this.durability = durability;
        this.command = command;
        this.name = name;
        this.slot = slot;
        this.lore = lore;
    }

    /**
     * Constructor for hotbar items without its command (used for enderbutt for example)
     *
     * @param material   the material of the hotbar item
     * @param durability the durability of the hotbar item
     * @param name       the name of the hotbar item
     * @param slot       the slot of the hotbar item
     */
    Hotbar(Material material, int durability, String name, int slot, List<String> lore) {
        this(material, durability, null, name, slot, lore);
    }

    /**
     * Gets the item stack of the hotbar item
     *
     * @return the item stack
     */
    public ItemStack getItem() {
        return new ItemBuilder(material)
                .name(name)
                .durability(durability)
                .build();
    }

    /**
     * Gets the hotbar item from the item stack
     *
     * @param item the item stack to get the hotbar item from
     * @return the hotbar item
     */
    public static Hotbar getItem(ItemStack item) {
        for (Hotbar hotbarItem : values()) {
            if (hotbarItem.getItem().equals(item)) {
                return hotbarItem;
            }
        }
        return null;
    }

    /**
     * Applies the hotbar items to the player's inventory
     *
     * @param player the player to apply the hotbar items to
     */
    public static void applyHotbarItems(Player player) {
        for (Hotbar hotbar : values()) {
            player.getInventory().setItem(hotbar.getSlot(), hotbar.getItem());
        }
    }
}