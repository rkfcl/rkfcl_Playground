package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.awt.event.KeyEvent;

public class menu implements CommandExecutor, Listener {
    private JavaPlugin plugin;

    public menu(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("메뉴")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openMenu(player);
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && isCapsLockActive(player)) {
            openMenu(player);
        }
    }

    private void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36 ,"[ 갈치의 놀이터 ] 메뉴");
        inventory.setItem(0, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        for(int i=1;i<=7;i++) {
            inventory.setItem(i, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        }
        inventory.setItem(8, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(9, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(10, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(11, ItemManager.MenuCheck());
        inventory.setItem(27, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(0, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        for(int i=28;i<=34;i++) {
            inventory.setItem(i, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        }
        inventory.setItem(35, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());

        player.openInventory(inventory);
    }

    private boolean isCapsLockActive(Player player) {
        return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    }
}
