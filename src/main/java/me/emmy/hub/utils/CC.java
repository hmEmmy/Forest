package me.emmy.hub.utils;

import me.emmy.hub.Forest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

	public static String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> translate(List<String> lines) {
		List<String> list = new ArrayList<>();

		for (String line : lines) {
			list.add(ChatColor.translateAlternateColorCodes('&', line));
		}

		return list;
	}

	public static List<String> translate(String[] lines) {
		List<String> list = new ArrayList<>();

		for (String line : lines) {
			if (line != null) {
				list.add(ChatColor.translateAlternateColorCodes('&', line));
			}
		}

		return list;
	}

	public static void sendError(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(CC.translate("[CC Util sendError] &c" + message + "!"));
	}

	public static void loadPlugin(long timeTaken) {
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

	public static void disablePlugin() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(translate("&8[&dForest&8] &7> &fDisabled the &dHubCore&f!"));
		Bukkit.getConsoleSender().sendMessage(" ");
	}
}
