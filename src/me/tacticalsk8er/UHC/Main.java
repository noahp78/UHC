package me.tacticalsk8er.UHC;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin {

	ScoreboardManager manager;
	Scoreboard Teams;
	Team Spectators;

	public static boolean GameStarted = false;
	public static boolean GameCountDown = false;
	public static int PlayerCount = 0;

	@Override
	public void onEnable() {
		getCommand("uhc").setExecutor(new CommandUHC(this));
		manager = Bukkit.getScoreboardManager();
		Teams = manager.getNewScoreboard();
		Spectators = Teams.registerNewTeam("Spectators");
		getServer().getPluginManager().registerEvents(new GameEvents(this, Spectators), this);
		makeConfig();
	}

	private void makeConfig() {
		getConfig().options().header();
		if (!(getConfig().contains("GameSettings.Raduis")))
			getConfig().set("GameSettings.Raduis", 1000);
		if (!(getConfig().contains("GameSettings.SpreadDistance")))
			getConfig().set("GameSettings.SpreadDistance", 100);
		saveConfig();
	}

}
