package net.mixeration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class Garbage extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.only-in-game")));
            return true;
        } else {
            Player p = (Player)sender;
            if ((cmd.getName().equalsIgnoreCase("disposal") || cmd.getName().equalsIgnoreCase("garbage")) && args.length == 0) {
                if (getConfig().getBoolean("settings.ask-permission", true)) {
                    if (p.hasPermission("Garbage.open")) {
                        Inventory inv1 = Bukkit.getServer().createInventory(p, this.getConfig().getInt("menu.gui-size"), this.getConfig().getString("menu.gui-name").replace("&", "§"));
                        p.openInventory(inv1);
                        return true;
                    } else {
                        if (!p.hasPermission("Garbage.open")) {
                            String noperm = this.getConfig().getString("messages.no-permission").replace("&", "§");
                            p.sendMessage(noperm);
                        }
                        return false;
                    }
                } else {
                    Inventory inv1 = Bukkit.getServer().createInventory(p, this.getConfig().getInt("menu.gui-size"), this.getConfig().getString("menu.gui-name").replace("&", "§"));
                    p.openInventory(inv1);
                    return true;
                }
            }
            if ((cmd.getName().equalsIgnoreCase("garbagerel") && args.length == 0)) {
                if (p.hasPermission("Garbage.Admin")) {
                    reloadConfig();
                    saveConfig();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Garbage &8| &7Plugin reloaded"));
                } else {
                    if (!p.hasPermission("Garbage.Admin")) {
                        String noperm = this.getConfig().getString("messages.no-permission").replace("&", "§");
                        p.sendMessage(noperm);
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
