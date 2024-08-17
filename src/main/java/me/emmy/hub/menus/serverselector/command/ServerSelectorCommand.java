package me.emmy.hub.menus.serverselector.command;

import me.emmy.hub.menus.serverselector.ServerSelectorMenu;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
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