package me.tacticalsk8er.UHC;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin{
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static boolean GameStarted = false;
	public static int PlayerCount = 0;
	
	@Override
	public void onEnable(){
		getCommand("uhc").setExecutor(new CommandUHC(this));
		makeConfig();
	}
	
	private void makeConfig(){
		getConfig().options().header();
	}
	
	public Team setupSpectatorTeam(){
		Scoreboard spectatorsb = manager.getNewScoreboard();
		Team spectator = spectatorsb.registerNewTeam("Spectators");
		spectator.setAllowFriendlyFire(false);
		spectator.setCanSeeFriendlyInvisibles(true);
		return spectator;
	}
	@EventHandler
	public void OnLogin(PlayerJoinEvent event){
	//Kick player on join if the game has started...
		if (GameStarted){
	
			event.setJoinMessage("");
			event.getPlayer().kickPlayer("Game In Progress - Try again later");
			
		}
	}
	
	}
	
