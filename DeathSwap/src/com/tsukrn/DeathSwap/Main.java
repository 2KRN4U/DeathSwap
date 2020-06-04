package com.tsukrn.DeathSwap;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;



public class Main extends JavaPlugin {


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
    int i = 0; //keep track of time
    int newLength = 0;
    String[] playerArgs = new String[0];
    Boolean gameOver = false;
    Boolean fireResist = false;
    Boolean haste = false;
    Boolean resist = false;
    Boolean hunger = false;
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("deatheffect")) {
        	if (args.length > 1) {
        		if (args[0].toLowerCase().equals("fireresist")) {
        			if (args[1].toLowerCase().equals("true")) {
        				fireResist = true;
        				Bukkit.broadcastMessage("Fire Reistance on");
        			}
        			if (args[1].toLowerCase().equals("false")) {
        				fireResist = false;
        				Bukkit.broadcastMessage("Fire Resistance off");
        			}
        		} else if (args[0].toLowerCase().equals("haste")) {
        			if (args[1].toLowerCase().equals("true")) {
        				haste = true;
        				Bukkit.broadcastMessage("Haste on");
        			}
        			if (args[1].toLowerCase().equals("false")) {
        				haste = false;
        				Bukkit.broadcastMessage("Haste off");
        			}
        		} else if (args[0].toLowerCase().equals("resist")) {
        			if (args[1].toLowerCase().equals("true")) {
        				resist = true;
        				Bukkit.broadcastMessage("Resistance on");
        			}
        			if (args[1].toLowerCase().equals("false")) {
        				resist = false;
        				Bukkit.broadcastMessage("Resistance off");
        			}
        		} else if (args[0].toLowerCase().equals("hunger")) {
        			if (args[1].equals("true")) {
        				hunger = true;
        				Bukkit.broadcastMessage("Saturation on");
        			}
        			if (args[1].equals("false")) {
        				hunger = false;
        				Bukkit.broadcastMessage("Saturation off");
        			}
        		} else if (args[0].toLowerCase().equals("all")) {
        			if (args[1].toLowerCase().equals("true")) {
        				fireResist = true;
        				haste = true;
        				resist = true;
        				hunger = true;
        				Bukkit.broadcastMessage("All effects on");
        			}
        			if (args[1].toLowerCase().equals("false")) {
        				fireResist = false;
        				haste = false;
        				resist = false;
        				hunger = false;
        				Bukkit.broadcastMessage("All effects off");
        			}
        		} else {
            		Bukkit.broadcastMessage("Usage: /deatheffect [effect] [true/false]\nEffects: fireResist (inf)\nhaste (first round + 30 seconds)\nresist (Resistance - first round + 30 seconds)\nhunger (Saturation - inf)\nall (enables all effects)");
            	}
        	} else {
        		Bukkit.broadcastMessage("Usage: /deatheffect [effect] [true/false]\nEffects: fireResist (inf)\nhaste (first round + 30 seconds)\nresist (Resistance - first round + 30 seconds)\nhunger (Saturation - inf)\nall (enables all effects)");
        	}
        }
    	
    	if (label.equalsIgnoreCase("deathswap")) {
            if (args.length > 2) {
                int time = Integer.parseInt(args[(args.length - 1)]);
                String[] arguments = new String[(args.length - 1)];
                playerArgs = arguments.clone();
                Player[] amtOfPlayers = new Player[(args.length - 1)];
                Location[] playerLocations = new Location[(args.length - 1)];
                for (int x = 0; x < (args.length - 1); x++) {
                    playerArgs[x] = args[x];
                    amtOfPlayers[x] = Bukkit.getPlayer(playerArgs[x]);
                    amtOfPlayers[x].getInventory().clear();
                    amtOfPlayers[x].getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                    amtOfPlayers[x].setGameMode(GameMode.SURVIVAL);
                    amtOfPlayers[x].setHealth(20.0);
                    amtOfPlayers[x].setFoodLevel(20);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + amtOfPlayers[x].getName() + " everything");
                    if (fireResist) {
                    	amtOfPlayers[x].addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, (999999), 1)); //uncomment these effects if you want to have everyone survive the first round
                    }
                    if (haste) {
                    	amtOfPlayers[x].addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (time*20+600), 9));
                    }
                    if (resist) {
                    	amtOfPlayers[x].addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (time*20+600), 9));
                    }
                    if (hunger) {
                    	amtOfPlayers[x].addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, (9999999), 20));
                    }
                }
                for (World w: Bukkit.getWorlds()) {
                    w.setTime(0);
                    w.setStorm(false);
                }


                Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "***DeathSwap***\nCreated by: \u30C4KRN");
                final int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        if (i == 1)
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in " + time + " secs");
                        if (i > (time - 11) && i < time) {
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in " + (time - i));
                            for (int y = 0; y < playerArgs.length; y++) {
                            	amtOfPlayers[y] = Bukkit.getPlayer(playerArgs[y]);
                            	amtOfPlayers[y].playSound(amtOfPlayers[y].getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.2f, 1);
                            }
                        }
                        if (i == time) {

                            for (int x = 0; x < playerArgs.length; x++) {
                                amtOfPlayers[x] = Bukkit.getPlayer(playerArgs[x]);
                                if (x != (playerArgs.length - 1)) {
                                    amtOfPlayers[x + 1] = Bukkit.getPlayer(playerArgs[x + 1]);
                                    playerLocations[x + 1] = amtOfPlayers[x + 1].getLocation();
                                }
                                playerLocations[x] = amtOfPlayers[x].getLocation();
                                if (x == playerArgs.length - 1) {
                                    amtOfPlayers[x].teleport(playerLocations[0]);
                                    amtOfPlayers[x].playSound(amtOfPlayers[x].getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                                } else {
                                    amtOfPlayers[x].teleport(playerLocations[(x + 1)]);
                                    amtOfPlayers[x].playSound(amtOfPlayers[x].getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                                }
                            }
                            i = 0;
                        }
                        for (int a = 0; a < playerArgs.length; a++) {
                            Player[] playerAlive = new Player[playerArgs.length];
                            playerAlive[a] = Bukkit.getPlayer(playerArgs[a]);
                            if (playerAlive[a].isDead()) {
                                String deadPlayer = playerAlive[a].getName();
                                playerAlive[a].setGameMode(GameMode.SPECTATOR);
                                newLength = playerArgs.length;
                                for (int b = 0; b < playerArgs.length; b++) {
                                    if (playerArgs[b].contains(deadPlayer)) { // shoutout to the peeps at stackoverflow https://stackoverflow.com/questions/47068605/java-remove-an-item-from-existing-string-array
                                        newLength--;
                                        
                                    }
                                    
                                }
                                String[] results = new String[newLength];
                                int count = 0;
                                for (int b = 0; b < playerArgs.length; b++) {
                                    if (!playerArgs[b].contains(deadPlayer)) {
                                        results[count] = playerArgs[b];
                                        count++;
                                    }
                                }
                                playerArgs = results.clone();
                                if (playerArgs.length == 1) {
                                	Player winner = Bukkit.getPlayer(playerArgs[0]);
                                	Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Winner: " + ChatColor.GOLD + ChatColor.BOLD + winner.getName()); 
                                	gameOver = true;
                                }
                            }
                        }
                        i++;
                    }
                }, 0, 20);
                Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                    	if (gameOver) {
                    		Bukkit.getScheduler().cancelTask(task);
                    		i = 0;
                    		gameOver = false;
                    	}
                    }
                }, 0, 20);

            } else {
        		Bukkit.broadcastMessage("Usage: /deathswap [player1] [player2] [player3...] [time] (add as many players, use /deatheffect for effects)");
        	}


        }
        return false;

    }
}