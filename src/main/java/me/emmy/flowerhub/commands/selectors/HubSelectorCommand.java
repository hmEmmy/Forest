package me.emmy.flowerhub.commands.selectors;

import me.emmy.flowerhub.menu.hubselector.HubSelectorMenu;
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

public class HubSelectorCommand extends BaseCommand {

    @Command(name = "hubselector")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        Player player = (Player) sender;

        new HubSelectorMenu().openMenu(player);
    }
}