package com.rkfcl.server_info.Manager;

import com.rkfcl.server_info.inventoryClickListener;
import com.rkfcl.server_info.test;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.block.data.Ageable;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AbilityManager implements Listener {
    private Map<UUID,ItemMeta> playerItems = new HashMap<>();
    private test plugin;
    private final PlayerDataManager playerDataManager;

    public AbilityManager(PlayerDataManager playerDataManager,test plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
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
            Material.WHEAT,
            Material.BEETROOTS,
            Material.MELON,
            Material.PUMPKIN,
            Material.CARROTS,
            Material.COCOA,
            Material.POTATOES

    );
    private final List<Material> targetSeedTypes = Arrays.asList(
            Material.WHEAT,
            Material.BEETROOTS,
            Material.CARROTS,
            Material.COCOA,
            Material.POTATOES

    );
    private List<String> foodNames = Arrays.asList(
            "collection_ptato_salad:감자 샐러드",
            "collection_vegetable_salad:야채 샐러드",
            "collection_fried_egg:계란 후라이",
            "collection_omelet:오믈렛",
            "collection_baked_fish:생선구이",
            "collection_perch_carp:도미,잉어",
            "collection_vegetable_medley:야채의 메들리",
            "collection_rice_spaghetti:스파게티",
            "collection_carp_surprise:깜짝 잉어",
            "collection_pancakes:팬케이크",
            "collection_trout_soup:송어 스프",
            "collection_tortilla:또띠아",
            "collection_fish_taco:생선 타코",
            "collection_salt:소금",
            "collection_fried_eel:장어튀김",
            "collection_maki_roll:마키 롤",
            "collection_rice_pudding:라이스 푸딩",
            "collection_ice_cream:아이스크림",
            "collection_pumpkin_soup:호박 죽",
            "collection_glazed_yams:고구마 맛탕",
            "collection_salmon_dinner:연어 정찬",
            "collection_crispy_bass:우럭 튀김"
    );
    private static final HashMap<Player, Integer> blockBreakCounts = new HashMap<>();

    private static void applySwiftnessEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0));
    }

    private static void applySwiftnessEffect2(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
    }

    public static void applyFireResistance(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
    }
    public static void applyWaterResistance(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
    }

    // 블록 파괴 횟수를 증가시키는 메서드
    private int increaseBlockBreakCount(Player player) {
        int currentCount = blockBreakCounts.getOrDefault(player, 0);
        int newCount = currentCount + 1;
        blockBreakCounts.put(player, newCount);
        return newCount;
    }

    //광부 능력
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material brokenBlockType = event.getBlock().getType();

        int blockBreakCount = increaseBlockBreakCount(player);
        int additionalOreAmount = 1;
        // 플레이어의 직업을 가져옴
        String job = playerDataManager.getPlayerJob(player.getUniqueId());
        if (job.startsWith("광부")) { // 블록 50개 캘 때마다 성급함 효과 부여
            if (blockBreakCount % 50 == 0) {
                blockBreakCounts.put(player, 0);
                if (job.equals("광부 1차") || job.equals("광부 2차")) {
                    applySwiftnessEffect(player);
                    player.sendMessage("성급함 1 효과를 획득하였습니다!");
                } else if (job.equals("광부 3차") || job.equals("광부 4차")) {
                    applySwiftnessEffect2(player);
                    player.sendMessage("성급함 2 효과를 획득하였습니다!");
                }
            }
        }
        if (job.startsWith("광부") || !job.contains("1차") || targetOreTypes.contains(brokenBlockType)) {
            // 10%의 확률로 광물 추가
            if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                // 추가될 광물의 종류와 양
                Material additionalOreMaterial = null;
                if (job.equals("광부 4차")) {
                    additionalOreAmount = 2;
                }

                switch (brokenBlockType) {
                    case COAL_ORE:
                    case DEEPSLATE_COAL_ORE:
                        additionalOreMaterial = Material.COAL;
                        break;
                    case IRON_ORE:
                    case DEEPSLATE_IRON_ORE:
                        additionalOreMaterial = Material.IRON_INGOT;
                        break;
                    case GOLD_ORE:
                        break;
                    case DEEPSLATE_GOLD_ORE:
                        additionalOreMaterial = Material.GOLD_INGOT;
                        break;
                    case DIAMOND_ORE:
                    case DEEPSLATE_DIAMOND_ORE:
                        additionalOreMaterial = Material.DIAMOND;
                        break;
                    case REDSTONE_ORE:
                    case DEEPSLATE_REDSTONE_ORE:
                        additionalOreMaterial = Material.REDSTONE;
                        break;
                    case LAPIS_ORE:
                    case DEEPSLATE_LAPIS_ORE:
                        additionalOreMaterial = Material.LAPIS_LAZULI;
                        break;
                    case EMERALD_ORE:
                    case DEEPSLATE_EMERALD_ORE:
                        additionalOreMaterial = Material.EMERALD;
                        break;
                    case NETHER_QUARTZ_ORE:
                        additionalOreMaterial = Material.QUARTZ;
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

    private static final Map<Material, Material> cropsToAdditionalItemMap = new HashMap<>();
    static {
        cropsToAdditionalItemMap.put(Material.WHEAT, Material.WHEAT);
        cropsToAdditionalItemMap.put(Material.CARROTS, Material.CARROTS);
        cropsToAdditionalItemMap.put(Material.PUMPKIN, Material.PUMPKIN);
        cropsToAdditionalItemMap.put(Material.MELON, Material.MELON);
        cropsToAdditionalItemMap.put(Material.BEETROOTS, Material.BEETROOT);
        cropsToAdditionalItemMap.put(Material.COCOA, Material.COCOA_BEANS);
        cropsToAdditionalItemMap.put(Material.POTATOES, Material.POTATO);
    }

    // 농작물 추가 메서드
    private void handleAdditionalCrops(Player player, Material brokenCropsType, int additionalCropsAmount) {
        Material additionalCropsMaterial = cropsToAdditionalItemMap.get(brokenCropsType);
        if (additionalCropsMaterial != null) {
            ItemStack additionalCropsItem = new ItemStack(additionalCropsMaterial, additionalCropsAmount);
            player.getInventory().addItem(additionalCropsItem);
            player.sendMessage("추가 농작물이 주어졌습니다!");
        }
    }

    //농부 능력
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCropsBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material brokenCropsType = event.getBlock().getType();

        // 플레이어의 직업을 가져옴
        String job = playerDataManager.getPlayerJob(player.getUniqueId());

        // 플레이어가 캐는 농작물이 타겟 농작물인지 확인
        if (job.startsWith("농부") && targetCropsTypes.contains(brokenCropsType)) {
            int additionalCropsAmount = job.contains("4차") ? 2 : 1;
            // 농작물이 다 자란 상태인지 확인
            boolean isFullyGrown = checkIfFullyGrown(block);
            // 농작물이 다 자란 상태일 때의 처리
            if (isFullyGrown && ThreadLocalRandom.current().nextDouble() < 0.1) { // 10% 확률로 농작물 추가+1
                handleAdditionalCrops(player, brokenCropsType, additionalCropsAmount);
            }
        }
        if (job.equals("농부 3차") || job.equals("농부 4차")) { // 일반 작물 자동 심기
            // 플레이어가 캐는 농작물이 타겟 농작물인지 확인
            if (targetSeedTypes.contains(brokenCropsType)) {
                boolean isFullyGrown = checkIfFullyGrown(block);
                if (isFullyGrown) {
                    switch (brokenCropsType) {
                        case WHEAT:
                            if (player.getInventory().contains(Material.WHEAT_SEEDS)) {
                                player.getInventory().removeItem(new ItemStack(Material.WHEAT_SEEDS, 1));
                            }
                            break;
                        case CARROTS:
                            if (player.getInventory().contains(Material.CARROT)) {
                                player.getInventory().removeItem(new ItemStack(Material.CARROT, 1));
                            }
                            break;
                        case BEETROOTS:
                            if (player.getInventory().contains(Material.BEETROOT_SEEDS)) {
                                player.getInventory().removeItem(new ItemStack(Material.BEETROOT_SEEDS, 1));
                            }
                            break;
                        case POTATOES:
                            if (player.getInventory().contains(Material.POTATO)) {
                                player.getInventory().removeItem(new ItemStack(Material.POTATO, 1));
                            }
                            break;
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // 클릭한 위치에 씨앗 다시 심기
                            Block soilBlock = block.getRelative(BlockFace.DOWN);
                            if (soilBlock.getType() == Material.FARMLAND) {
                                soilBlock.getRelative(BlockFace.UP).setType(brokenCropsType);
                            }
                        }
                    }.runTaskLater(this.plugin, 1);
                }
            }
        }
    }


    //요리사 직업 능력
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack resultItem = event.getRecipe().getResult();
        String foodName = resultItem.getItemMeta().getDisplayName();
        String job = playerDataManager.getPlayerJob(player.getUniqueId());
        if (job.contains("요리사")) {
            // 대소문자를 모두 소문자로 변환하고 색 코드 제거 후 공백을 모두 제거하여 처리
            String formattedFoodName = ChatColor.stripColor(foodName).toLowerCase().replaceAll("\\s+", "");

            for (String food : foodNames) {
                String[] parts = food.split(":");
                String itemName = parts[1].toLowerCase().replaceAll("\\s+", "");
                if (formattedFoodName.equals(itemName)) {
                    if (job.contains("1차")) {
                        if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                            player.getInventory().addItem(resultItem);
                            player.sendMessage("추가 요리가 주어졌습니다!");
                        }
                    }else if (job.contains("2차")) {
                        if (ThreadLocalRandom.current().nextDouble() < 0.1) {
                            player.getInventory().addItem(resultItem);
                            player.getInventory().addItem(resultItem);
                            player.sendMessage("추가 요리가 주어졌습니다!");
                        }
                    }else if (job.contains("3차")) {
                        if (ThreadLocalRandom.current().nextDouble() < 0.2) {
                            player.getInventory().addItem(resultItem);
                            player.sendMessage("추가 요리가 주어졌습니다!");
                        }
                    }else if (job.contains("4차")) {
                        if (ThreadLocalRandom.current().nextDouble() < 0.3) {
                            player.getInventory().addItem(resultItem);
                            player.getInventory().addItem(resultItem);
                            player.sendMessage("추가 요리가 주어졌습니다!");
                        }
                    }else
                    break;
                }
            }
        }
    }

    private boolean checkIfFullyGrown(Block block) {
        BlockState blockState = block.getState();
        if(block.getType()==Material.MELON||block.getType()==Material.PUMPKIN){
            return true;
        }
        if (blockState.getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) blockState.getBlockData();
            return ageable.getAge() == ageable.getMaximumAge();
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String job = playerDataManager.getPlayerJob(player.getUniqueId());
        int amount = playerDataManager.getPlayerBalance(player.getUniqueId());
        if (job.equals("초보자")) {
            // 아이템 드롭 방지
            event.setKeepInventory(true);
            event.getDrops().clear();

            // 착용장비 드롭
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            }
            player.getInventory().setArmorContents(null);
        } else {
            boolean hasCustomItem = false;
            ItemStack invensave = null; // 아이템을 저장할 변수 선언

            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == Material.PAPER && item.getItemMeta().getCustomModelData() == 5002) {
                    invensave = item; // 아이템 저장
                    hasCustomItem = true;
                    break;
                }
            }
            if (hasCustomItem) {
                // 아이템 드롭 방지
                if (invensave != null) {
                    inventoryClickListener.removeItems(player.getInventory(),Material.PAPER,5002,1);
                }
                event.setKeepInventory(true);
                event.getDrops().clear();
            } else {
                if (amount!=0){
                    ItemStack check = ItemManager.createCheck(amount);
                    event.getDrops().add(check);
                }
                playerDataManager.setPlayerBalance(player.getUniqueId(), 0);
                plugin.updateScoreboard(player);
            }
        }
    }

    //can not pvp
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // 플레이어가 다른 플레이어를 공격할 때 이벤트 처리
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
            if (damageByEntityEvent.getDamager() instanceof Player && damageByEntityEvent.getEntity() instanceof Player) {
                // PvP 공격을 취소하고 데미지를 제거
                event.setCancelled(true);
                event.setDamage(0);
            }
        }
    }
    //pvp arrow damage cancel
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }
}