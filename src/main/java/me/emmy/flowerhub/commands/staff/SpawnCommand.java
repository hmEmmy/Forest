package me.emmy.flowerhub.commands.staff;

import me.emmy.flowerhub.FlowerHub;
import me.emmy.flowerhub.utils.CC;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class SpawnCommand extends BaseCommand {

    @Command(name = "spawn")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        Player player = (Player) sender;

        if (!player.hasPermission(FlowerHub.getInstance().getConfig().getString("spawn-command.permission"))) {
            player.sendMessage(CC.translate(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.no-permission")));
        }

        if (FlowerHub.getInstance().getConfig().getBoolean("enable-teleport")) {
            FlowerHub.getInstance().teleportToSpawn(player);
            player.sendMessage(CC.translate(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.teleported-to-spawn")));
        } else {
            player.sendMessage(CC.translate(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.teleport-spawn-disabled")));
        }
    }
}
