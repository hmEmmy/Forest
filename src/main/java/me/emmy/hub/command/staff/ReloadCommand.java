package me.emmy.hub.command.staff;

import me.emmy.hub.Forest;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class ReloadCommand extends BaseCommand {
    @Override
    @Command(name = "forest.reload", inGameOnly = false, permission = "forest.staff")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        long start = System.currentTimeMillis();

        for (String message : Forest.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("messages.reload")) {
            sender.sendMessage(CC.translate(message));
        }

        Forest.getInstance().getConfigHandler().reloadConfigs();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        for (String message : Forest.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("messages.reload-finish")) {
            sender.sendMessage(CC.translate(message.replace("%timetaken%", String.valueOf(timeTaken))));
        }

        Bukkit.getConsoleSender().sendMessage(CC.translate(" &bSuccessfully reloaded all config files in " + timeTaken + "&bms." ));
    }
}
