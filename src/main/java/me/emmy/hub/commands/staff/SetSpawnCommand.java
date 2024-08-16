package me.emmy.hub.commands.staff;

import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SetSpawnCommand extends BaseCommand {
    @Override
    @Command(name = "setspawn", permission = "forest.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        boolean enableSpawnTeleport = Forest.getInstance().getConfig().getBoolean("enableSpawnTeleport", true);

        Forest.getInstance().setLocation(player.getLocation());
        player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.spawn-set")));

        if (!enableSpawnTeleport) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.spawn-disabled")));
            System.out.println(" ");
            System.out.println(Forest.getInstance().getConfig("messages.yml").getString("messages.spawn-disabled-console"));
            System.out.println(" ");
        }
    }
}