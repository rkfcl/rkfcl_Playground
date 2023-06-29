package com.rkfcl.server_info;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rkfcl.server_info.ItemReturn.ReturnedItems;
import static com.rkfcl.server_info.ItemReturn.returnItem;

public class ItemRegistration {
    public static Map<ItemStack, UUID> registeredItems = new HashMap<>();

    public static void registerItem(ItemStack item, int price, Player player) {
        // 가격 정보 설정
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(price);
        lore.add("");
        lore.add(ChatColor.GOLD + "§l|" + ChatColor.WHITE + " 가격: " + formattedPrice);
        lore.add("");

        // 판매 기한 설정
        LocalDateTime expirationDateTime = LocalDateTime.now().plusDays(1); // 판매 기한은 등록일로부터 하루
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"); // 판매 기한을 원하는 형식으로 포맷
        String formattedExpirationDate = expirationDateTime.format(formatter);
        lore.add(ChatColor.RED + "§l|" + ChatColor.WHITE + " 만료: " + formattedExpirationDate);
        lore.add("");
        lore.add(ChatColor.GREEN + "§l|" + ChatColor.WHITE + " 판매자: " + player.getDisplayName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        registeredItems.put(item, player.getUniqueId());

        removeExpiredItems();
    }
    public static void removeExpiredItems() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Iterator<Map.Entry<ItemStack, UUID>> iterator = registeredItems.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<ItemStack, UUID> entry = iterator.next();
            ItemStack item = entry.getKey();
            UUID playerUUID = entry.getValue();

            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasLore()) {
                List<String> lore = meta.getLore();
                if (lore != null && !lore.isEmpty()) {
                    String expirationLine = lore.get(lore.size() - 3); // 만료 기한이 있는 줄의 인덱스
                    String expirationDateText = expirationLine.substring(expirationLine.indexOf(":") + 2); // 만료 기한 날짜 텍스트 추출
                    LocalDateTime expirationDateTime = LocalDateTime.parse(expirationDateText, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
                    if (expirationDateTime.isBefore(currentDateTime)) {
                        returnItem(item, registeredItems.get(item)); // 아이템 반환
                        iterator.remove(); // iterator를 통해 안전하게 아이템 제거
                    }
                }
            }
        }
    }


    public static void unregisterItem(ItemStack item) {
        registeredItems.remove(item);
    }

    public static boolean isItemRegistered(ItemStack item) {
        return registeredItems.containsKey(item);
    }



    // TODO: 필요한 경우 다른 유틸리티 메서드 추가

    // 예시로 등록된 아이템 목록을 반환하는 메서드
    public static Map<ItemStack, UUID> getRegisteredItems() {
        return registeredItems;
    }
}

