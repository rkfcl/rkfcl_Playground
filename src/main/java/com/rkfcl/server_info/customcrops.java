package com.rkfcl.server_info;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;


public class customcrops implements Listener {

    private Plugin plugin;
    public static HashMap<Location, Integer> taskMap= new HashMap<Location, Integer>() ;
    public static HashMap<Location, Integer> cornstageMap= new HashMap<Location, Integer>() ;

    public customcrops(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        if (block.getType() == Material.WHEAT) {
            ItemMeta itemMeta = event.getItemInHand().getItemMeta();
            if (itemMeta != null && itemMeta.getCustomModelData() == 10001) {
                CustomBlock customBlock = CustomBlock.getInstance("corn_seed_stage_1");
                int initialStage = 0;
                if (customBlock != null) {
                    customBlock.place(location);
                    int taskId = createGrowthTask(location, initialStage, "corn_seed_stage_2", "corn_seed_stage_3", "corn_seed_stage_4", "corn_seed_stage_5");
                    taskMap.put(location, taskId);
                    cornstageMap.put(location,initialStage);
                }
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


    public int createGrowthTask(Location location, int initialStage, String... growthStages) {
        int delay = 500; // 작물이 자라는 시간 간격 (틱 단위)

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
                cornstageMap.put(location,stageIndex);
                if (stageIndex >= growthStages.length) {
                    // 마지막 성장 단계에 도달했을 때 태스크 종료
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
        }
    }

    public void cancelAllTasks() {
        for (int taskId : taskMap.values()) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        taskMap.clear();
        cornstageMap.clear();
    }


}

