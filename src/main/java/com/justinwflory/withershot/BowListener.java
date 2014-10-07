/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Justin W. Flory
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

        if (cmd.getName().equalsIgnoreCase("withershot")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "You must either enable or disable WitherShot!");
                return false;
            } else if (p.hasPermission("withershot.fire")) {
                if (args[0].equalsIgnoreCase("enable")) {
                    enabledPlayers.add(p.getName());
                    p.sendMessage(ChatColor.GREEN + "You are now the master of the wither bow!");
                } else if (args[0].equalsIgnoreCase("disable")) {
                    enabledPlayers.remove(p.getName());
                    p.sendMessage(ChatColor.GREEN + "You have become a normal archer again.");
                }
            } else if (cmd.getName().equalsIgnoreCase("reset") && p.hasPermission("withershot.clear")) {
                enabledPlayers.clear();
                p.sendMessage(ChatColor.GREEN + "The power of the wither has been extinguished... for now.");
            } else {
                p.sendMessage("You do not have permission to use that command.");
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
                p.launchProjectile(WitherSkull.class).setVelocity(e.getProjectile().getVelocity().multiply(0.4));
            }
        }
    }
}