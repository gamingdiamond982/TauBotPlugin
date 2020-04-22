package com.simdemocracy.minecraft.taubotplugin.taubotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TauBotPlugin extends JavaPlugin {
    ApiConnection connection = new ApiConnection();
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("TauBot has been started");
        this.getCommand("e").setExecutor(new CommandParser());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("TauBot has been stopped");
        
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        getLogger().info("Unsupported command: " + cmd.getName());
        return false;
    }
}
