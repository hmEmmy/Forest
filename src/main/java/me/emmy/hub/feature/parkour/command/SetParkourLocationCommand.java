package me.emmy.hub.feature.parkour.command;

import me.emmy.hub.Forest;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import me.emmy.hub.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 21/08/2024 - 19:30
 */
public class SetParkourLocationCommand extends BaseCommand {
    @Override
    @Command(name = "setparkourlocation", permission = "forest.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /setparkourlocation <start/end>"));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                Forest.getInstance().getParkourHandler().setStartLocation(player.getLocation());
                player.sendMessage(CC.translate("&aYou have set the start location for the parkour."));
                break;
            case "end":
                Forest.getInstance().getParkourHandler().setEndLocation(player.getLocation());
                player.sendMessage(CC.translate("&aYou have set the end location for the parkour."));
                break;
        }
    }
}
