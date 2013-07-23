package com.crystalcraftmc.witherbow;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
        // TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("WitherBow has been enabled!");
		
		getServer().getPluginManager().registerEvents(new BowListener(this), this);
	}
	
	@Override
	public void onDisable()
	{
        // TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("WitherBow has been enabled!");
	}
}
