package com.rkfcl.server_info.Manager;

import com.rkfcl.server_info.test;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.block.data.Ageable;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AbilityManager implements Listener {
    private Plugin plugin;
    private PlayerDataManager playerDataManager;
    public AbilityManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }
    private final List<Material> targetOreTypes = Arrays.asList(
            Material.COAL_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.EMERALD_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE

    );

    private final List<Material> targetCropsTypes = Arrays.asList(
            Material.WHEAT

    );
    private static HashMap<Player, Integer> blockBreakCounts = new HashMap<>();

    private static void applySwiftnessEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0));
    }

    private static void applySwiftnessEffect2(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
    }

    public static void applyFireResistance(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
    }

    private static int getBlockBreakCount(Player player) {
        return blockBreakCounts.getOrDefault(player, 0);
    }

    // 블록 파괴 이벤트를 추적하여 블록 파괴 카운트를 증가시키는 메서드
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material brokenBlockType = event.getBlock().getType();
        // 인식할 광물 종류
        Material targetOreType = Material.DIAMOND_ORE;
        int currentCount = getBlockBreakCount(player);
        blockBreakCounts.put(player, currentCount + 1);
        // 추가적인 로직을 수행할 수 있음
        // 직업별로 능력을 부여하는 로직을 작성
        Plugin pluginInstance = Bukkit.getPluginManager().getPlugin("server_info");

        // 플레이어의 직업을 가져옴
        String job = playerDataManager.getPlayerJob(player.getUniqueId());
        if (job.equals("광부 1차")) { // 블록 50개 캘 때마다 성급함 1 10초 부여
            int blockBreakCount = getBlockBreakCount(player);
            if (blockBreakCount % 50 == 0) {
                blockBreakCounts.put(player, 0);
                applySwiftnessEffect(player);
                player.sendMessage("성급함 1 효과를 획득하였습니다!");
            }
        }else if(job.equals("광부 3차")||job.equals("광부 4차")){
            int blockBreakCount = getBlockBreakCount(player);
            if (blockBreakCount % 50 == 0) {
                blockBreakCounts.put(player, 0);
                applySwiftnessEffect2(player);
                player.sendMessage("성급함 2 효과를 획득하였습니다!");
            }
        }
        if (job.equals("광부 2차")||job.equals("광부 3차")||job.equals("광부 4차")) {
            // 플레이어가 캐는 광물이 타겟 광물인지 확인
            if (targetOreTypes.contains(brokenBlockType)) {
                // 10%의 확률로 광물 추가
                if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                    // 추가될 광물의 종류와 양
                    Material additionalOreMaterial;
                    int additionalOreAmount;

                    switch (brokenBlockType) {
                        case COAL_ORE:
                            additionalOreMaterial = Material.COAL;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_COAL_ORE:
                            additionalOreMaterial = Material.COAL;
                            additionalOreAmount = 1;
                            break;
                        case IRON_ORE:
                            additionalOreMaterial = Material.IRON_INGOT;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_IRON_ORE:
                            additionalOreMaterial = Material.IRON_INGOT;
                            additionalOreAmount = 1;
                            break;
                        case GOLD_ORE:
                            additionalOreMaterial = Material.GOLD_INGOT;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_GOLD_ORE:
                            additionalOreMaterial = Material.GOLD_INGOT;
                            additionalOreAmount = 1;
                            break;
                        case DIAMOND_ORE:
                            additionalOreMaterial = Material.DIAMOND;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_DIAMOND_ORE:
                            additionalOreMaterial = Material.DIAMOND;
                            additionalOreAmount = 1;
                            break;
                        case REDSTONE_ORE:
                            additionalOreMaterial = Material.REDSTONE;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_REDSTONE_ORE:
                            additionalOreMaterial = Material.REDSTONE;
                            additionalOreAmount = 1;
                            break;
                        case LAPIS_ORE:
                            additionalOreMaterial = Material.LAPIS_LAZULI;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_LAPIS_ORE:
                            additionalOreMaterial = Material.LAPIS_LAZULI;
                            additionalOreAmount = 1;
                            break;
                        case EMERALD_ORE:
                            additionalOreMaterial = Material.EMERALD;
                            additionalOreAmount = 1;
                            break;
                        case DEEPSLATE_EMERALD_ORE:
                            additionalOreMaterial = Material.EMERALD;
                            additionalOreAmount = 1;
                            break;
                        case NETHER_QUARTZ_ORE:
                            additionalOreMaterial = Material.QUARTZ;
                            additionalOreAmount = 1;
                            break;
                        default:
                            return;
                    }
                    // 광물 추가 아이템 생성
                    ItemStack additionalOreItem = new ItemStack(additionalOreMaterial, additionalOreAmount);

                    // 플레이어에게 광물 추가 아이템 주기
                    player.getInventory().addItem(additionalOreItem);
                    player.sendMessage("추가 광물이 주어졌습니다!");
                }
            }
        }
        if (job.equals("광부 3차")||job.equals("광부 4차")) {
            // 플레이어가 캐는 광물이 타겟 광물인지 확인
            if (targetOreTypes.contains(brokenBlockType)) {
                // 10%의 확률로 광물 추가
                if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                    // 추가될 광물의 종류와 양
                    Material additionalOreMaterial;
                    int additionalOreAmount;

                    switch (brokenBlockType) {
                        case COAL_ORE:
                            additionalOreMaterial = Material.COAL;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_COAL_ORE:
                            additionalOreMaterial = Material.COAL;
                            additionalOreAmount = 2;
                            break;
                        case IRON_ORE:
                            additionalOreMaterial = Material.IRON_INGOT;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_IRON_ORE:
                            additionalOreMaterial = Material.IRON_INGOT;
                            additionalOreAmount = 2;
                            break;
                        case GOLD_ORE:
                            additionalOreMaterial = Material.GOLD_INGOT;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_GOLD_ORE:
                            additionalOreMaterial = Material.GOLD_INGOT;
                            additionalOreAmount = 2;
                            break;
                        case DIAMOND_ORE:
                            additionalOreMaterial = Material.DIAMOND;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_DIAMOND_ORE:
                            additionalOreMaterial = Material.DIAMOND;
                            additionalOreAmount = 2;
                            break;
                        case REDSTONE_ORE:
                            additionalOreMaterial = Material.REDSTONE;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_REDSTONE_ORE:
                            additionalOreMaterial = Material.REDSTONE;
                            additionalOreAmount = 2;
                            break;
                        case LAPIS_ORE:
                            additionalOreMaterial = Material.LAPIS_LAZULI;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_LAPIS_ORE:
                            additionalOreMaterial = Material.LAPIS_LAZULI;
                            additionalOreAmount = 2;
                            break;
                        case EMERALD_ORE:
                            additionalOreMaterial = Material.EMERALD;
                            additionalOreAmount = 2;
                            break;
                        case DEEPSLATE_EMERALD_ORE:
                            additionalOreMaterial = Material.EMERALD;
                            additionalOreAmount = 2;
                            break;
                        case NETHER_QUARTZ_ORE:
                            additionalOreMaterial = Material.QUARTZ;
                            additionalOreAmount = 2;
                            break;
                        default:
                            return;
                    }

                    // 광물 추가 아이템 생성
                    ItemStack additionalOreItem = new ItemStack(additionalOreMaterial, additionalOreAmount);

                    // 플레이어에게 광물 추가 아이템 주기
                    player.getInventory().addItem(additionalOreItem);
                    player.sendMessage("추가 광물이 주어졌습니다!");
                }
            }
        }

    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCropsBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material brokenCropsType = event.getBlock().getType();

        // 추가적인 로직을 수행할 수 있음
        // 직업별로 능력을 부여하는 로직을 작성
        Plugin pluginInstance = Bukkit.getPluginManager().getPlugin("server_info");

        // 플레이어의 직업을 가져옴
        String job = playerDataManager.getPlayerJob(player.getUniqueId());
        if (job.equals("농부 1차")) { // 10% 확률로 농작물 추가+1
            // 플레이어가 캐는 농작물이 타겟 농작물인지 확인
            if (brokenCropsType == Material.WHEAT) {
                player.sendMessage("작물확인");
                // 농작물이 다 자란 상태인지 확인
                BlockState blockState = event.getBlock().getState();
                if (blockState instanceof Ageable) {
                    Ageable ageable = (Ageable) blockState;
                    if (ageable.getAge() >= ageable.getMaximumAge()) {
                        player.sendMessage("다자란지 확인");
                        // 추가될 광물의 종류와 양
                        Material additionalCropsMaterial = Material.COAL;
                        int additionalCropsAmount = 1;

                        // 10%의 확률로 농작물 추가
                        if (ThreadLocalRandom.current().nextDouble() < 0.9) {
                            // 농작물 추가 아이템 생성
                            ItemStack additionalCropsItem = new ItemStack(additionalCropsMaterial, additionalCropsAmount);

                            // 플레이어에게 농작물 추가 아이템 주기
                            player.getInventory().addItem(additionalCropsItem);
                            player.sendMessage("추가 농작물이 주어졌습니다!");
                        }
                    }
                }
            }
        }
    }





    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String job = playerDataManager.getPlayerJob(player.getUniqueId());

        if (job.equals("초보자")) {
            // 아이템 드롭 방지
            event.setKeepInventory(true);
            event.getDrops().clear();
            //착용장비 드랍
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            }
            player.getInventory().setArmorContents(null);
        }
    }
}

