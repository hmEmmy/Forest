package me.emmy.flowerhub.utils;

import me.emmy.flowerhub.FlowerHub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

	public static String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> translate(List<String> lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
		}

		return toReturn;
	}

	public static List<String> translate(String[] lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			if (line != null) {
				toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
			}
		}

		return toReturn;
	}

	public static void sendError(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(CC.translate("[CC Util sendError] &c" + message + "!"));  //In Player Manager class!
	}

	public static void loadPlugin(long timeTaken) {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(translate("&8&m-----------------------------------------------"));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Plugin: &b" + FlowerHub.getInstance().getDescription().getName()));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Author: &b" + FlowerHub.getInstance().getDescription().getAuthors().get(0)));
		Bukkit.getConsoleSender().sendMessage(translate(" "));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Version: &b" + FlowerHub.getInstance().getDescription().getVersion()));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Link: &b" + FlowerHub.getInstance().getDescription().getWebsite()));
		Bukkit.getConsoleSender().sendMessage(translate(" "));
		Bukkit.getConsoleSender().sendMessage(translate(" &f| Load time: &b" + (timeTaken) + " &bms"));
		Bukkit.getConsoleSender().sendMessage(translate("&8&m-----------------------------------------------"));
		Bukkit.getConsoleSender().sendMessage(" ");
	}

	public static void disablePlugin() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage(translate("&8[&bFlowerHub&8] &7> &fDisabled the &bHubCore&f!"));
		Bukkit.getConsoleSender().sendMessage(" ");
	}
}
