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

public class SetSpawnCommand extends BaseCommand {
    @Command(name = "setspawn", permission = "flowerhub.staff")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
            Player player = (Player) sender;
            boolean enableSpawnTeleport = FlowerHub.getInstance().getConfig().getBoolean("enableSpawnTeleport", true);
            FlowerHub.getInstance().setLocation(player.getLocation());

            player.sendMessage(CC.translate(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.spawn-set")));

        if (!enableSpawnTeleport) {
            player.sendMessage(CC.translate(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.spawn-disabled")));
            System.out.println(" ");
            System.out.println(FlowerHub.getInstance().getConfig("messages.yml").getString("messages.spawn-disabled-console"));
            System.out.println(" ");
        }
    }
}