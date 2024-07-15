package me.emmy.flowerhub.commands.socials;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class TikTokCommand extends BaseCommand {

    @Command(name = "tiktok", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        String commandName = "tiktok";

        boolean enableCommand = FlowerHub.getInstance().getConfig("messages.yml").getBoolean("links." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerHub.getInstance().getConfig("messages.yml").getString("links." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", "TikTok");

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerHub.getInstance().getConfig("messages.yml").getString("links." + commandName + ".enabled_message");
            sender.sendMessage(CC.translate(link));
        }
    }
}