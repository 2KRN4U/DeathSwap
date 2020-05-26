package com.tsukrn.DeathSwap;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;


public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		//startup
	}
	
	@Override
	public void onDisable() {
		//shutdown
	}
	int i = 0; //keep track of time
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("deathswap")) {
			if (args.length <= 1 || args.length > 2) {
				Player player = (Player) sender;
				player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Enter two players.");
				
				return true;
			}
			
			if (args.length == 2) {
				String argument1 = args[0];
				String argument2 = args[1];
	
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
					@Override
					public void run() {
						if (i == 1)
							Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in 2 mins");
						if(i > 110 && i < 120) {
							Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in " + (120 - i));
						}
						if (i == 120) {
							Player player1 = Bukkit.getPlayer(argument1);
							Player player2 = Bukkit.getPlayer(argument2);
							Location play1 = player1.getLocation();
							Location play2 = player2.getLocation();
							player1.teleport(play2);
							player2.teleport(play1);
							i = 0;
						}
						i++;
						}
					}, 0, 20);


				}
				
				
			}
		return false;
		
	}
}
