package me.emmy.hub.commands.selectors;

import me.emmy.hub.feature.serverselector.ServerSelectorMenu;
import me.emmy.hub.utils.command.BaseCommand;
import me.emmy.hub.utils.command.Command;
import me.emmy.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class ServerSelectorCommand extends BaseCommand {
    @Override
    @Command(name = "serverselector")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new ServerSelectorMenu().openMenu(player);
    }
}