package com.rkfcl.server_info.ItemManagerCost;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCost {
    public int itemCost(ItemStack itemStack,int customModelData) {
        Material itemType = itemStack.getType();
        ItemMeta meta = itemStack.getItemMeta();

        if (itemType == Material.COD) {
            return 1;
        } else if (itemType == Material.TROPICAL_FISH) {
            return 3;
        } else if (itemType == Material.PUFFERFISH) {
            return 3;
        } else if (itemType == Material.SALMON) {
            return 3;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 1) {
            return 1;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 2) {
            return 10;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 3) {
            return 5;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 4) {
            return 13;
        }
        //광물 가격
        if (itemType == Material.FLINT) {
            return 1;
        } else if (itemType == Material.IRON_INGOT) {
            return 3;
        } else if (itemType == Material.GOLD_INGOT) {
            return 0;
        } else if (itemType == Material.DIAMOND) {
            return 50;
        } else if (itemType == Material.QUARTZ) {
            return 0;
        } else if (itemType == Material.AMETHYST_SHARD) {
            return 0;
        } else if (itemType == Material.COPPER_INGOT) {
            return 2;
        } else if (itemType == Material.EMERALD) {
            return 0;
        } else if (itemType == Material.NETHERITE_INGOT) {
            return 0;
        } else if (itemType == Material.COAL_BLOCK) {
            return 0;
        } else if (itemType == Material.LAPIS_BLOCK) {
            return 0;
        } else if (itemType == Material.REDSTONE_BLOCK) {
            return 0;
        } else if (itemType == Material.OBSIDIAN) {
            return 0;
        } else if (itemType == Material.CRYING_OBSIDIAN) {
            return 0;
        } else if (itemType == Material.GLOWSTONE) {
            return 0;
        }
        return 0;
    }

}
