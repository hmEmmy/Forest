package me.emmy.hub.menus.subselectors.subselectorone;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.menu.Button;
import me.emmy.hub.api.menu.Menu;
import me.emmy.hub.api.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SubSelectorOneMenu extends Menu {

	private final RefillGlassButton refillGlassButton;

	public SubSelectorOneMenu() {
		this.refillGlassButton = new RefillGlassButton(
				Material.STAINED_GLASS_PANE,
				Forest.getInstance().getConfig("menus.yml").getInt("menus.subselectorone.refill-glass.data", 15),
				"menus.subselectorone.refill-glass"
		);
	}

	@Override
	public String getTitle(Player player) {
		return CC.translate(Forest.getInstance().getConfig("menus.yml").getString("menus.subselectorone.title"));
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		ConfigurationSection serversSection = Forest.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectorone.servers");

		if (serversSection != null) {
			for (String serverKey : serversSection.getKeys(false)) {
				ConfigurationSection serverSection = serversSection.getConfigurationSection(serverKey);

				if (serverSection != null) {
					int slot = serverSection.getInt("slot", 0);
					Material materialType = Material.matchMaterial(serverSection.getString("material", "STONE"));
					String name = CC.translate(serverSection.getString("name", "&c&lInvalid Server"));
					List<String> lore = serverSection.getStringList("lore").stream()
							.map(line -> CC.translate(PlaceholderAPI.setPlaceholders(player, line)))
							.collect(Collectors.toList());
					int data = serverSection.getInt("data", 0);
					Material material = new MaterialData(materialType, (byte) data).toItemStack().getType();

					buttons.put(slot, new SubSelectorOneButton(material, (short) data, name, lore, serverSection.getString("command")));
				}
			}
		}

		// Add refill glass button
		ConfigurationSection refillGlassSection = Forest.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectorone.refill-glass");
		if (refillGlassSection != null && refillGlassSection.getBoolean("enabled", true)) {
			List<String> refillSlots = refillGlassSection.getStringList("slots");
			for (String refillSlot : refillSlots) {
				int slot = Integer.parseInt(refillSlot);
				buttons.put(slot, refillGlassButton);
			}
		}

		return buttons;
	}

	@Override
	public int getSize() {
		ConfigurationSection menuSection = Forest.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectorone");

		if (menuSection != null && menuSection.contains("size")) {
			return menuSection.getInt("size", 9 * 3);
		}

		return 9 * 3;
	}
}
