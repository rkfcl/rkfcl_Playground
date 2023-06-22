package com.rkfcl.server_info;


import com.rkfcl.server_info.Manager.ShopInventoryManager;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ProtectBlock implements Listener {
    private Plugin plugin;
    private Location sectorID;
    ShopInventoryManager shopInventoryManager = new ShopInventoryManager();

    public static HashMap<Location, Location> protectMap = new HashMap<>();
    public static HashMap<UUID, List<Location>> AllowprotectMap = new HashMap<>();
    public static HashMap<Location, List<UUID>> AccountprotectMap = new HashMap<>();

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
            Location SectorID = new Location(location.getWorld(), x , y , z );
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
            addLocationToAllowprotectMap(player.getUniqueId(),SectorID);
            // 11x11x11 크기의 정사각형의 모든 좌표를 protectMap에 저장합니다.
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    for (int k = 0; k < 11; k++) {
                        Location blockLocation = new Location(location.getWorld(), x + i, y + j, z + k);
                        // 중심 좌표 위로 3칸에 건차 관리 블럭 설치
                        if (j == 3 && i == 5 && k == 5) {
                            Block AccountBlock = blockLocation.getBlock().getRelative(0, 0, 0);
                            CustomBlock.place("beacon",blockLocation);
                            addUuidToAccountprotectMap(SectorID,player.getUniqueId());
                        }
                        // 특정 위치에 SectorID추가
                        protectMap.put(blockLocation,SectorID);

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
            if (block.getBlockData().equals(CustomBlock.getInstance("beacon").getBaseBlockData())) {
                event.setCancelled(true); // beacon 블럭인 경우 무시합니다.
            }
            Location SectorID = protectMap.get(location);
            List<Location> PlayerAllow = AllowprotectMap.get(player.getUniqueId());
            if (PlayerAllow != null && !PlayerAllow.contains(SectorID)) {
                player.sendMessage("§6[ 건차 ] §f" + player.getName() + "님의 §c건설 차단 §f구역입니다.");
                event.setCancelled(true);
            }
        }
    }
    //피스톤 작동 제한
    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (protectMap.containsKey(block.getLocation())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            if (protectMap.containsKey(block.getLocation())) {
                event.setCancelled(true);
                break;
            }
        }
    }


    @EventHandler
    public void Accountconstructionblock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || !clickedBlock.getBlockData().equals(CustomBlock.getInstance("beacon").getBaseBlockData())) {
            return; // 블럭이 없거나 beacon이(가) 아닌 경우 무시합니다.
        }
        if (clickedBlock != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location location = clickedBlock.getLocation();
            sectorID = protectMap.get(location);
            UUID playerUUID = player.getUniqueId();
            List<UUID> playerUUIDs = AccountprotectMap.get(sectorID);
            if (playerUUIDs != null && playerUUIDs.size() > 0 && playerUUIDs.get(0).equals(playerUUID)) {
                shopInventoryManager.AccountInventory(player, sectorID);
            }
        }
    }
    @EventHandler
    public void ProtectInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 메뉴 상점
        if (event.getView().getTitle().equalsIgnoreCase(Bukkit.getPlayer(player.getUniqueId()).getDisplayName()+"님의 소유")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            }
            if (event.getSlot() == 31){
                shopInventoryManager.AccountAddInventory(player);
            }
            if (clickType == ClickType.SHIFT_LEFT) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() == Material.PLAYER_HEAD) {
                    SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                    OfflinePlayer clickedPlayer = skullMeta.getOwningPlayer();
                    if (clickedPlayer != null) {
                        UUID clickedUUID = clickedPlayer.getUniqueId();
                        List<UUID> playerUUIDs = AccountprotectMap.get(sectorID); // 위치에 해당하는 UUID 목록 가져오기
                        if (playerUUIDs != null && playerUUIDs.contains(clickedUUID)) {
                            // 이미 권한이 있는 경우 제거
                            removeUuidFromAccountprotectMap(sectorID, clickedUUID);
                            removeLocationFromAllowprotectMap(clickedUUID, sectorID);
                            player.sendMessage("§6[ 건차 ] §f" + clickedPlayer.getName() + "님의 건설 권한을 제거하였습니다.");
                        }
                        shopInventoryManager.AccountInventory(player, sectorID);
                    }
                }
            }
        }
    }
    @EventHandler
    public void AccountProtectAddInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase("권한을 추가할 플레이어를 선택해 주세요")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함

            if (event.getSlot() < 0 || event.getSlot() >= inventory.getSize()) {
                return; // 클릭한 슬롯이 메뉴 슬롯이 아닌 경우 종료
            }
            if (event.getSlot() == 49){
                shopInventoryManager.AccountInventory(player, sectorID);
            }
            if (event.getClick() == ClickType.LEFT) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() == Material.PLAYER_HEAD) {
                    SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                    OfflinePlayer clickedPlayer = skullMeta.getOwningPlayer();
                    if (clickedPlayer != null) {
                        UUID clickedUUID = clickedPlayer.getUniqueId();
                        List<UUID> playerUUIDs = AccountprotectMap.get(sectorID); // 위치에 해당하는 UUID 목록 가져오기
                        player.sendMessage(String.valueOf(sectorID));
                        if (playerUUIDs != null && playerUUIDs.contains(clickedUUID)) {
                            player.sendMessage("§6[ 건차 ] §f" + clickedPlayer.getName() + "님은 이미 건설 권한이 있습니다.");
                        } else {
                            addUuidToAccountprotectMap(sectorID, clickedUUID);
                            addLocationToAllowprotectMap(clickedUUID, sectorID);

                            player.sendMessage("§6[ 건차 ] §f" + clickedPlayer.getName() + "님을 건설 권한에 추가했습니다.");
                            shopInventoryManager.AccountInventory(player, sectorID);
                        }
                    }
                }
            }
        }
    }


    public void addLocationToAllowprotectMap(UUID uuid, Location location) {
        List<Location> locationList = AllowprotectMap.get(uuid);

        if (locationList == null) {
            locationList = new ArrayList<>();
        }

        locationList.add(location);

        AllowprotectMap.put(uuid, locationList);
    }

    public void removeUuidFromAccountprotectMap(Location location, UUID uuid) {
        List<UUID> uuidList = AccountprotectMap.get(location);
        if (uuidList != null) {
            uuidList.remove(uuid);
            if (uuidList.isEmpty()) {
                AccountprotectMap.remove(location);
            } else {
                AccountprotectMap.put(location, uuidList);
            }
        }
    }


    public void addUuidToAccountprotectMap(Location location, UUID uuid) {
        List<UUID> uuidList = AccountprotectMap.get(location);

        if (uuidList == null) {
            uuidList = new ArrayList<>();
        }

        uuidList.add(uuid);

        AccountprotectMap.put(location, uuidList);
    }

    public void removeLocationFromAllowprotectMap(UUID uuid, Location location) {
        List<Location> locationList = AllowprotectMap.get(uuid);
        if (locationList != null) {
            locationList.remove(location);
            if (locationList.isEmpty()) {
                AllowprotectMap.remove(uuid);
            } else {
                AllowprotectMap.put(uuid, locationList);
            }
        }
    }


}
