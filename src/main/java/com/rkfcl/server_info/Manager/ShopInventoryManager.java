package com.rkfcl.server_info.Manager;

import com.rkfcl.server_info.ItemRegistration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


import java.util.*;

import static com.rkfcl.server_info.ItemRegistration.registeredItems;
import static com.rkfcl.server_info.ItemRegistration.removeExpiredItems;
import static com.rkfcl.server_info.ItemReturn.ReturnedItems;
import static com.rkfcl.server_info.Manager.ItemManager.*;
import static com.rkfcl.server_info.ProtectBlock.AccountprotectMap;
import static com.rkfcl.server_info.ProtectBlock.protectMap;

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
    List<String> itemcropsNames = Arrays.asList(
            "corn",
            "cabbage",
            "onion",
            "sweet_potato",
            "tomato",
            "rice",
            "corn_seeds",
            "cabbage_seeds",
            "onion_seeds",
            "sweet_potato_seeds",
            "tomato_seeds",
            "rice_seeds"


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

//        setItem(inventory, 6, ItemManager.createCookerJob1Item());
//        setItem(inventory, 15, ItemManager.createCookerJob2Item());
//        setItem(inventory, 24, ItemManager.createCookerJob3Item());
//        setItem(inventory, 33, ItemManager.createCookerJob4Item());

        setItem(inventory, 39, ItemManager.createresetJobItem());

//        setItem(inventory, 45, createItemsAdderItem("small_construction_block"));
//        setItem(inventory, 46, createItemsAdderItem("medium_construction_block"));
//        setItem(inventory, 47, createItemsAdderItem("large_construction_block"));

        player.openInventory(inventory);
    }
    public void openShopButcherInventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "도축업자 상점");

        setItem(inventory, 0, ItemManager.createOreItem(Material.MUTTON,0));
        setItem(inventory, 1, ItemManager.createOreItem(Material.CHICKEN,0));
        setItem(inventory, 2, ItemManager.createOreItem(Material.RABBIT,0));
        setItem(inventory, 3, ItemManager.createOreItem(Material.BEEF,0));
        setItem(inventory, 4, ItemManager.createOreItem(Material.PORKCHOP,0));
        setItem(inventory, 5, ItemManager.createOreItem(Material.EGG,0));
        setItem(inventory, 6, ItemManager.createOreItem(Material.FEATHER,0));
        setItem(inventory, 7, ItemManager.createOreItem(Material.HONEYCOMB,0));
        setItem(inventory, 8, ItemManager.createOreItem(Material.HONEY_BOTTLE,0));
        setItem(inventory, 9, ItemManager.createOreItem(Material.COOKED_MUTTON,0));
        setItem(inventory, 10, ItemManager.createOreItem(Material.COOKED_CHICKEN,0));
        setItem(inventory, 11, ItemManager.createOreItem(Material.COOKED_RABBIT,0));
        setItem(inventory, 12, ItemManager.createOreItem(Material.COOKED_BEEF,0));
        setItem(inventory, 13, ItemManager.createOreItem(Material.COOKED_PORKCHOP,0));
        setItem(inventory, 18, ItemManager.createOreItem(Material.RABBIT_HIDE,0));
        setItem(inventory, 19, ItemManager.createOreItem(Material.RABBIT_FOOT,0));
        setItem(inventory, 20, ItemManager.createOreItem(Material.LEATHER,0));
        setItem(inventory, 21, ItemManager.createOreItem(Material.SCUTE,0));

        setGlassPanes(inventory);
        setClock(inventory, 49);

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
        setItem(inventory, 14, ItemManager.createOreItem(Material.AMETHYST_BLOCK,0));

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }
    public void openShopFarmer1Inventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "농부 상점");

        setItem(inventory, 0, ItemManager.createOreItem(Material.PUMPKIN,0));
        setItem(inventory, 1, ItemManager.createOreItem(Material.CARROT,0));
        setItem(inventory, 2, ItemManager.createOreItem(Material.BAKED_POTATO,0));
        setItem(inventory, 3, ItemManager.createOreItem(Material.POISONOUS_POTATO,0));
        setItem(inventory, 4, ItemManager.createOreItem(Material.BEETROOT,0));
        setItem(inventory, 5, ItemManager.createOreItem(Material.WHEAT,0));
        setItem(inventory, 6, ItemManager.createOreItem(Material.BREAD,0));
        setItem(inventory, 7, ItemManager.createOreItem(Material.PUMPKIN_PIE,0));
        setItem(inventory, 9, ItemManager.createOreItem(Material.MELON,0));
        setItem(inventory, 10, ItemManager.createOreItem(Material.GOLDEN_CARROT,0));
        setItem(inventory, 11, ItemManager.createOreItem(Material.SUGAR_CANE,0));
        setItem(inventory, 12, ItemManager.createOreItem(Material.APPLE,0));
        setItem(inventory, 13, ItemManager.createOreItem(Material.GOLDEN_APPLE,0));
        setItem(inventory, 14, ItemManager.createOreItem(Material.GLISTERING_MELON_SLICE,0));
        setItem(inventory, 15, ItemManager.createOreItem(Material.NETHER_WART,0));
        setItem(inventory, 16, ItemManager.createOreItem(Material.CAKE,0));
        setItem(inventory, 18, ItemManager.createOreItem(Material.GLOW_BERRIES,0));
        setItem(inventory, 19, ItemManager.createOreItem(Material.SWEET_BERRIES,0));

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }
    public void openShopFarmer2Inventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "농부2 상점");

        for (int i=0;i<=5;i++) {
            setItem(inventory, i, createItemsAdderItem(itemcropsNames.get(i)));
        }
        for (int i=9;i<=14;i++) {
            setItem(inventory, i, createSaleItemsAdderItem(itemcropsNames.get(i-3)));
        }
        setItem(inventory, 35, ItemManager.createSaleItem(Material.OAK_PLANKS,10000,"농토"));
        setItem(inventory, 44, createSaleItemsAdderItem("watering_can"));

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
            setItem(inventory, i, createItemsAdderItem(itemNames.get(i-9)));
        }

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }
    public void openShopItemsInventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "잡화 상점");
        setItem(inventory, 0, createSaleItemsAdderItem("small_construction_block"));
        setItem(inventory, 1, createSaleItemsAdderItem("medium_construction_block"));
        setItem(inventory, 2, createSaleItemsAdderItem("large_construction_block"));
        setItem(inventory, 3, ItemManager.createSaleItem(Material.IRON_DOOR,1,"비밀번호 문"));
        setItem(inventory, 9, createSaleItemsAdderItem("letter_of_return"));
        setItem(inventory, 18, createSaleItemsAdderItem("chur"));

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }
    public void openexchangeInventory(Player player, int page) {
        removeExpiredItems();
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "거래소 : " + page + "페이지");

        Map<ItemStack, UUID> registeredItems = ItemRegistration.getRegisteredItems();

        int slot = 0;
        int maxSlotsPerPage = 45;

        int startIndex = (page - 1) * maxSlotsPerPage;
        int endIndex = startIndex + maxSlotsPerPage;

        List<ItemStack> itemList = new ArrayList<>(registeredItems.keySet());
        for (int i = startIndex; i < endIndex && i < itemList.size(); i++) {
            ItemStack item = itemList.get(i);
            setItem(inventory, slot, item);
            slot++;
        }

        setItem(inventory, 45, ItemManager.InvenDecoRED_STAINED_GLASS_PANE_BEFORE());
        setItem(inventory, 46, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 47, ItemManager.InvenDeco_CHEST());
        setItem(inventory, 48, ItemManager.InvenDeco_ENDERCHEST());
        setItem(inventory, 49, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 50, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 51, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 52, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 53, ItemManager.InvenDecoGREEN_STAINED_GLASS_PANE_NEXT());

        player.openInventory(inventory);
    }
    public void openRegisteredInventory(Player player, int page) {
        removeExpiredItems();
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "등록된 아이템 : " + page + "페이지");

        int slot = 0;
        int maxSlotsPerPage = 45;
        List<ItemStack> registeredItemList = new ArrayList<>();
        for (ItemStack item : registeredItems.keySet()) {
            UUID uuid = registeredItems.get(item);
            if (uuid.equals(player.getUniqueId())) {
                registeredItemList.add(item);
            }
        }
        int startIndex = (page - 1) * maxSlotsPerPage;
        int endIndex = Math.min(startIndex + maxSlotsPerPage, registeredItemList.size());

        for (int i = startIndex; i < endIndex; i++) {
            ItemStack item = registeredItemList.get(i);
            setItem(inventory, slot, item);
            slot++;
        }
        setItem(inventory, 45, ItemManager.InvenDecoRED_STAINED_GLASS_PANE_BEFORE());
        setItem(inventory, 46, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 47, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 48, ItemManager.InvenDeco_ENDERCHEST());
        setItem(inventory, 49, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 50, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 51, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 52, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 53, ItemManager.InvenDecoGREEN_STAINED_GLASS_PANE_NEXT());

        player.openInventory(inventory);
    }

    public void openReturnedInventory(Player player, int page) {
        removeExpiredItems();
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 54, "반환된 아이템 : " + page + "페이지");

        int slot = 0;
        int maxSlotsPerPage = 45;

        List<ItemStack> returnedItemList = new ArrayList<>();
        for (ItemStack item : ReturnedItems.keySet()) {
            UUID uuid = ReturnedItems.get(item);
            if (uuid.equals(player.getUniqueId())) {
                returnedItemList.add(item);
            }
        }

        int startIndex = (page - 1) * maxSlotsPerPage;
        int endIndex = Math.min(startIndex + maxSlotsPerPage, returnedItemList.size());

        for (int i = startIndex; i < endIndex; i++) {
            ItemStack item = returnedItemList.get(i);
            setItem(inventory, slot, item);
            slot++;
        }
        setItem(inventory, 45, ItemManager.InvenDecoRED_STAINED_GLASS_PANE_BEFORE());
        setItem(inventory, 46, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 47, ItemManager.InvenDeco_CHEST());
        setItem(inventory, 48, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 49, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 50, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 51, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 52, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        setItem(inventory, 53, ItemManager.InvenDecoGREEN_STAINED_GLASS_PANE_NEXT());
        player.openInventory(inventory);
    }




    public void AccountInventory(Player player, Location location) {
        Inventory inventory = Bukkit.createInventory(null, 36, Bukkit.getPlayer(player.getUniqueId()).getDisplayName()+"님의 소유");

        // 플레이어 머리 아이템 생성
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName(player.getDisplayName());
        playerHead.setItemMeta(skullMeta);


        inventory.setItem(0, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(1, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(2, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(3, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(4, playerHead);
        inventory.setItem(5, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(6, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(7, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(8, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());

        int slot = 9;
        List<UUID> playerList = AccountprotectMap.get(location);
        for (int i = 1; i < playerList.size(); i++) {
            UUID uuid = playerList.get(i);
            player.sendMessage(uuid);
            Player targetPlayer = Bukkit.getPlayer(uuid);
            if (targetPlayer != null) {
                ItemStack targetPlayerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta targetSkullMeta = (SkullMeta) targetPlayerHead.getItemMeta();
                targetSkullMeta.setOwningPlayer(targetPlayer);
                targetSkullMeta.setDisplayName(targetPlayer.getDisplayName());
                targetPlayerHead.setItemMeta(targetSkullMeta);


                // [ 쉬프트 좌클릭시 해당 플레이어의 권한을 제거합니다 ] 설명 추가
                ItemMeta itemMeta = targetPlayerHead.getItemMeta();
                itemMeta.setLore(Collections.singletonList("§7[ 쉬프트 좌클릭시 해당 플레이어의 권한을 제거합니다 ]"));
                targetPlayerHead.setItemMeta(itemMeta);

                inventory.setItem(slot, targetPlayerHead);
                slot++;
            }
        }

        inventory.setItem(27, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(28, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(29, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(30, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(31, ItemManager.InvenDecoGREEN_STAINED_GLASS_PANE());
        inventory.setItem(32, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(33, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(34, ItemManager.InvenDecoBLACK_STAINED_GLASS_PANE());
        inventory.setItem(35, ItemManager.InvenDecoRED_STAINED_GLASS_PANE());
        player.openInventory(inventory);
    }
    public void AccountAddInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "권한을 추가할 플레이어를 선택해 주세요");
        int slot = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.setOwningPlayer(onlinePlayer);
            playerHead.setItemMeta(skullMeta);

            ItemMeta itemMeta = playerHead.getItemMeta();
            itemMeta.setDisplayName(onlinePlayer.getDisplayName());
            List<String> lore = new ArrayList<>();
            lore.add("클릭시 건차에 권한을 추가합니다");
            itemMeta.setLore(lore);
            playerHead.setItemMeta(itemMeta);

            inventory.setItem(slot, playerHead);
            slot++;
        }

        setGlassPanes(inventory);
        setClock(inventory, 49);

        player.openInventory(inventory);
    }

    public void lockdoorsettings(Player player,String key) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 45, "비밀번호 문 설정");
        setItem(inventory, 4, ItemManager.locksign(key));
        setItem(inventory, 40, ItemManager.locknum(0));
        setItem(inventory, 12, ItemManager.locknum(1));
        setItem(inventory, 13, ItemManager.locknum(2));
        setItem(inventory, 14, ItemManager.locknum(3));
        setItem(inventory, 21, ItemManager.locknum(4));
        setItem(inventory, 22, ItemManager.locknum(5));
        setItem(inventory, 23, ItemManager.locknum(6));
        setItem(inventory, 30, ItemManager.locknum(7));
        setItem(inventory, 31, ItemManager.locknum(8));
        setItem(inventory, 32, ItemManager.locknum(9));
        setItem(inventory, 36, ItemManager.lockdelete());
        setItem(inventory, 44, ItemManager.lockconfirm());

        player.openInventory(inventory);
    }
    public void lockdoor(Player player,String key) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 45, "비밀번호 문");
        setItem(inventory, 4, ItemManager.locksign(key));
        setItem(inventory, 40, ItemManager.locknum(0));
        setItem(inventory, 12, ItemManager.locknum(1));
        setItem(inventory, 13, ItemManager.locknum(2));
        setItem(inventory, 14, ItemManager.locknum(3));
        setItem(inventory, 21, ItemManager.locknum(4));
        setItem(inventory, 22, ItemManager.locknum(5));
        setItem(inventory, 23, ItemManager.locknum(6));
        setItem(inventory, 30, ItemManager.locknum(7));
        setItem(inventory, 31, ItemManager.locknum(8));
        setItem(inventory, 32, ItemManager.locknum(9));
        setItem(inventory, 36, ItemManager.lockdelete());
        setItem(inventory, 44, ItemManager.lockconfirm());

        player.openInventory(inventory);
    }
    public void coinshopinventory(Player player) {
        itemManager = new ItemManager();
        Inventory inventory = Bukkit.createInventory(null, 45, "코인 상점");
        setItem(inventory, 21, createSaleCoinItemsAdderItem("invensave"));
        setItem(inventory, 3, createSaleCoinItemsAdderItem("small_construction_block"));
        setItem(inventory, 4, createSaleCoinItemsAdderItem("medium_construction_block"));
        setItem(inventory, 5, createSaleCoinItemsAdderItem("large_construction_block"));
        setItem(inventory, 23, createSaleCoinItemsAdderItem("randombook"));


        player.openInventory(inventory);
    }

    private void setItem(Inventory inventory, int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    private void setGlassPanes(Inventory inventory) {
        for (int i = 45; i <= 53; i++) {
            inventory.setItem(i, ItemManager.InvenDecoWHITE_STAINED_GLASS_PANE());
        }
    }

    private void setClock(Inventory inventory, int slot) {
        inventory.setItem(slot, new ItemStack(Material.CLOCK));
    }
}
