package com.crystalcraftmc.witherbow;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
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