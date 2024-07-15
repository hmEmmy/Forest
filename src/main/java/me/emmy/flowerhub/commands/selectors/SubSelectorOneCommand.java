package me.emmy.flowerhub.commands.selectors;

import me.emmy.flowerhub.menu.subselectors.subselectorone.SubSelectorOneMenu;
import me.emmy.flowerhub.utils.command.BaseCommand;
import me.emmy.flowerhub.utils.command.Command;
import me.emmy.flowerhub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class SubSelectorOneCommand extends BaseCommand {

    @Command(name = "subselectorone")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        Player player = (Player) sender;

        new SubSelectorOneMenu().openMenu(player);

    }
}
