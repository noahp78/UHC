package me.tacticalsk8er.UHC;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable(){
		getCommand("uhc").setExecutor(new CommandUHC(this));
	}
	
}
