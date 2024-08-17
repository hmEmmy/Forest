package me.emmy.hub.feature.pvpmode.command;

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
 * @date 16/08/2024 - 20:10
 */
public class LeavePvPModeCommand extends BaseCommand {
    @Override
    @Command(name = "leavepvpmode", aliases = {"leavepvp", "leaveffa"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (!PlayerState.isState(player, PlayerState.FIGHTING)) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.not-in-pvp-mode")));
            return;
        }

        Forest.getInstance().getPvpModeRepository().removePlayer(player);
        player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.left-pvp-mode")));
    }
}
