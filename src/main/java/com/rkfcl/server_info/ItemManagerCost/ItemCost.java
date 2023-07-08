package com.rkfcl.server_info.ItemManagerCost;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCost {
    public int itemCost(ItemStack itemStack,int customModelData) {
        Material itemType = itemStack.getType();
        ItemMeta meta = itemStack.getItemMeta();
        if (itemType == Material.PAPER && customModelData == 167) {
            return 10000;
        } else if (itemType == Material.PAPER && customModelData == 168) {
            return 30000;
        } else if (itemType == Material.PAPER && customModelData == 169) {
            return 50000;
        } else if (itemType == Material.PAPER && customModelData == 100) {
            return 500;
        } else if (itemType == Material.BREAD && customModelData == 10000) {
            return 3;
        }else if (itemType == Material.IRON_DOOR && customModelData == 1) {
            return 1000;
        }else if (itemType == Material.PAPER && customModelData == 5002) {
            return 2;
        }else if (itemType == Material.COD) {
            return 1;
        } else if (itemType == Material.TROPICAL_FISH) {
            return 3;
        } else if (itemType == Material.PUFFERFISH) {
            return 3;
        } else if (itemType == Material.SALMON) {
            return 2;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 1) {
            return 2;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 2) {
            return 10;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 3) {
            return 5;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 4) {
            return 4;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 5) {
            return 7;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 6) {
            return 5;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 70) {
            return 4;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 8) {
            return 3;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 9) {
            return 3;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 10) {
            return 2;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 11) {
            return 3;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 12) {
            return 8;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 13) {
            return 8;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 14) {
            return 10;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 15) {
            return 16;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 16) {
            return 32;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 17) {
            return 20;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 18) {
            return 13;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 19) {
            return 10;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 20) {
            return 10;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 21) {
            return 6;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 22) {
            return 11;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 23) {
            return 7;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 24) {
            return 5;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 25) {
            return 4;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 26) {
            return 4;
        } else if (itemType == Material.ROTTEN_FLESH && customModelData == 27) {
            return 8;
        }
        //광물 가격
        if (itemType == Material.FLINT) {
            return 1;
        } else if (itemType == Material.IRON_INGOT) {
            return 2;
        } else if (itemType == Material.GOLD_INGOT) {
            return 4;
        } else if (itemType == Material.DIAMOND) {
            return 50;
        } else if (itemType == Material.QUARTZ) {
            return 2;
        } else if (itemType == Material.AMETHYST_SHARD) {
            return 1;
        } else if (itemType == Material.COPPER_INGOT) {
            return 1;
        } else if (itemType == Material.EMERALD) {
            return 70;
        } else if (itemType == Material.NETHERITE_INGOT) {
            return 500;
        } else if (itemType == Material.COAL_BLOCK) {
            return 10;
        } else if (itemType == Material.LAPIS_BLOCK) {
            return 10;
        } else if (itemType == Material.REDSTONE_BLOCK) {
            return 10;
        } else if (itemType == Material.AMETHYST_BLOCK) {
            return 4;
        } else if (itemType == Material.OBSIDIAN) {
            return 3;
        } else if (itemType == Material.CRYING_OBSIDIAN) {
            return 60;
        } else if (itemType == Material.GLOWSTONE) {
            return 3;
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
        } else if (itemType == Material.BREAD && customModelData == 0) {
            return 6;
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
            return 25;
        } else if (itemType == Material.GLOW_BERRIES) {
            return 2;
        } else if (itemType == Material.SWEET_BERRIES) {
            return 1;
            //농부 2상점 아이템 가격
        } else if (itemType == Material.APPLE && customModelData == 10028) {
            return 15;
        } else if (itemType == Material.APPLE && customModelData == 10029) {
            return 18;
        } else if (itemType == Material.APPLE && customModelData == 10030) {
            return 15;
        } else if (itemType == Material.APPLE && customModelData == 10031) {
            return 17;
        } else if (itemType == Material.APPLE && customModelData == 10032) {
            return 16;
        } else if (itemType == Material.APPLE && customModelData == 10033) {
            return 13;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10001) {
            return 3;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10002) {
            return 5;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10003) {
            return 3;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10004) {
            return 6;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 10005) {
            return 3;
        } else if (itemType == Material.WHEAT_SEEDS && customModelData == 5) {
            return 4;
        } else if (itemType == Material.OAK_PLANKS && customModelData == 10000) {
            return 60;
        } else if (itemType == Material.SHEARS && customModelData == 101) {
            return 1000;
            //도축업자 아이템 가격
        } else if (itemType == Material.MUTTON && customModelData == 0) {
            return 1;
        } else if (itemType == Material.CHICKEN && customModelData == 0) {
            return 1;
        } else if (itemType == Material.RABBIT && customModelData == 0) {
            return 1;
        } else if (itemType == Material.BEEF && customModelData == 0) {
            return 1;
        } else if (itemType == Material.PORKCHOP && customModelData == 0) {
            return 1;
        } else if (itemType == Material.EGG && customModelData == 0) {
            return 2;
        } else if (itemType == Material.FEATHER && customModelData == 0) {
            return 1;
        } else if (itemType == Material.HONEYCOMB && customModelData == 0) {
            return 1;
        } else if (itemType == Material.HONEY_BOTTLE && customModelData == 0) {
            return 2;
        } else if (itemType == Material.COOKED_MUTTON && customModelData == 0) {
            return 2;
        } else if (itemType == Material.COOKED_CHICKEN && customModelData == 0) {
            return 2;
        } else if (itemType == Material.COOKED_RABBIT && customModelData == 0) {
            return 2;
        } else if (itemType == Material.COOKED_BEEF && customModelData == 0) {
            return 2;
        } else if (itemType == Material.COOKED_PORKCHOP && customModelData == 0) {
            return 2;
        } else if (itemType == Material.RABBIT_HIDE && customModelData == 0) {
            return 15;
        } else if (itemType == Material.RABBIT_FOOT && customModelData == 0) {
            return 16;
        } else if (itemType == Material.LEATHER && customModelData == 0) {
            return 1;
        } else if (itemType == Material.SCUTE && customModelData == 0) {
            return 15;
        }
            return 0;
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
        }
        return 0;
    }

}
