package me.emmy.hub.util;

import lombok.experimental.UtilityClass;
import me.emmy.hub.Forest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Forest
 * @date 27/02/2024 - 14:55
 */
@UtilityClass
public class CC {

	/**
	 * Translate a string.
	 *
	 * @param string The string to translate.
	 * @return The translated string.
	 */
	public String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	/**
	 * Translate a list of strings.
	 *
	 * @param lines The list of strings to translate.
	 * @return The translated list of strings.
	 */
	public List<String> translate(List<String> lines) {
		List<String> list = new ArrayList<>();

		for (String line : lines) {
			list.add(ChatColor.translateAlternateColorCodes('&', line));
		}

		return list;
	}

	/**
	 * Translate a list of strings.
	 *
	 * @param lines The list of strings to translate.
	 * @return The translated list of strings.
	 */
	public List<String> translate(String[] lines) {
		List<String> list = new ArrayList<>();

		for (String line : lines) {
			if (line != null) {
				list.add(ChatColor.translateAlternateColorCodes('&', line));
			}
		}

		return list;
	}

	/**
	 * Send a message to the console when the plugin is enabled.
	 *
	 * @param message The message to send.
	 */
	public void sendError(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(CC.translate("[CC Util sendError] &c" + message + "!"));
	}

	/**
	 * Send a message to the console when the plugin is enabled.
	 */
	public void sendEnableMessage(long timeTaken) {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(translate("&d&l" + Forest.getInstance().getDescription().getName()));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Author: &d" + Forest.getInstance().getDescription().getAuthors().get(0)));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Version: &d" + Forest.getInstance().getDescription().getVersion()));
		Bukkit.getConsoleSender().sendMessage(translate(" "));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Link: &d" + Forest.getInstance().getDescription().getWebsite()));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Description: &d" + Forest.getInstance().getDescription().getDescription()));
		Bukkit.getConsoleSender().sendMessage(translate(" "));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Load time: &d" + (timeTaken) + " &dms"));
		Bukkit.getConsoleSender().sendMessage(" ");
	}

	/**
	 * Send a message to the console when the plugin is enabled.
	 */
	public void sendDisableMessage() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(translate("&8[&dForest&8] &7> &fDisabled the &dHubCore&f!"));
		Bukkit.getConsoleSender().sendMessage(" ");
	}
}
