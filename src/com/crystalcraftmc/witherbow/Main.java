package com.crystalcraftmc.witherbow;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e){
			// Failed to submit the stats :-(
		}
		
        if (this.getConfig().getBoolean("auto-update"))
        {
        	@SuppressWarnings("unused")
			Updater updater = new Updater(this, "wither-bow", this.getFile(), Updater.UpdateType.DEFAULT, true);
        }
		
		BowListener bl = new BowListener(this);
		
        getCommand("witherbow").setExecutor(bl);
        
        getServer().getPluginManager().registerEvents(bl, this);
	}
	
	@Override
	public void onDisable()
	{
        // Add a way to clear the the string enabledPlayers?
	}
}