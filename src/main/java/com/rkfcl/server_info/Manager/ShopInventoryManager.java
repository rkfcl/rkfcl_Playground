package com.rkfcl.server_info.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopInventoryManager {
    public void openShopJobInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "전직교관 상점");
        inventory.setItem(4, ItemManager.createMineJob1Item());
        inventory.setItem(13, ItemManager.createMineJob2Item());
        inventory.setItem(22, ItemManager.createMineJob3Item());
        inventory.setItem(31, ItemManager.createMineJob4Item());

        inventory.setItem(2, ItemManager.createFarmerJob1Item());
        inventory.setItem(11, ItemManager.createFarmerJob2Item());
        inventory.setItem(20, ItemManager.createFarmerJob3Item());
        inventory.setItem(29, ItemManager.createFarmerJob4Item());

        inventory.setItem(0, ItemManager.createFisherJob1Item());
        inventory.setItem(9, ItemManager.createFisherJob2Item());
        inventory.setItem(18, ItemManager.createFisherJob3Item());
        inventory.setItem(27, ItemManager.createFisherJob4Item());

        inventory.setItem(6, ItemManager.createCookerJob1Item());
        inventory.setItem(15, ItemManager.createCookerJob2Item());
        inventory.setItem(24, ItemManager.createCookerJob3Item());
        inventory.setItem(33, ItemManager.createCookerJob4Item());


        inventory.setItem(39, ItemManager.createresetJobItem());
        player.openInventory(inventory);
    }

    public void openShopMineInventory(Player player) {
        ItemManager itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "광부 상점");
        inventory.setItem(0, ItemManager.createOreItem(Material.FLINT));
        inventory.setItem(1, ItemManager.createOreItem(Material.QUARTZ));
        inventory.setItem(2, ItemManager.createOreItem(Material.AMETHYST_SHARD));
        inventory.setItem(3, ItemManager.createOreItem(Material.COPPER_INGOT));
        inventory.setItem(4, ItemManager.createOreItem(Material.IRON_INGOT));
        inventory.setItem(5, ItemManager.createOreItem(Material.GOLD_INGOT));
        inventory.setItem(6, ItemManager.createOreItem(Material.DIAMOND));
        inventory.setItem(7, ItemManager.createOreItem(Material.EMERALD));
        inventory.setItem(8, ItemManager.createOreItem(Material.NETHERITE_INGOT));
        inventory.setItem(9, ItemManager.createOreItem(Material.COAL_BLOCK));
        inventory.setItem(10, ItemManager.createOreItem(Material.LAPIS_BLOCK));
        inventory.setItem(11, ItemManager.createOreItem(Material.REDSTONE_BLOCK));
        inventory.setItem(12, ItemManager.createOreItem(Material.OBSIDIAN));
        inventory.setItem(13, ItemManager.createOreItem(Material.CRYING_OBSIDIAN));
        inventory.setItem(14, ItemManager.createOreItem(Material.GLOWSTONE));

        inventory.setItem(53, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(52, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(51, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(50, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(49, new ItemStack(Material.CLOCK));
        inventory.setItem(48, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(47, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(46, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        inventory.setItem(45, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));


        player.openInventory(inventory);
    }
}
