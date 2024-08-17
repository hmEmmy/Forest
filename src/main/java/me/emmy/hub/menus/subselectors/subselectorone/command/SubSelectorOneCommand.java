package me.emmy.hub.menus.subselectors.subselectorone.command;

import me.emmy.hub.menus.subselectors.subselectorone.SubSelectorOneMenu;
import me.emmy.hub.api.command.BaseCommand;
import me.emmy.hub.api.command.Command;
import me.emmy.hub.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Forest
 * @date 14/08/2024
 */
public class SubSelectorOneCommand extends BaseCommand {
    @Override
    @Command(name = "subselectorone")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        new SubSelectorOneMenu().openMenu(player);
    }
}
