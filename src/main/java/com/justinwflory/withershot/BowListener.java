/*
 * Copyright 2015 Justin W. Flory
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.justinwflory.withershot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.ArrayList;
import java.util.List;

public class BowListener implements Listener, CommandExecutor {
    public List<String> enabledPlayers = new ArrayList<String>();
    Main plugin;

    public BowListener(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("ws")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "You must either enable or disable WitherShot!");
                return false;
            } else if (args[0].equalsIgnoreCase("enable") && p.hasPermission("withershot.fire")) {
                enabledPlayers.add(p.getName());
                p.sendMessage(ChatColor.DARK_PURPLE + "You are now the master of the wither bow!");
            } else if (args[0].equalsIgnoreCase("disable") && p.hasPermission("withershot.fire")) {
                enabledPlayers.remove(p.getName());
                p.sendMessage(ChatColor.GREEN + "You have become a normal archer again.");
            } else if (args[0].equalsIgnoreCase("reset") && p.hasPermission("withershot.reset")) {
                enabledPlayers.clear();
                p.sendMessage(ChatColor.GREEN + "The power of the wither has been extinguished for all.");
            } else {
                p.sendMessage(ChatColor.RED + "You entered an invalid command!");
                return false;
            }
        } else {
            p.sendMessage(ChatColor.RED + "You entered an invalid command!");
            return false;
        }
        return true;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (enabledPlayers.contains(p.getName())) {
                e.setCancelled(true);
                p.launchProjectile(WitherSkull.class).setVelocity(e.getProjectile().getVelocity().multiply(0.3));
            }
        }
    }
}