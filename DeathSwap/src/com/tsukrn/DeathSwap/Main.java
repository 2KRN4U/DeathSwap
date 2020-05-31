package com.tsukrn.DeathSwap;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("deathswap")) {
            if (args.length > 2) {
                String[] arguments = new String[(args.length - 1)];
                playerArgs = arguments.clone();
                Player[] water = new Player[(args.length - 1)];
                Player[] amtOfPlayers = new Player[(args.length - 1)];
                Location[] playerLocations = new Location[(args.length - 1)];
                for (int x = 0; x < (args.length - 1); x++) {
                    playerArgs[x] = args[x];
                    water[x] = Bukkit.getPlayer(playerArgs[x]);
                    water[x].getInventory().clear();
                    water[x].getInventory().addItem(new ItemStack(Material.WATER_BUCKET));

                }
                for (World w: Bukkit.getWorlds()) {
                    w.setTime(0);
                    w.setStorm(false);
                }



                Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "***DeathSwap***\nCreated by: \u30C4KRN");
                int time = Integer.parseInt(args[(args.length - 1)]);
                Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        if (i == 1)
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in " + time + " secs");
                        if (i > (time - 10) && i < time) {
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Teleporting in " + (time - i));
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
                                } else {
                                    amtOfPlayers[x].teleport(playerLocations[(x + 1)]);
                                }
                            }
                            i = 0;
                        }
                        for (int a = 0; a < playerArgs.length; a++) {
                            Player[] playerAlive = new Player[playerArgs.length];
                            playerAlive[a] = Bukkit.getPlayer(playerArgs[a]);
                            if (playerAlive[a].isDead()) {
                                String deadPlayer = playerAlive[a].getName();
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
                            }
                        }
                        i++;
                    }
                }, 0, 20);


            }


        }
        return false;

    }
}