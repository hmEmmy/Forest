package me.emmy.hub.menus.subselectors.subselectortwo.command;

import me.emmy.hub.menus.subselectors.subselectortwo.SubSelectorTwoMenu;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SubSelectorTwoCommand extends BaseCommand {
    @Override
    @Command(name = "subselectortwo")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new SubSelectorTwoMenu().openMenu(player);
    }
}

