package me.emmy.hub.commands;

import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import me.emmy.hub.utils.command.Completer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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
        sender.sendMessage(CC.translate("&d&lForest &7(Hub)"));
        sender.sendMessage(CC.translate(" &fAuthor: &d" + Forest.getInstance().getDescription().getAuthors().get(0)));
        sender.sendMessage(CC.translate(" &fVersion: &d" + Forest.getInstance().getDescription().getVersion()));
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate(" &fDescription: &d" + Forest.getInstance().getDescription().getDescription()));
        sender.sendMessage(CC.translate(" &fWebsite: &d" + Forest.getInstance().getDescription().getWebsite()));
        sender.sendMessage(" ");
    }
}