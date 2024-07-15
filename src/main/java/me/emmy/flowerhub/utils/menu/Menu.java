package me.emmy.flowerhub.utils.menu;

import lombok.Getter;
import lombok.Setter;
import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Menu {

	public static Map<String, Menu> currentlyOpenedMenus = new HashMap<>();

	@Getter
	protected FlowerHub ricardo = FlowerHub.getInstance();
	private Map<Integer, Button> buttons = new HashMap<>();
	private boolean autoUpdate = false;
	private boolean updateAfterClick = true;
	private boolean closedByMenu = false;
	private boolean placeholder = false;
	private Button placeholderButton = Button.placeholder(Material.STAINED_GLASS_PANE, (byte) 15, " ");

	private ItemStack createItemStack(Player player, Button button) {
		ItemStack item = button.getButtonItem(player);

		if (item.getType() != Material.SKULL_ITEM) {
			ItemMeta meta = item.getItemMeta();

			if (meta != null && meta.hasDisplayName()) {
				meta.setDisplayName(meta.getDisplayName() + "§b§c§d§e");
			}

			item.setItemMeta(meta);
		}

		return item;
	}

	/**
	 *
	 * I Edited the openMenu void so that it does not close the previously opened menu.
	 * It's just smoother - Emmy
	 *
	 */

	public void openMenu(final Player player) {
		this.buttons = this.getButtons(player);

		Inventory inventory;
		int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
		String title = CC.translate(this.getTitle(player));

		if (title.length() > 32) {
			title = title.substring(0, 32);
		}

		inventory = Bukkit.createInventory(player, size, title);

		inventory.setContents(new ItemStack[inventory.getSize()]);

		for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
			inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
		}

		if (this.isPlaceholder()) {
			for (int index = 0; index < size; index++) {
				if (this.buttons.get(index) == null) {
					this.buttons.put(index, this.placeholderButton);
					inventory.setItem(index, this.placeholderButton.getButtonItem(player));
				}
			}
		}

		player.openInventory(inventory);

		currentlyOpenedMenus.put(player.getName(), this);

		this.onOpen(player);
	}

	public int size(Map<Integer, Button> buttons) {
		int highest = 0;

		for (int buttonValue : buttons.keySet()) {
			if (buttonValue > highest) {
				highest = buttonValue;
			}
		}

		return (int) (Math.ceil((highest + 1) / 9D) * 9D);
	}

	public int getSize() {
		return -1;
	}

	public int getSlot(int x, int y) {
		return ((9 * y) + x);
	}

	public abstract String getTitle(Player player);

	public abstract Map<Integer, Button> getButtons(Player player);

	public void onOpen(Player player) {
	}

	public void onClose(Player player) {
	}

}
