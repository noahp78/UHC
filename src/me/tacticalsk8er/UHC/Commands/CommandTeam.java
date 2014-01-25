package me.tacticalsk8er.UHC.Commands;

import me.tacticalsk8er.UHC.Main;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class CommandTeam {

	public CommandTeam(Main plugin, CommandSender sender, String[] args) {
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("join")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("You must be a player to use this command!");
				} else {
					Player p = (Player) sender;
					if (args.length > 2) {
						String team = args[2];
						Team playerTeam = plugin.Team.getTeam(team);
						if (playerTeam == null) {
							if (plugin.Team.getTeams().size() >= plugin.getConfig().getInt("TeamSettings.MaxTeams")) {
								sender.sendMessage("Max number of teams has been reached, try join a team.");
							} else {
								playerTeam = plugin.Team.registerNewTeam(team);
								playerTeam.addPlayer(p);
								sender.sendMessage("You have created and have been added to the " + team + " Team.");
							}
						} else {
							if (playerTeam.getSize() >= plugin.getConfig().getInt("TeamSetting.MaxTeamSize")) {
								sender.sendMessage("Team is full, join of create another team!");
							} else {
								playerTeam.addPlayer(p);
								sender.sendMessage("You have been added to the " + team + " Team");
							}
						}
					} else {
						sender.sendMessage("Invalid Command: Needs more arguments!");
					}
				}
			} else if (args[1].equalsIgnoreCase("leave")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("You must be a plyaer to use this command!");
				} else {
					boolean hasTeam = false;
					Player p = (Player) sender;
					for (Team team : plugin.Team.getTeams()) {
						if (team.hasPlayer(p)) {
							team.removePlayer(p);
							sender.sendMessage("You have been removed from your team!");
							hasTeam = true;
						}
					}
					if (!hasTeam) {
						sender.sendMessage("You are not in a team!");
					}
				}
			} else if (args[1].equalsIgnoreCase("list")) {
				StringBuilder sb = new StringBuilder();
				int max = plugin.getConfig().getInt("TeamSettings.MaxTeamSize");
				sb.append("Teams: ");
				for (Team team : plugin.Team.getTeams()) {
					if (!(team.getName().equalsIgnoreCase("spectators"))) {
						String s = team.getName();
						int size = team.getSize();
						sb.append(s + " " + size + "/" + max + " | ");
					}
				}
				sender.sendMessage(sb.toString());
			} else if (args[1].equalsIgnoreCase("color")) {
				if (args.length > 2) {
					String[] colors = { "Black", "DarkBlue", "DarkGreen", "DarkAqua", "DarkRed", "DarkPurple", "Gold", "Gray", "DarkGray", "Blue", "Green", "Aqua", "Red", "LightPurple", "Yellow", "White" };
					for (String s : colors) {
						if (args[2].equalsIgnoreCase(s)) {
							s = s.toUpperCase();

						}
					}
				} else {
					sender.sendMessage("Invalid Command: Missing Argument \"Color\"");
				}
			}
		} else {
			sender.sendMessage("Invaild Command: Needs more arguments! [usage: [/uhc team list] or [/uhc team join [team]] [or /uhc team leave]");
		}
	}

}
