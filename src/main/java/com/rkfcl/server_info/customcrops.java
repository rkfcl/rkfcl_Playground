package com.rkfcl.server_info;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;


public class customcrops implements Listener {

    private Plugin plugin;
    public static HashMap<Location, Integer> taskMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> cornstageMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> CabbageMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> OnionMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> Sweet_potatoMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> TomatoMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> RiceMap= new HashMap<Location, Integer>() ;

    public customcrops(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 우클릭한 블럭이 아닌 경우 무시합니다.
        }
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        CustomBlock custom = CustomBlock.getInstance("water_farmland");

        if (clickedBlock == null || !clickedBlock.getBlockData().equals(custom.getBaseBlockData())) {
            return; // 블럭이 없거나 Material.DIRT가 아닌 경우 무시합니다.
        }
        Location location = clickedBlock.getLocation().add(0, 1, 0);
        ItemMeta itemMeta = event.getItem().getItemMeta();
        ItemStack itemStack = event.getItem();
            if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("corn_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("corn_seed_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,cornstageMap, initialStage,3000, "corn_seed_stage_2", "corn_seed_stage_3", "corn_seed_stage_4", "corn_seed_stage_5");
                    taskMap.put(location, taskId);
                    cornstageMap.put(location,initialStage);
                }
            }else if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("cabbage_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("cabbage_seed_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,CabbageMap, initialStage,3800, "cabbage_seed_stage_2", "cabbage_seed_stage_3", "cabbage_seed_stage_4");
                    taskMap.put(location, taskId);
                    CabbageMap.put(location,initialStage);
                }
            }else if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("onion_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("onion_seed_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,OnionMap, initialStage,4000,"onion_seed_stage_2", "onion_seed_stage_3", "onion_seed_stage_4");
                    taskMap.put(location, taskId);
                    OnionMap.put(location,initialStage);
                }
            }else if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("sweet_potato_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("sweet_potato_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,Sweet_potatoMap, initialStage,5000, "sweet_potato_stage_2", "sweet_potato_stage_3");
                    taskMap.put(location, taskId);
                    Sweet_potatoMap.put(location,initialStage);
                }
            }else if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("tomato_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("tomato_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,TomatoMap, initialStage,2400, "tomato_stage_2", "tomato_stage_3", "tomato_stage_4","tomato_stage_5","tomato_stage_6");
                    taskMap.put(location, taskId);
                    TomatoMap.put(location,initialStage);
                }
            }else if (itemMeta != null && itemMeta.equals(CustomStack.getInstance("rice_seeds").getItemStack().getItemMeta())) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                CustomBlock customBlock = CustomBlock.getInstance("rice_stage_0");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location,TomatoMap, initialStage,2000, "rice_stage_1", "rice_stage_2", "rice_stage_3","rice_stage_4","rice_stage_5","rice_stage_6","rice_stage_7");
                    taskMap.put(location, taskId);
                    RiceMap.put(location,initialStage);
                }
            }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        // 아래에 있는 블록 위치 계산
        Location upLocation = location.clone().add(0, 1, 0);
        // 아래 블록이 부서지면 작물 제거
        if (taskMap.containsKey(upLocation)) {
            cancelTask(upLocation);
            CustomBlock.remove(upLocation);
        }
        if (taskMap.containsKey(location)) {
            cancelTask(location);
        }
    }
    @EventHandler
    public void watering_can(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 우클릭한 블럭이 아닌 경우 무시합니다.
        }
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block water = event.getClickedBlock().getRelative(event.getBlockFace());
        // 아이템과 블럭이 null이 아닌지 확인합니다.
        if (item == null || item.getItemMeta() == null || event.getClickedBlock() == null) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
            if (item.getType() == Material.SHEARS && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 10) {
                if (item.getDurability() == 212 || item.getDurability() == 238) {
                    ItemStack watering_can = ItemsAdder.getCustomItem("watering_can");
                    player.getInventory().setItemInMainHand(watering_can);
                }
            }
            if (item.getType() == Material.SHEARS && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 101) {
                if (water.getType() == Material.WATER) {
                    ItemStack watering_can = ItemsAdder.getCustomItem("watering_can_fill");
                    player.getInventory().setItemInMainHand(watering_can);
                }
            }

    }
    public int createGrowthTask(Location location,HashMap<Location, Integer> map, int initialStage,int delay, String... growthStages) {


        int taskId = new BukkitRunnable() {
            int stageIndex = initialStage;

            @Override
            public void run() {
                String currentStage = growthStages[stageIndex];
                CustomBlock currentBlock = CustomBlock.getInstance(currentStage);
                if (currentBlock != null) {
                    currentBlock.place(location);
                }

                stageIndex++;
                map.put(location,stageIndex);
                if (stageIndex >= growthStages.length) {
                    // 마지막 성장 단계에 도달했을 때 태스크 종료 및 농토 건조하게 바꿈
                    location.subtract(0,1,0);
                    CustomBlock.getInstance("farmland").place(location);
                    cancelTask(location);
                }
            }
        }.runTaskTimer(plugin, delay, delay).getTaskId();

        return taskId;
    }

    private void cancelTask(Location location) {
        if (taskMap.containsKey(location)) {
            int taskId = taskMap.get(location);
            Bukkit.getScheduler().cancelTask(taskId);
            taskMap.remove(location);
            cornstageMap.remove(location);
            CabbageMap.remove(location);
            OnionMap.remove(location);
            Sweet_potatoMap.remove(location);
            TomatoMap.remove(location);
            RiceMap.remove(location);
        }
    }

    public void cancelAllTasks() {
        for (int taskId : taskMap.values()) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        taskMap.clear();
        cornstageMap.clear();
        CabbageMap.clear();
        OnionMap.clear();
        Sweet_potatoMap.clear();
        TomatoMap.clear();
        RiceMap.clear();
    }


}

