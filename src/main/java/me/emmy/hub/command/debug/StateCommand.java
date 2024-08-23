package me.emmy.hub.command.debug;

import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import me.emmy.hub.player.PlayerState;
import me.emmy.hub.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 23/08/2024 - 16:01
 */
public class StateCommand extends BaseCommand {
    @Override
    @Command(name = "state", permission = "forest.command.state")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        String state = PlayerState.getStateName(player);
        player.sendMessage(CC.translate("&7Your current state is: &c" + state));

    }
}
