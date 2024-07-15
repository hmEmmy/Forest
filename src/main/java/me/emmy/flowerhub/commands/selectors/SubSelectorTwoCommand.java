package me.emmy.flowerhub.commands.selectors;

import me.emmy.flowerhub.menu.subselectors.subselectortwo.SubSelectorTwoMenu;
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

public class SubSelectorTwoCommand extends BaseCommand {

    @Command(name = "subselectortwo")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

            Player player = (Player) sender;

            new SubSelectorTwoMenu().openMenu(player);
        }
    }

