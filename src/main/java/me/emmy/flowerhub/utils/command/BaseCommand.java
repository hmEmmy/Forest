package me.emmy.flowerhub.utils.command;

import me.emmy.flowerhub.FlowerHub;

public abstract class BaseCommand {

	public FlowerHub main = FlowerHub.getInstance();

	public BaseCommand() {
		main.getCommandFramework().registerCommands(this);
	}

	public abstract void onCommand(CommandArgs command);

}
