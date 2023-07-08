package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.Manager.ItemManager;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuCommand implements CommandExecutor, Listener {
    private JavaPlugin plugin;

    public MenuCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("메뉴")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openMenu(player);
                return true;
            }
        }
        return false;
    }

    private void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "[ 갈치의 놀이터 ] 메뉴");
        inventory.setItem(0, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(8, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(9, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(17, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(18, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(26, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(27, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(35, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        for (int i = 1; i <= 7; i++) {
            inventory.setItem(i, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        }

        inventory.setItem(10, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(11, ItemManager.MenuCheck());
        inventory.setItem(12, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(13, ItemManager.InvenDecoSign());
        inventory.setItem(14, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(15, ItemManager.Menu_Quest());
        inventory.setItem(16, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(19, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(21, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(23, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        inventory.setItem(24, ItemManager.Menu_fishcollect());
        inventory.setItem(25, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        for (int i = 28; i <= 34; i++) {
            inventory.setItem(i, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        }


        player.openInventory(inventory);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()
                && player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            openMenu(player);
        }
    }
}
