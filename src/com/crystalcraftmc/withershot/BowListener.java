package com.crystalcraftmc.withershot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class BowListener implements Listener, CommandExecutor
{
	Main plugin;
	public BowListener(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public List<String> enabledPlayers = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = (Player) sender;
		
		if (!(sender instanceof Player))
    	{
    		sender.sendMessage("This command can only be run by a player.");
    		return true;
    	}
		
		if (cmd.getName().equalsIgnoreCase("withershot"))
		{
	    	if(args.length != 1)
		    {
	    		p.sendMessage(ChatColor.RED + "You must either enable or disable WitherShot!");
		        return false;
		    }
	    	
	    	else if(args[0].equalsIgnoreCase("enable"))
	    	{
	    		if(p.hasPermission("withershot.fire"))
	    		{
	    			enabledPlayers.add(p.getName());
	    			p.sendMessage(ChatColor.GREEN + "You are now the master of the wither bow!");
	    			return true;
	    		}
	    	}
	    	
	    	else if(args[0].equalsIgnoreCase("disable"))
	    	{
	    		if(p.hasPermission("withershot.fire"))
	    		{
	    			enabledPlayers.remove(p.getName());
	    			p.sendMessage(ChatColor.GREEN + "You have become a normal archer again.");
	    			return true;
	    		}
	    	}
	    	return true;
	    }
	    	
	    else
		{
    		p.sendMessage("You do not have permission to use that command.");
		}
    	return true;
	}
	
	@EventHandler
	public void onShoot(EntityShootBowEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			
			if(enabledPlayers.contains(p.getName()))
			{
				e.setCancelled(true);
				p.launchProjectile(WitherSkull.class).setVelocity(e.getProjectile().getVelocity().multiply(0.4));
			}
		}
	}
}