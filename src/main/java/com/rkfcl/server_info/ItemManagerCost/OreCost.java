package com.rkfcl.server_info.ItemManagerCost;

import org.bukkit.Material;

public class OreCost {
    public int calculateIndividualCost(Material itemType) {
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
