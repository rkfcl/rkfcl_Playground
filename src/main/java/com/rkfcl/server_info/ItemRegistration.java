package com.rkfcl.server_info;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegistration {
    private static Map<ItemStack, Integer> registeredItems = new HashMap<>();

    public static void registerItem(ItemStack item, int price) {
        // 가격 정보 설정
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(price);
        lore.add("");
        lore.add(ChatColor.GOLD + "§l|"+ ChatColor.WHITE+" 가격: "+ formattedPrice);
        meta.setLore(lore);
        item.setItemMeta(meta);
        registeredItems.put(item, price);
    }

    public static void unregisterItem(ItemStack item) {
        registeredItems.remove(item);
    }

    public static boolean isItemRegistered(ItemStack item) {
        return registeredItems.containsKey(item);
    }

    public static int getItemPrice(ItemStack item) {
        return registeredItems.getOrDefault(item, 0);
    }

    // TODO: 필요한 경우 다른 유틸리티 메서드 추가

    // 예시로 등록된 아이템 목록을 반환하는 메서드
    public static Map<ItemStack, Integer> getRegisteredItems() {
        return registeredItems;
    }
}

