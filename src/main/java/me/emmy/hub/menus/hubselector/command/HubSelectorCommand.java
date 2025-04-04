package me.emmy.hub.menus.hubselector.command;

import me.emmy.hub.menus.hubselector.HubSelectorMenu;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class HubSelectorCommand extends BaseCommand {
    @Override
    @Command(name = "hubselector")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new HubSelectorMenu().openMenu(player);
    }
}