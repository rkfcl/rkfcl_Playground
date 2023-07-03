package com.rkfcl.server_info;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.*;

public class ItemReturn {
    public static Map<ItemStack, UUID> ReturnedItems = new LinkedHashMap<>();
    public static void returnItem(ItemStack item, UUID uuid) {
        ReturnedItems.put(item, uuid);
    }



    public static void unReturnItem(ItemStack item) {
        ReturnedItems.remove(item);
    }
    public static Map<ItemStack, UUID> getReturnedItemsItems() {
        return ReturnedItems;
    }
}
