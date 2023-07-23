package com.rkfcl.server_info.ItemManagerCost;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemCost {
    public static Map<String, Integer> itemPrices = new HashMap<>();
    public static Map<String, Integer> itemDefaultPrices = new HashMap<>();
    private Map<String, Integer> itemMinValues = new HashMap<>();
    private Map<String, Integer> itemMaxValues = new HashMap<>();

    public ItemCost() {
        initItemValues();
        updateItemCosts();
    }

    private void initItemValues() {
        //잡화 상점 가격
        itemDefaultMinMaxValues("paper-167",10000,10000,10000);
        itemDefaultMinMaxValues("paper-168",30000,30000,30000);
        itemDefaultMinMaxValues("paper-169",50000,50000,50000);
        itemDefaultMinMaxValues("iron_door-1",1000,1000,1000);
        itemDefaultMinMaxValues("paper-100",500,500,500);
        itemDefaultMinMaxValues("paper-5005",500,500,500);
        itemDefaultMinMaxValues("bread-10000",3,3,3);

        //광부 상점 가격
        itemDefaultMinMaxValues("flint-0",1,0,2);
        itemDefaultMinMaxValues("iron_ingot-0",2,0,4);
        itemDefaultMinMaxValues("gold_ingot-0",4,1,6);
        itemDefaultMinMaxValues("diamond-0",50,30,80);
        itemDefaultMinMaxValues("quartz-0",1,0,3);
        itemDefaultMinMaxValues("amethyst_shard-0",1,0,2);
        itemDefaultMinMaxValues("copper_ingot-0",1,0,2);
        itemDefaultMinMaxValues("emerald-0",70,50,150);
        itemDefaultMinMaxValues("netherite_ingot-0",500,300,1000);
        itemDefaultMinMaxValues("coal_block-0",10,5,15);
        itemDefaultMinMaxValues("lapis_block-0",10,5,15);
        itemDefaultMinMaxValues("redstone_block-0",10,3,15);
        itemDefaultMinMaxValues("amethyst_block-0",4,1,8);
        itemDefaultMinMaxValues("obsidian-0",3,1,5);
        itemDefaultMinMaxValues("crying_obsidian-0",60,40,80);
        itemDefaultMinMaxValues("glowstone-0",3,1,5);

        //농부 상점 가격
        itemDefaultMinMaxValues("pumpkin-0",3,1,5);
        itemDefaultMinMaxValues("carrot-0",1,0,2);
        itemDefaultMinMaxValues("baked_potato-0",1,0,2);
        itemDefaultMinMaxValues("poisonous_potato-0",0,0,1);
        itemDefaultMinMaxValues("beetroot-0",1,0,2);
        itemDefaultMinMaxValues("wheat-0",2,1,3);
        itemDefaultMinMaxValues("bread-0",6,1,9);
        itemDefaultMinMaxValues("pumpkin_pie-0",7,5,9);
        itemDefaultMinMaxValues("melon-0",3,1,5);
        itemDefaultMinMaxValues("golden_carrot-0",7,5,10);
        itemDefaultMinMaxValues("sugar_cane-0",1,0,2);
        itemDefaultMinMaxValues("apple-0",1,1,2);
        itemDefaultMinMaxValues("golden_apple-0",30,15,45);
        itemDefaultMinMaxValues("glistering_melon_slice-0",3,1,5);
        itemDefaultMinMaxValues("nether_wart-0",3,1,5);
        itemDefaultMinMaxValues("cake-0",25,15,35);
        itemDefaultMinMaxValues("glow_berries-0",2,1,3);
        itemDefaultMinMaxValues("sweet_berries-0",1,0,2);
        itemDefaultMinMaxValues("cocoa_beans-0",1,0,2);

        //농부 2상점 가격
        itemDefaultMinMaxValues("apple-10028",15,10,20); //옥수수
        itemDefaultMinMaxValues("apple-10029",18,13,23); //양배추
        itemDefaultMinMaxValues("apple-10030",15,10,20); //양파
        itemDefaultMinMaxValues("apple-10031",17,12,22); //고구마
        itemDefaultMinMaxValues("apple-10032",16,11,21); //토마토
        itemDefaultMinMaxValues("apple-10033",13,8,18); //쌀
        itemDefaultMinMaxValues("wheat_seeds-1",5,5,5);
        itemDefaultMinMaxValues("wheat_seeds-2",6,6,6);
        itemDefaultMinMaxValues("wheat_seeds-3",5,5,5);
        itemDefaultMinMaxValues("wheat_seeds-4",6,6,6);
        itemDefaultMinMaxValues("wheat_seeds-5",5,5,5);
        itemDefaultMinMaxValues("wheat_seeds-6",4,4,4);
        itemDefaultMinMaxValues("oak_planks-10000",60,60,60);
        itemDefaultMinMaxValues("shears-101",5000,5000,5000);

        //어부 상점 가격
        itemDefaultMinMaxValues("cod-0",1,0,2);
        itemDefaultMinMaxValues("tropical_fish-0",3,2,4);
        itemDefaultMinMaxValues("pufferfish-0",3,2,4);
        itemDefaultMinMaxValues("salmon-0",2,1,3);
        itemDefaultMinMaxValues("rotten_flesh-1",2,1,3);
        itemDefaultMinMaxValues("rotten_flesh-2",10,7,13);
        itemDefaultMinMaxValues("rotten_flesh-3",5,3,7);
        itemDefaultMinMaxValues("rotten_flesh-4",4,2,6);
        itemDefaultMinMaxValues("rotten_flesh-5",7,4,10);
        itemDefaultMinMaxValues("rotten_flesh-6",5,3,7);
        itemDefaultMinMaxValues("rotten_flesh-7",4,2,6);
        itemDefaultMinMaxValues("rotten_flesh-8",3,1,5);
        itemDefaultMinMaxValues("rotten_flesh-9",3,1,5);
        itemDefaultMinMaxValues("rotten_flesh-10",2,1,3);
        itemDefaultMinMaxValues("rotten_flesh-11",3,1,5);
        itemDefaultMinMaxValues("rotten_flesh-12",8,5,13);
        itemDefaultMinMaxValues("rotten_flesh-13",8,5,13);
        itemDefaultMinMaxValues("rotten_flesh-14",10,8,12);
        itemDefaultMinMaxValues("rotten_flesh-15",16,10,22);
        itemDefaultMinMaxValues("rotten_flesh-16",32,17,47);
        itemDefaultMinMaxValues("rotten_flesh-17",20,15,30);
        itemDefaultMinMaxValues("rotten_flesh-18",13,10,16);
        itemDefaultMinMaxValues("rotten_flesh-19",10,8,12);
        itemDefaultMinMaxValues("rotten_flesh-20",10,7,13);
        itemDefaultMinMaxValues("rotten_flesh-21",6,4,8);
        itemDefaultMinMaxValues("rotten_flesh-22",11,7,15);
        itemDefaultMinMaxValues("rotten_flesh-23",7,5,9);
        itemDefaultMinMaxValues("rotten_flesh-24",5,3,7);
        itemDefaultMinMaxValues("rotten_flesh-25",4,3,5);
        itemDefaultMinMaxValues("rotten_flesh-26",4,1,8);
        itemDefaultMinMaxValues("rotten_flesh-27",8,4,12);

        //도축 상점 가격
        itemDefaultMinMaxValues("mutton-0",1,0,2);
        itemDefaultMinMaxValues("chicken-0",1,0,2);
        itemDefaultMinMaxValues("rabbit-0",1,0,2);
        itemDefaultMinMaxValues("beef-0",1,0,2);
        itemDefaultMinMaxValues("porkchop-0",1,0,2);
        itemDefaultMinMaxValues("egg-0",1,0,2);
        itemDefaultMinMaxValues("feather-0",1,0,2);
        itemDefaultMinMaxValues("honeycomb-0",2,1,3);
        itemDefaultMinMaxValues("honey_bottle-0",3,1,6);
        itemDefaultMinMaxValues("cooked_mutton-0",2,1,3);
        itemDefaultMinMaxValues("cooked_chicken-0",2,1,3);
        itemDefaultMinMaxValues("cooked_rabbit-0",2,1,3);
        itemDefaultMinMaxValues("cooked_beef-0",2,1,3);
        itemDefaultMinMaxValues("cooked_porkchop-0",2,1,3);
        itemDefaultMinMaxValues("rabbit_hide-0",15,10,20);
        itemDefaultMinMaxValues("rabbit_food-0",16,10,22);
        itemDefaultMinMaxValues("leather-0",2,1,3);
        itemDefaultMinMaxValues("scute-0",15,10,20);
        itemDefaultMinMaxValues("white_wool-0",1,0,2);

        //요리 상점 가격
        itemDefaultMinMaxValues("beetroot_soup-10000",20,15,25);
        itemDefaultMinMaxValues("beetroot_soup-10001",60,50,70);
        itemDefaultMinMaxValues("bread-10001",2,1,3);
        itemDefaultMinMaxValues("bread-10002",3,1,5);
        itemDefaultMinMaxValues("bread-10003",17,14,20);
        itemDefaultMinMaxValues("bread-10005",20,15,25);
        itemDefaultMinMaxValues("bread-10006",20,15,25);
        itemDefaultMinMaxValues("bread-10007",3,1,5);
        itemDefaultMinMaxValues("bread-10008",4,2,6);
        itemDefaultMinMaxValues("bread-10009",6,4,8);
        itemDefaultMinMaxValues("bread-10010",19,14,23);
        itemDefaultMinMaxValues("bread-10011",55,45,65);
        itemDefaultMinMaxValues("bread-10021",1,1,1);
        itemDefaultMinMaxValues("bread-10013",13,10,16);
        itemDefaultMinMaxValues("bread-10014",15,10,20);
        itemDefaultMinMaxValues("bread-10015",15,10,20);
        itemDefaultMinMaxValues("bread-10016",3,1,5);
        itemDefaultMinMaxValues("bread-10017",5,3,7);
        itemDefaultMinMaxValues("bread-10018",20,15,25);
        itemDefaultMinMaxValues("bread-10019",45,35,55);
        itemDefaultMinMaxValues("bread-10020",10,5,15);
    }
    private void itemDefaultMinMaxValues(String type,int Default,int Min,int Max) {
        // 아이템별 기본값 및 최소값과 최대값 설정
        itemDefaultPrices.put(type, Default);
        itemMinValues.put(type, Min);
        itemMaxValues.put(type, Max);
    }

    private void updateItemCosts() {
        Calendar cal = Calendar.getInstance();
        int currentMinute = cal.get(Calendar.MINUTE);

        if (currentMinute == 0) {
            Random random = new Random();

            for (String itemName : itemDefaultPrices.keySet()) {
                int currentPrice = itemPrices.getOrDefault(itemName, itemDefaultPrices.get(itemName));
                int minValue = itemMinValues.getOrDefault(itemName, 0);
                int maxValue = itemMaxValues.getOrDefault(itemName, Integer.MAX_VALUE);

                // 현재 가격에서 변동 범위 내의 새로운 가격 계산
                int variation = new Random().nextInt(21) - 10;
                int newPrice = Math.min(Math.max(currentPrice + variation, minValue), maxValue);

                itemPrices.put(itemName, newPrice);
            }
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("server_info");
        Bukkit.getScheduler().runTaskLater(plugin, this::updateItemCosts, 20 * 60 * 60);
    }


    public int getItemCost(ItemStack itemStack, int customModelData) {
        Material itemType = itemStack.getType();
        String itemKey = itemType.toString().toLowerCase() + "-" + customModelData;

        int price = itemPrices.getOrDefault(itemKey, itemDefaultPrices.getOrDefault(itemKey, 0));
        int minValue = itemMinValues.getOrDefault(itemKey, 0);
        int maxValue = itemMaxValues.getOrDefault(itemKey, Integer.MAX_VALUE);

        // 가격이 최소값과 최대값 사이에 있도록 조정
        return Math.min(Math.max(price, minValue), maxValue);
    }
    public int itemCoinCost(ItemStack itemStack,int customModelData) {
        Material itemType = itemStack.getType();
        ItemMeta meta = itemStack.getItemMeta();
        if (itemType == Material.PAPER && customModelData == 5002) {
            return 2;
        }else if (itemType == Material.PAPER && customModelData == 167) {
            return 10;
        }else if (itemType == Material.PAPER && customModelData == 168) {
            return 30;
        }else if (itemType == Material.PAPER && customModelData == 169) {
            return 50;
        }else if (itemType == Material.ENCHANTED_BOOK && customModelData == 5003) {
            return 2;
        }else if (itemType == Material.BOOK && customModelData == 6000) {
            return 2;
        }
        return 0;
    }
}