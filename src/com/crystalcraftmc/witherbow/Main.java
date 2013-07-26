package com.crystalcraftmc.witherbow;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new BowListener(this), this);
	}
	
	@Override
	public void onDisable()
	{
        // Add a way to clear the the string enabledPlayers?
	}
}
// Test