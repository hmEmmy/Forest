package me.emmy.flowerhub.commands.staff;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class FlowerHubReloadCommand extends BaseCommand {
    @Command(name = "flowerhub.reload", inGameOnly = false, permission = "flowerhub.staff")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        long start = System.currentTimeMillis();

        for (String message : FlowerHub.getInstance().getConfig("messages.yml").getStringList("messages.reload")) {
            sender.sendMessage(CC.translate(message));
        }

        FlowerHub.getInstance().getConfigHandler().reloadConfigs();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        for (String message : FlowerHub.getInstance().getConfig("messages.yml").getStringList("messages.reload-finish")) {
            sender.sendMessage(CC.translate(message.replace("%timetaken%", String.valueOf(timeTaken))));
        }

        Bukkit.getConsoleSender().sendMessage(CC.translate(" &bSuccessfully reloaded all config files in " + timeTaken + "&bms." ));
    }
}
