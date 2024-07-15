package me.emmy.flowerhub.menu.subselectors.subselectortwo;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.menu.Button;
import me.emmy.flowerhub.utils.menu.Menu;
import me.emmy.flowerhub.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class SubSelectorTwoMenu extends Menu {

	private final RefillGlassButton refillGlassButton;

	public SubSelectorTwoMenu() {
		this.refillGlassButton = new RefillGlassButton(
				Material.STAINED_GLASS_PANE,
				FlowerHub.getInstance().getConfig("menus.yml").getInt("menus.subselectortwo.refill-glass.data", 15),
				"menus.subselectortwo.refill-glass"
		);
	}

	@Override
	public String getTitle(Player player) {
		return CC.translate(FlowerHub.getInstance().getConfig("menus.yml").getString("menus.subselectortwo.title"));
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		ConfigurationSection serversSection = FlowerHub.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectortwo.servers");

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

					buttons.put(slot, new SubSelectorTwoButton(material, (short) data, name, lore, serverSection.getString("command")));
				}
			}
		}

		// Add refill glass button
		ConfigurationSection refillGlassSection = FlowerHub.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectortwo.refill-glass");
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
		ConfigurationSection menuSection = FlowerHub.getInstance().getConfig("menus.yml").getConfigurationSection("menus.subselectortwo");

		if (menuSection != null && menuSection.contains("size")) {
			return menuSection.getInt("size", 9 * 3);
		}

		return 9 * 3;
	}
}