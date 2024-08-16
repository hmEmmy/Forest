package me.emmy.hub.feature.menus.subselectors.subselectortwo;

import lombok.RequiredArgsConstructor;
import me.emmy.hub.utils.ItemBuilder;
import me.emmy.hub.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
@RequiredArgsConstructor
public class SubSelectorTwoButton extends Button {

	private Material material;
	private short data;
	private String displayName;
	private List<String> lore;
	private String command;

	public SubSelectorTwoButton(Material material, short data, String displayName, List<String> lore, String command) {
		this.material = material;
		this.data = data;
		this.displayName = displayName;
		this.lore = lore;
		this.command = command;
	}

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack item = new ItemStack(material);
		item.setDurability(data);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return new ItemBuilder(item).hideMeta().build();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
		if (clickType == ClickType.NUMBER_KEY || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
			new SubSelectorTwoMenu().openMenu(player);
		}

		if (clickType != ClickType.LEFT) {
			return;
		}

		if (command == null) {
			return;
		}

		player.performCommand(command);
	}
}
