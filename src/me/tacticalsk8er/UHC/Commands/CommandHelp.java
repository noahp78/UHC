package me.tacticalsk8er.UHC.Commands;

import org.bukkit.command.CommandSender;

/*
 * This class will return the help page to the user who asked for it.
 * This command accepts you argument and that argument is the page number.
 */

public class CommandHelp {
	
	CommandSender sender;
	String[] args;
	
	public CommandHelp(CommandSender sender, String[] args){
		this.sender = sender;
		this.args = args;
	}
	
	
	
}
