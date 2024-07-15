package me.emmy.flowerhub.commands.socials;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerHub
 */

public class LinksCommand extends BaseCommand {
    @Command(name = "links", aliases = "listlinks", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&b&lೋღ&b&l&m«-------&f&l&m---------------------&b&l&m-------»&r&b&lღೋ"));
        sender.sendMessage(CC.translate("  &b&lA list of socials we're using:"));

        FileConfiguration config = FlowerHub.getInstance().getConfig("messages.yml");
        Map<String, String> links = getStringStringMap();

        links.forEach((feature, messagePath) -> {
            if (config.getBoolean(feature + ".enabled")) {
                sender.sendMessage(CC.translate("   &f&l┃  " + config.getString(messagePath)));
            }
        });

        sender.sendMessage(CC.translate("&b&lೋღ&b&l&m«-------&f&l&m---------------------&b&l&m-------»&r&b&lღೋ"));
        sender.sendMessage(" ");
    }

    private static Map<String, String> getStringStringMap() {
        Map<String, String> links = new LinkedHashMap<>();
        links.put("links.discord", "links.discord.enabled_message");
        links.put("links.store", "links.store.enabled_message");
        links.put("links.tiktok", "links.tiktok.enabled_message");
        links.put("links.twitter", "links.twitter.enabled_message");
        links.put("links.website", "links.website.enabled_message");
        links.put("links.youtube", "links.youtube.enabled_message");
        return links;
    }
}