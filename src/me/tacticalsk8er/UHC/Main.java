package me.tacticalsk8er.UHC;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin {

	public ScoreboardManager manager;
	public Scoreboard Team;
	public Team Spectators;
	public List<Team> Teams;

	public static boolean GameStarted = false;
	public static boolean GameCountDown = false;
	public static int PlayerCount = 0;

	@Override
	public void onEnable() {
		getCommand("uhc").setExecutor(new CommandUHC(this));
		manager = Bukkit.getScoreboardManager();
		Team = manager.getNewScoreboard();
		Spectators = Team.registerNewTeam("Spectators");
		for (int i = 1; i <= getConfig().getInt("TeamSettings.MaxTeams"); i++) {
			Team GameTeam = Team.registerNewTeam("1");
			Teams.add(GameTeam);
		}

		getServer().getPluginManager().registerEvents(new GameEvents(this, Spectators), this);
		makeConfig();
	}

	private void makeConfig() {
		getConfig().options().header();
		if (!(getConfig().contains("GameSettings.Raduis")))
			getConfig().set("GameSettings.Raduis", 1000);
		if (!(getConfig().contains("GameSettings.SpreadDistance")))
			getConfig().set("GameSettings.SpreadDistance", 100);
		if (!(getConfig().contains("TeamSettings.MaxTeams")))
			getConfig().set("TeamSettings.MaxTeams", 8);
		if (!(getConfig().contains("TeamSettings.MaxTeamSize")))
			getConfig().set("TeamSettings.MaxTeamSize", 2);
		saveConfig();
	}

}
