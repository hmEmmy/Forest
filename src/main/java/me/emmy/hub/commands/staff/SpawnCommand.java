package me.emmy.hub.commands.staff;

import me.emmy.hub.Forest;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SpawnCommand extends BaseCommand {
    @Override
    @Command(name = "spawn")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!player.hasPermission("forest.command.spawn")) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.no-permission")));
        }

        if (!Forest.getInstance().getConfigHandler().getConfig("listeners.yml").getBoolean("spawn.teleporting")) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.teleport-spawn-disabled")));
            return;
        }

        Forest.getInstance().teleportToSpawn(player);
        player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.teleported-to-spawn")));
    }
}
