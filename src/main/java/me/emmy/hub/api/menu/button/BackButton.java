package me.emmy.hub.api.menu.button;

import lombok.AllArgsConstructor;
import me.emmy.hub.util.ItemBuilder;
import me.emmy.hub.api.menu.Button;
import me.emmy.hub.api.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@AllArgsConstructor
public class BackButton extends Button {

	private Menu back;

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.REDSTONE)
				.name("&c&lBack")
				.lore(Arrays.asList(
						"&cClick here to return to",
						"&cthe previous menu.")
				)
				.build();
	}

	@Override
	public void clicked(Player player, ClickType clickType) {
		Button.playNeutral(player);
		back.openMenu(player);
	}
}
