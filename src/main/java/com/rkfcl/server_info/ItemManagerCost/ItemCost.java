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

        // 농부 상점 아이템 가격
        if (itemType == Material.PUMPKIN) {
            return 3;
        } else if (itemType == Material.CARROT) {
            return 1;
        } else if (itemType == Material.BAKED_POTATO) {
            return 1;
        } else if (itemType == Material.POISONOUS_POTATO) {
            return 0;
        } else if (itemType == Material.BEETROOT) {
            return 1;
        } else if (itemType == Material.WHEAT) {
            return 2;
        } else if (itemType == Material.BREAD) {
            return 4;
        } else if (itemType == Material.PUMPKIN_PIE) {
            return 1;
        } else if (itemType == Material.MELON) {
            return 3;
        } else if (itemType == Material.GOLDEN_CARROT) {
            return 10;
        } else if (itemType == Material.SUGAR_CANE) {
            return 1;
        } else if (itemType == Material.APPLE && customModelData == 0) {
            return 1;
        } else if (itemType == Material.GOLDEN_APPLE) {
            return 5;
        } else if (itemType == Material.GLISTERING_MELON_SLICE) {
            return 2;
        } else if (itemType == Material.NETHER_WART) {
            return 1;
        } else if (itemType == Material.CAKE) {
            return 5;
        } else if (itemType == Material.GLOW_BERRIES) {
            return 2;
        } else if (itemType == Material.SWEET_BERRIES) {
            return 1;
        } else if (itemType == Material.APPLE && customModelData == 10028) {
            return 28;
        } else if (itemType == Material.APPLE && customModelData == 10029) {
            return 29;
        } else if (itemType == Material.APPLE && customModelData == 10030) {
            return 30;
        } else if (itemType == Material.APPLE && customModelData == 10031) {
            return 31;
        } else if (itemType == Material.APPLE && customModelData == 10032) {
            return 32;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10001) {
            return 1;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10002) {
            return 2;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10003) {
            return 3;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10004) {
            return 4;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10005) {
            return 5;
        }
        return 0;
    }

}
