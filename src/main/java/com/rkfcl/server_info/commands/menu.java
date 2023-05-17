package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class menu implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("메뉴")){
            Player player =(Player) sender;
            Inventory inventory = Bukkit.createInventory(null, 54 ,"[ 갈치의 놀이터 ] 메뉴");
            inventory.setItem(45, new ItemStack(Material.PAPER));


            player.openInventory(inventory);
        }
        return false;
    }
}
