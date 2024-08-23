package me.emmy.hub.api.menu.button;

import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RefillGlassButton extends Button {

    private Material material;
    private short data;
    private String configPath;

    public RefillGlassButton(Material material, int data, String configPath) {
        this.material = material;
        this.data = (short) data;
        this.configPath = configPath;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = new ItemStack(material, 1, data);
        ItemMeta meta = itemStack.getItemMeta();

        String displayName = Forest.getInstance().getConfigHandler().getConfig("menus.yml").getString(configPath + ".item-title");

        if (displayName != null) {
            meta.setDisplayName(CC.translate(displayName));
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        // Handle the refill glass button click event if needed
    }
}
