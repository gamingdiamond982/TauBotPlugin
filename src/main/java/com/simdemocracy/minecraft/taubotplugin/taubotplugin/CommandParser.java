package com.simdemocracy.minecraft.taubotplugin.taubotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandParser implements CommandExecutor {
    //builds connection object
    ApiConnection connection = new ApiConnection();
    
    public CommandParser() {
        System.out.println("Constructing a TauBot CommandParser");

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("Running command " + command.getName());
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        int balance;
        //Sloppy ik but it works
        if (args[0].equals("balance") || args[0].equals("bal")){
            try {
                balance = connection.getUserBalance(sender.getName());
            }catch (ApiException e){
                sender.sendMessage(e.getMessage());
                return true;
            }
            sender.sendMessage("Your balance is: " + balance);
            return true;
        }
        return false;
    }
}
