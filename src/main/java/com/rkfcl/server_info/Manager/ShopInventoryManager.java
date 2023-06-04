package com.rkfcl.server_info.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.util.Arrays;
import java.util.List;

import static com.rkfcl.server_info.Manager.ItemManager.createItemsAdderFishItem;

public class ShopInventoryManager {
    ItemManager itemManager;
    List<String> itemNames = Arrays.asList(
            "arapaima",
            "anglerfish",
            "armored_catfish",
            "bayad",
            "blackfish",
            "bluegill",
            "tilapia",
            "brown_trout",
            "capitaine",
            "carp",
            "catfish",
            "electric_eel",
            "gar",
            "halibut",
            "herring",
            "minnow",
            "muskellunge",
            "perch",
            "pink_salmon",
            "pollock",
            "rainbow_trout",
            "red_bellied_piranha",
            "red_grouper",
            "smallmouth_bass",
            "synodontis",
            "tambaqui",
            "tuna"
    );


    public void openShopJobInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "전직교관 상점");

        setItem(inventory, 4, ItemManager.createMineJob1Item());
        setItem(inventory, 13, ItemManager.createMineJob2Item());
        setItem(inventory, 22, ItemManager.createMineJob3Item());
        setItem(inventory, 31, ItemManager.createMineJob4Item());

        setItem(inventory, 2, ItemManager.createFarmerJob1Item());
        setItem(inventory, 11, ItemManager.createFarmerJob2Item());
        setItem(inventory, 20, ItemManager.createFarmerJob3Item());
        setItem(inventory, 29, ItemManager.createFarmerJob4Item());

        setItem(inventory, 0, ItemManager.createFisherJob1Item());
        setItem(inventory, 9, ItemManager.createFisherJob2Item());
        setItem(inventory, 18, ItemManager.createFisherJob3Item());
        setItem(inventory, 27, ItemManager.createFisherJob4Item());

        setItem(inventory, 6, ItemManager.createCookerJob1Item());
        setItem(inventory, 15, ItemManager.createCookerJob2Item());
        setItem(inventory, 24, ItemManager.createCookerJob3Item());
        setItem(inventory, 33, ItemManager.createCookerJob4Item());

        setItem(inventory, 39, ItemManager.createresetJobItem());

        player.openInventory(inventory);
    }

    public void openShopMineInventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "광부 상점");

        setItem(inventory, 0, ItemManager.createOreItem(Material.FLINT,0));
        setItem(inventory, 1, ItemManager.createOreItem(Material.QUARTZ,0));
        setItem(inventory, 2, ItemManager.createOreItem(Material.AMETHYST_SHARD,0));
        setItem(inventory, 3, ItemManager.createOreItem(Material.COPPER_INGOT,0));
        setItem(inventory, 4, ItemManager.createOreItem(Material.IRON_INGOT,0));
        setItem(inventory, 5, ItemManager.createOreItem(Material.GOLD_INGOT,0));
        setItem(inventory, 6, ItemManager.createOreItem(Material.DIAMOND,0));
        setItem(inventory, 7, ItemManager.createOreItem(Material.EMERALD,0));
        setItem(inventory, 8, ItemManager.createOreItem(Material.NETHERITE_INGOT,0));
        setItem(inventory, 9, ItemManager.createOreItem(Material.COAL_BLOCK,0));
        setItem(inventory, 10, ItemManager.createOreItem(Material.LAPIS_BLOCK,0));
        setItem(inventory, 11, ItemManager.createOreItem(Material.REDSTONE_BLOCK,0));
        setItem(inventory, 12, ItemManager.createOreItem(Material.OBSIDIAN,0));
        setItem(inventory, 13, ItemManager.createOreItem(Material.CRYING_OBSIDIAN,0));
        setItem(inventory, 14, ItemManager.createOreItem(Material.GLOWSTONE,0));

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }

    public void openShopFishInventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "어부 상점");

        setItem(inventory, 0, ItemManager.createFishItem(Material.COD, 0));
        setItem(inventory, 1, ItemManager.createFishItem(Material.SALMON, 0));
        setItem(inventory, 2, ItemManager.createFishItem(Material.TROPICAL_FISH, 0));
        setItem(inventory, 3, ItemManager.createFishItem(Material.PUFFERFISH, 0));
        for (int i=9;i<=35;i++) {
            setItem(inventory, i, createItemsAdderFishItem(itemNames.get(i-9)));
        }

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }
    private void setItem(Inventory inventory, int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    private void setGlassPanes(Inventory inventory) {
        for (int i = 45; i <= 53; i++) {
            inventory.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        }
    }

    private void setClock(Inventory inventory, int slot) {
        inventory.setItem(slot, new ItemStack(Material.CLOCK));
    }
}
