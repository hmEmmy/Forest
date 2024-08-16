package me.emmy.hub.feature.pvpmode.command;

import me.emmy.hub.Forest;
import me.emmy.hub.feature.pvpmode.PvPModeRepository;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 20:08
 */
public class JoinPvPModeCommand extends BaseCommand {
    @Override
    @Command(name = "joinpvpmode", aliases = "joinpvp")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (PlayerState.isState(player, PlayerState.FIGHTING)) {
            player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.already-in-pvp-mode")));
            return;
        }

        Forest.getInstance().getPvpModeRepository().addPlayer(player);
        player.sendMessage(CC.translate(Forest.getInstance().getConfig("messages.yml").getString("messages.joined-pvp-mode")));
    }
}
