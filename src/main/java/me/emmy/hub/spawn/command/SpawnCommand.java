package me.emmy.hub.spawn.command;

import me.emmy.hub.Forest;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.util.CC;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
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

        if (PlayerState.isState(player, PlayerState.FIGHTING)) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.cannot-tp")));
            return;
        }

        if (!player.hasPermission("forest.command.spawn")) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.no-permission")));
        }

        if (!Forest.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("spawn.teleporting")) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.teleport-spawn-disabled")));
            return;
        }

        Forest.getInstance().getSpawnHandler().teleportToLocation(player);
        player.sendMessage(CC.translate(Forest.getInstance().getConfigHandler().getConfig("messages.yml").getString("messages.teleported-to-spawn")));
    }
}
