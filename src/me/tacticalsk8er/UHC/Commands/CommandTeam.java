package me.tacticalsk8er.UHC.Commands;

import me.tacticalsk8er.UHC.Main;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class CommandTeam {

	public CommandTeam(Main plugin, CommandSender sender, String[] args) {
		if (args[1].equalsIgnoreCase("join")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				int Team = 0;
				try {
					Team = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					sender.sendMessage("Invalid Command: Argument 3 needs to be an integer");
				}
				if (Team <= 0) {
					sender.sendMessage("Invalid Command: Team needs to be 1 or higher!");
				} else {
					if (Team <= plugin.getConfig().getInt("TeamSettings.MaxTeams")) {
						sender.sendMessage("Invalid Team Number: You're Team Number is beyond the max allowed Teams");
					} else {
						Team playerTeam = plugin.Teams.get(Team);
						if (playerTeam.getSize() < plugin.getConfig().getInt("TeamSettings.MaxTeamSize")) {
							playerTeam.addPlayer(p);
						} else {
							sender.sendMessage("Team is full try to join another team.");
						}
					}
				}
			} else {
				sender.sendMessage("You must be a player to use this command!");
			}
		}
	}
}
