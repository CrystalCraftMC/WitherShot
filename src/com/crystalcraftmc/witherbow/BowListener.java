package com.crystalcraftmc.witherbow;

import java.util.ArrayList;
import java.util.List;

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
		// Make the letter 'p' a variable for the command sender (or the player).
		Player p = (Player) sender;
		
		// If the player types "/witherbow [...]"
		if (cmd.getName().equalsIgnoreCase("witherbow"))
		{
			// ...and if the command is ONLY "/witherbow"...
	    	if(args.length != 1)
		    {
		        // ...return the command as incomplete.
		        return false;
		    }
	    	
	    	// ...but if the player types "/witherbow enable"...
	    	else if(args[0].equalsIgnoreCase("enable"))
	    	{
	    		// ...and if they have the following permission...
	    		if(p.hasPermission("witherbow.fire"))
	    		{
	    			// ...but they are not a player in-game...
	    			if (!(sender instanceof Player))
			    	{
	    				// ...do not let the command be run.
			    		sender.sendMessage("This command can only be run by a player.");
			    	}
	    			
	    			// ...but if they are a player, add them to the enabledPlayers list!
	    			enabledPlayers.add(p.getName());
	    		}
	    	}
	    	
	    	// If the player types "/witherbow disable"...
	    	else if(args[0].equalsIgnoreCase("disable"))
	    	{
	    		// ...and if they have the following permission...
	    		if(p.hasPermission("witherbow.fire"))
	    		{
	    			// ...but they are not a player in-game...
	    			if (!(sender instanceof Player))
	    			{
	    				// ...do not let the command be run.
	    				sender.sendMessage("This command can only be run by a player.");
	    			}
	    			
	    			// ...but if they are a player, remove them to the enabledPlayers list!
	    			enabledPlayers.remove(p.getName());
	    		}
	    	}
	    		
	    	// If this has happened, the function will return true. 
	    	return true;
	    }
	    	
	    // Otherwise, if the sender of the command doesn't have the permission...
	    else
		{
	    	// ...do not let the command be run.
    		p.sendMessage("You do not have permission to use that command.");
		}

		// If this hasn't happened, a value of false will be returned.
    	return false;
	}
	
	@EventHandler
	public void onShoot(EntityShootBowEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			
			if(p.hasPermission("witherbow.fire"))
			{
				enabledPlayers.add(p.getName());
				{
					e.setCancelled(true);
					p.launchProjectile(WitherSkull.class).setVelocity(e.getProjectile().getVelocity().multiply(0.5));
				}
			}
		}
	}
}