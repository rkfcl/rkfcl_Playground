package com.rkfcl.server_info;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProtectBlock implements Listener {
    private Plugin plugin;
    public static HashMap<Location, List<UUID>> protectMap = new HashMap<>();

    public ProtectBlock(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void protectBlockPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 우클릭한 블록이 아닌 경우 무시합니다.
        }
        Block clickedBlock = event.getClickedBlock();
        Location location = clickedBlock.getLocation();
        ItemMeta itemMeta = event.getItem().getItemMeta();
        if (itemMeta != null && itemMeta.getCustomModelData() == 166) {
            // 좌표를 맨 아래 바닥 정 가운데로 설정합니다.
            int x = location.getBlockX() - 5;
            int y = location.getBlockY();
            int z = location.getBlockZ() - 5;

            boolean overlappingBlocks = false;

            // 추가로 확인한 좌표가 protectMap에 이미 존재하는지 확인합니다.
            for (int i = -10; i <= 20; i++) {
                for (int j = -10; j <= 20; j++) {
                    for (int k = -10; k <= 20; k++) {
                        Location extraLocation = new Location(location.getWorld(), x + i, y + j, z + k);
                        if (protectMap.containsKey(extraLocation)) {
                            overlappingBlocks = true;
                            break;
                        }
                    }
                }
            }

            if (overlappingBlocks) {
                player.sendMessage("§c경고: 주변 건차랑 너무 가깝습니다!");
                return; // 이미 보호된 영역이 있으므로 더 이상 진행하지 않습니다.
            }

            // 11x11x11 크기의 정사각형의 모든 좌표를 protectMap에 저장합니다.
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    for (int k = 0; k < 11; k++) {
                        Location blockLocation = new Location(location.getWorld(), x + i, y + j, z + k);
                        // 중심 좌표 위로 3칸에 한 개의 흙 블럭 설치
                        if (j == 2 && i == 5 && k == 5) {
                            Block dirtBlock = blockLocation.getBlock().getRelative(0, 1, 0);
                            dirtBlock.setType(Material.DIRT);
                        }
                        // 특정 위치에 플레이어 UUID 추가
                        addAllowedPlayer(blockLocation,player.getUniqueId());

                        if (j == 0) {
                            if (i == 0 || i == 10 || k == 0 || k == 10) {
                                Block block = blockLocation.getBlock();
                                block.setType(Material.RED_CONCRETE);
                            }
                        } else if (j == 10) {
                            if (i == 0 || i == 10 || k == 0 || k == 10) {
                                Block block = blockLocation.getBlock();
                                block.setType(Material.RED_STAINED_GLASS);
                            }
                        }
                        if (j>0) {
                            if ((i == 0 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 0) || (i == 10 & k == 10)) {
                                Block block = blockLocation.getBlock();
                                block.setType(Material.RED_STAINED_GLASS);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        if (protectMap.containsKey(location)) {
            List<UUID> allowedPlayers = protectMap.get(location);
            UUID name = allowedPlayers.get(0);

            if (allowedPlayers.contains(player.getUniqueId())) {
                player.sendMessage("§6[ 건차 ] §f"+Bukkit.getPlayer(name).getName()+"님의 §c건설 차단 §f구역입니다.");
                event.setCancelled(true);
            }
        }
    }

    // 특정 위치에 플레이어 UUID 추가
    public static void addAllowedPlayer(Location location, UUID playerUUID) {
        List<UUID> allowedPlayers = protectMap.get(location);
        if (allowedPlayers == null) {
            allowedPlayers = new ArrayList<>();
            protectMap.put(location, allowedPlayers);
        }
        allowedPlayers.add(playerUUID);
    }

    // 특정 위치에서 플레이어 UUID 제거
    public static void removeAllowedPlayer(Location location, UUID playerUUID) {
        List<UUID> allowedPlayers = protectMap.get(location);
        if (allowedPlayers != null) {
            allowedPlayers.remove(playerUUID);
            if (allowedPlayers.isEmpty()) {
                protectMap.remove(location);
            }
        }
    }
}
