package com.rkfcl.server_info.ItemManagerCost;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.util.*;

public class ItemCost {
    public static Map<String, Integer> itemPrices = new HashMap<>();
    public static Map<String, Integer> itemDefaultPrices = new HashMap<>();
    private Map<String, Integer> itemMinValues = new HashMap<>();
    private Map<String, Integer> itemMaxValues = new HashMap<>();

    public ItemCost() {
        initItemValues();
    }

    private void initItemValues() {
        //전직 교관 상점 가격
        //광부
        itemDefaultMinMaxValues("paper-200",3000,3000,3000);
        itemDefaultMinMaxValues("paper-201",7000,7000,7000);
        itemDefaultMinMaxValues("paper-202",30000,30000,30000);
        itemDefaultMinMaxValues("paper-203",55000,55000,55000);
        //농부
        itemDefaultMinMaxValues("paper-204",5000,5000,5000);
        itemDefaultMinMaxValues("paper-205",10000,10000,10000);
        itemDefaultMinMaxValues("paper-206",30000,30000,30000);
        itemDefaultMinMaxValues("paper-207",55000,55000,55000);
        //어부
        itemDefaultMinMaxValues("paper-208",5000,5000,5000);
        itemDefaultMinMaxValues("paper-209",8000,8000,8000);
        itemDefaultMinMaxValues("paper-210",30000,30000,30000);
        itemDefaultMinMaxValues("paper-211",45000,45000,45000);
        //요리사
        itemDefaultMinMaxValues("paper-212",5000,5000,5000);
        itemDefaultMinMaxValues("paper-213",10000,10000,10000);
        itemDefaultMinMaxValues("paper-214",25000,25000,25000);
        itemDefaultMinMaxValues("paper-215",40000,40000,40000);
        //초기화
        itemDefaultMinMaxValues("paper-216",10000,10000,10000);

        //잡화 상점 가격
        itemDefaultMinMaxValues("paper-167",10000,10000,10000);
        itemDefaultMinMaxValues("paper-168",30000,30000,30000);
        itemDefaultMinMaxValues("paper-169",50000,50000,50000);
        itemDefaultMinMaxValues("iron_door-1",1000,1000,1000);
        itemDefaultMinMaxValues("paper-100",500,500,500);
        itemDefaultMinMaxValues("paper-5005",500,500,500);
        itemDefaultMinMaxValues("bread-10000",3,3,3);
        itemDefaultMinMaxValues("arrow-10000",30000,30000,300000);
        itemDefaultMinMaxValues("tipped_arrow-10000",15000,15000,150000);

        //광부 상점 가격
        itemDefaultMinMaxValues("flint-0",1,0,2);
        itemDefaultMinMaxValues("iron_ingot-0",2,1,4);
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
        itemDefaultMinMaxValues("carrot-0",1,1,2);
        itemDefaultMinMaxValues("baked_potato-0",1,1,2);
        itemDefaultMinMaxValues("poisonous_potato-0",0,0,1);
        itemDefaultMinMaxValues("beetroot-0",1,1,2);
        itemDefaultMinMaxValues("wheat-0",2,1,3);
        itemDefaultMinMaxValues("bread-0",6,1,9);
        itemDefaultMinMaxValues("pumpkin_pie-0",7,5,9);
        itemDefaultMinMaxValues("melon-0",3,1,5);
        itemDefaultMinMaxValues("golden_carrot-0",7,5,10);
        itemDefaultMinMaxValues("sugar_cane-0",1,1,2);
        itemDefaultMinMaxValues("apple-0",1,1,2);
        itemDefaultMinMaxValues("golden_apple-0",30,15,45);
        itemDefaultMinMaxValues("glistering_melon_slice-0",3,1,5);
        itemDefaultMinMaxValues("nether_wart-0",3,1,5);
        itemDefaultMinMaxValues("cake-0",25,15,35);
        itemDefaultMinMaxValues("glow_berries-0",2,1,3);
        itemDefaultMinMaxValues("sweet_berries-0",1,1,2);
        itemDefaultMinMaxValues("cocoa_beans-0",1,1,2);

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
        itemDefaultMinMaxValues("cod-0",3,2,4);
        itemDefaultMinMaxValues("tropical_fish-0",5,4,6);
        itemDefaultMinMaxValues("pufferfish-0",5,4,6);
        itemDefaultMinMaxValues("salmon-0",5,4,6);
        itemDefaultMinMaxValues("rotten_flesh-1",5,4,6);
        itemDefaultMinMaxValues("rotten_flesh-2",13,10,16);
        itemDefaultMinMaxValues("rotten_flesh-3",8,6,10);
        itemDefaultMinMaxValues("rotten_flesh-4",7,5,9);
        itemDefaultMinMaxValues("rotten_flesh-5",10,7,13);
        itemDefaultMinMaxValues("rotten_flesh-6",8,6,10);
        itemDefaultMinMaxValues("rotten_flesh-7",7,5,9);
        itemDefaultMinMaxValues("rotten_flesh-8",6,4,8);
        itemDefaultMinMaxValues("rotten_flesh-9",6,4,8);
        itemDefaultMinMaxValues("rotten_flesh-10",5,4,6);
        itemDefaultMinMaxValues("rotten_flesh-11",6,4,8);
        itemDefaultMinMaxValues("rotten_flesh-12",13,10,18);
        itemDefaultMinMaxValues("rotten_flesh-13",12,9,17);
        itemDefaultMinMaxValues("rotten_flesh-14",14,12,16);
        itemDefaultMinMaxValues("rotten_flesh-15",20,14,26);
        itemDefaultMinMaxValues("rotten_flesh-16",36,22,50);
        itemDefaultMinMaxValues("rotten_flesh-17",23,18,33);
        itemDefaultMinMaxValues("rotten_flesh-18",16,13,19);
        itemDefaultMinMaxValues("rotten_flesh-19",13,11,15);
        itemDefaultMinMaxValues("rotten_flesh-20",12,9,15);
        itemDefaultMinMaxValues("rotten_flesh-21",9,7,11);
        itemDefaultMinMaxValues("rotten_flesh-22",15,11,19);
        itemDefaultMinMaxValues("rotten_flesh-23",10,8,12);
        itemDefaultMinMaxValues("rotten_flesh-24",8,6,10);
        itemDefaultMinMaxValues("rotten_flesh-25",7,5,9);
        itemDefaultMinMaxValues("rotten_flesh-26",7,4,11);
        itemDefaultMinMaxValues("rotten_flesh-27",11,7,15);

        //도축 상점 가격
        itemDefaultMinMaxValues("mutton-0",1,1,2);
        itemDefaultMinMaxValues("chicken-0",1,1,2);
        itemDefaultMinMaxValues("rabbit-0",1,1,2);
        itemDefaultMinMaxValues("beef-0",1,1,2);
        itemDefaultMinMaxValues("porkchop-0",1,1,2);
        itemDefaultMinMaxValues("egg-0",1,1,2);
        itemDefaultMinMaxValues("feather-0",1,1,2);
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

        //코인상점
        itemDefaultMinMaxValues("paper-167",10,10,10);
        itemDefaultMinMaxValues("paper-168",30,30,30);
        itemDefaultMinMaxValues("paper-169",50,50,50);
        itemDefaultMinMaxValues("paper-300",5,5,5);
        itemDefaultMinMaxValues("paper-5002",2,2,2);
        itemDefaultMinMaxValues("enchanted_book-5003",2,2,2);
        itemDefaultMinMaxValues("book-6000",2,2,2);
        itemDefaultMinMaxValues("bow-10000",20,20,20);
        itemDefaultMinMaxValues("shield-10000",20,20,20);
    }
    private void itemDefaultMinMaxValues(String type,int Default,int Min,int Max) {
        // 아이템별 기본값 및 최소값과 최대값 설정
        itemDefaultPrices.put(type, Default);
        itemMinValues.put(type, Min);
        itemMaxValues.put(type, Max);
    }
    public void updateItemCosts() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("server_info");
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // 현재 시간을 가져옵니다.
            Date now = new Date();

            // 현재 시간을 분 단위로 변환합니다.
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String currentTime = format.format(now);
            int currentHour = Integer.parseInt(currentTime.split(":")[0]);
            int currentMinute = Integer.parseInt(currentTime.split(":")[1]);

            // 00:00에서 00:01 사이인지 확인합니다.
            if (currentMinute >= 0 && currentMinute < 1) {
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

        }, 0L, 20 * 60L); // 1분(60초)마다 작업을 실행합니다.
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
        }else if (itemType == Material.PAPER && customModelData == 171) {
            return 5;
        }else if (itemType == Material.ENCHANTED_BOOK && customModelData == 5003) {
            return 2;
        }else if (itemType == Material.BOOK && customModelData == 6000) {
            return 2;
        }else if (itemType == Material.BOW && customModelData == 10000) {
            return 20;
        }else if (itemType == Material.SHIELD && customModelData == 10000) {
            return 20;
        }
        return 0;
    }
}