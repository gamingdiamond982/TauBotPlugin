package com.simdemocracy.minecraft.taubotplugin.taubotplugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandParser implements CommandExecutor {
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
        if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")){
            try {
                balance = connection.getUserBalance(sender.getName());
            }catch (ApiException e){
                sender.sendMessage(e.getMessage());
                return true;
            }
            sender.sendMessage("Your balance is: " + balance);
            return true;
        } else if (args[0].equalsIgnoreCase("transfer")){
            try {
                connection.transfer(sender.getName(), args[1], Integer.parseInt(args[2]));
            } catch (ApiException e) {
                sender.sendMessage(e.getMessage());
                return true;
            }
            sender.sendMessage("Success");
            return true;
        }
        else {
            sender.sendMessage("that is not a real command, SMH my head");
        }
        return false;
    }
}
