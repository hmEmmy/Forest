package me.emmy.hub.command;

import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class ForestCommand extends BaseCommand {
    @Override
    @Command(name = "Forest", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&b&lForest &7(Hub)"));
        sender.sendMessage(CC.translate(" &fAuthor: &b" + Forest.getInstance().getDescription().getAuthors().get(0)));
        sender.sendMessage(CC.translate(" &fVersion: &b" + Forest.getInstance().getDescription().getVersion()));
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate(" &fDescription: &b" + Forest.getInstance().getDescription().getDescription()));
        sender.sendMessage(CC.translate(" &fWebsite: &b" + Forest.getInstance().getDescription().getWebsite()));
        sender.sendMessage(" ");
    }
}