package me.emmy.flowerhub.commands;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import me.emmy.flowerhub.utils.command.Completer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerHub
 */

public class FlowerHubCommand extends BaseCommand {

    private final FlowerHub plugin = FlowerHub.getInstance();

    @Completer(name = "flowerhub")
    public List<String> flowerhubCompleter(CommandArgs args) {
        List<String> command = new ArrayList<>();
        if (args.length() == 1) {
            command.add("reload");
        }
        return command;
    }

    @Command(name = "flowerhub", aliases = "hubcore", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&b&lೋღ&b&l&m«-------&f&l&m-------&b&l&m-------»&r&b&lღೋ"));
        sender.sendMessage(CC.translate("  &b&l   FlowerHub"));
        sender.sendMessage(CC.translate("      &f┃ Author: &b" + plugin.getDescription().getAuthors().get(0)));
        sender.sendMessage(CC.translate("      &f┃ Version: &b" + plugin.getDescription().getVersion()));
        sender.sendMessage(CC.translate(" "));
        sender.sendMessage(CC.translate("  &b&l   Description:"));
        sender.sendMessage(CC.translate("      &f┃ " + plugin.getDescription().getDescription()));
        sender.sendMessage(CC.translate("&b&lೋღ&b&l&m«-------&f&l&m-------&b&l&m-------»&r&b&lღೋ"));
        sender.sendMessage(" ");
    }
}