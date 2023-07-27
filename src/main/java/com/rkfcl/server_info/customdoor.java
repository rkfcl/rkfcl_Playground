package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.ItemManager;
import com.rkfcl.server_info.Manager.ShopInventoryManager;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.rkfcl.server_info.ProtectBlock.AllowprotectMap;
import static com.rkfcl.server_info.ProtectBlock.protectMap;

public class customdoor implements Listener {
    private Plugin plugin;
    private boolean interactCooldown;
    ShopInventoryManager shopInventoryManager = new ShopInventoryManager();
    private StringBuilder pasw = new StringBuilder();

    public static HashMap<Location, String> LockDoorMap= new HashMap<Location, String>() ;
    public static HashMap<UUID, Location> PlayerDoorLocationMap= new HashMap<UUID, Location>() ;
    public customdoor(Plugin plugin) {
        this.plugin = plugin;
        this.interactCooldown = false;
    }
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent event) {
        Block block = event.getBlock();

        if(block.getType()==Material.IRON_DOOR) {
            event.setNewCurrent(event.getOldCurrent()); // 현재 레드스톤 신호를 이전 값으로 변경하여 철문이 열리지 않도록 함
        }
    }
    @EventHandler
    public void LcokDoorPlace(PlayerInteractEvent event) {
        if (interactCooldown || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 쿨다운 중이거나 우클릭 이벤트가 아닌 경우 이벤트를 무시합니다.
        }

        interactCooldown = true; // 쿨다운 설정

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        ItemStack itemStack = event.getItem();
        PlayerDoorLocationMap.put(player.getUniqueId(), clickedBlock.getLocation());
        if (itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == 1 && itemStack.getType()==Material.IRON_DOOR) {
            Location doorLocation = clickedBlock.getLocation().add(0, 1, 0);
            PlayerDoorLocationMap.put(player.getUniqueId(), doorLocation);
            pasw = new StringBuilder(); // pasw 초기화
            shopInventoryManager.lockdoorsettings(player, pasw.toString());
            LockDoorMap.put(PlayerDoorLocationMap.get(player.getUniqueId()), pasw.toString());
        }
        Location sectorId = protectMap.get(clickedBlock.getLocation());
        List<Location> locations = AllowprotectMap.get(player.getUniqueId());
        if (LockDoorMap.containsKey(clickedBlock.getLocation())||LockDoorMap.containsKey(clickedBlock.getLocation().subtract(0,1,0))) {
            if (locations != null && locations.contains(sectorId)) {
                if (clickedBlock != null && clickedBlock.getType() == Material.IRON_DOOR) {
                    BlockData blockData = clickedBlock.getBlockData();
                    if (blockData instanceof Openable) {
                        Openable openable = (Openable) blockData;
                        if (openable.isOpen()) {
                            openable.setOpen(false);
                            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0f, 1.0f);
                        } else {
                            openable.setOpen(true);
                            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);
                        }
                        clickedBlock.setBlockData(openable);
                        clickedBlock.getState().update();
                    }
                }
            }else if (clickedBlock != null && clickedBlock.getType() == Material.IRON_DOOR) {
                BlockData blockData = clickedBlock.getBlockData();
                if (blockData instanceof Openable) {
                    Openable openable = (Openable) blockData;
                    if (openable.isOpen()) {
                        openable.setOpen(false);
                        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0f, 1.0f);
                        clickedBlock.setBlockData(openable);
                        clickedBlock.getState().update();
                    } else {
                        if (LockDoorMap.containsKey(clickedBlock.getLocation())||LockDoorMap.containsKey(clickedBlock.getLocation().subtract(0,1,0))) {
                            pasw = new StringBuilder(); // pasw 초기화
                            shopInventoryManager.lockdoor(player, pasw.toString());
                        }
                    }

                }
            }
        }
        // 지연 후 쿨다운을 재설정하기 위해 작업을 예약합니다.
        Bukkit.getScheduler().runTaskLater(plugin, () -> interactCooldown = false, 2L); // 20L = 1초
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        Location sector = protectMap.get(location);

        // sector가 null이면 해당 위치에 보호 영역이 없으므로 return
        if (sector == null) {
            return;
        }

        int x = sector.getBlockX();
        int y = sector.getBlockY();
        int z = sector.getBlockZ();

        // 권한 없으면 부수지 못하게 하는 코드
        if (protectMap.containsKey(location)) {
            Location SectorID = protectMap.get(location);
            List<Location> PlayerAllow = AllowprotectMap.get(player.getUniqueId());
            if (PlayerAllow != null && PlayerAllow.contains(SectorID)) {
                if (block.getType() == Material.IRON_DOOR) {
                    Location blockLocation = block.getLocation();
                    if (LockDoorMap.containsKey(blockLocation)) {
                        // 아이템 드롭 캔슬
                        event.setDropItems(false);
                        ItemStack lockdoordrop = ItemManager.lockdoor();
                        // 플레이어에게 대체 아이템 주기
                        player.getInventory().addItem(lockdoordrop);

                        // 소리 재생
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
                    }
                }
            }
        } else if (block.getType() == Material.IRON_DOOR) {
            Location blockLocation = block.getLocation();
            if (LockDoorMap.containsKey(blockLocation)) {
                // 아이템 드롭 캔슬
                event.setDropItems(false);
                ItemStack lockdoordrop = ItemManager.lockdoor();
                // 플레이어에게 대체 아이템 주기
                player.getInventory().addItem(lockdoordrop);

                // 소리 재생
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            }
        }
    }

    @EventHandler
    public void LockDoorSetting(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equalsIgnoreCase("비밀번호 문 설정")) {
            return;
        }
        event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함

        if (event.getSlot() < 0 || event.getSlot() >= inventory.getSize()) {
            return; // 클릭한 슬롯이 메뉴 슬롯이 아닌 경우 종료
        }

        int clickedSlot = event.getSlot();

        if (clickedSlot == 36) {
            removeLastChar(pasw);
        } else if (clickedSlot == 44) {
            if (pasw.length() < 1) {
                player.sendMessage(ChatColor.RED + "1~6자리 숫자만 입력 가능합니다");
                return;
            }
            LockDoorMap.put(PlayerDoorLocationMap.get(player.getUniqueId()), pasw.toString());
            player.closeInventory();
            return;
        } else {
            int[] numberSlots = {40, 12, 13, 14, 21, 22, 23, 30, 31, 32};
            int number = -1;
            for (int i = 0; i < numberSlots.length; i++) {
                if (numberSlots[i] == clickedSlot) {
                    number = i;
                    break;
                }
            }
            if (number >= 0) {
                if (pasw.length() >= 6) {
                    player.sendMessage(ChatColor.RED + "1~6자리 숫자만 입력 가능합니다");
                    return;
                }
                pasw.append(number);
            }
        }
        shopInventoryManager.lockdoorsettings(player, pasw.toString());
    }

    @EventHandler
    public void LockDoor(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equalsIgnoreCase("비밀번호 문")) {
            return;
        }

        event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함

        if (event.getSlot() < 0 || event.getSlot() >= inventory.getSize()) {
            return; // 클릭한 슬롯이 메뉴 슬롯이 아닌 경우 종료
        }

        int clickedSlot = event.getSlot();

        if (clickedSlot == 36) {
            removeLastChar(pasw);
        } else if (clickedSlot == 44) {
            if (pasw.length() < 1) {
                player.sendMessage(ChatColor.RED + "1~6자리 숫자만 입력 가능합니다");
                return;
            }
            Location location = PlayerDoorLocationMap.get(player.getUniqueId());
            if (LockDoorMap.containsKey(location) && LockDoorMap.get(location).equals(pasw.toString())){
                player.closeInventory();

                // 철문 열기 작업
                Location doorLocation = PlayerDoorLocationMap.get(player.getUniqueId());
                Block doorBlock = doorLocation.getBlock();
                if (doorBlock.getType() == Material.IRON_DOOR) {
                    BlockData blockData = doorBlock.getBlockData();
                    if (blockData instanceof Openable) {
                        Openable openable = (Openable) blockData;
                        if (!openable.isOpen()) {
                            openable.setOpen(true);
                            doorBlock.setBlockData(openable);
                            doorBlock.getState().update();
                            player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);
                            player.sendMessage("철문이 열렸습니다.");
                        } else {
                            player.sendMessage("이미 열려있는 철문입니다.");
                        }
                    }
                }
            }else {
                Location locationUp = PlayerDoorLocationMap.get(player.getUniqueId()).subtract(0, 1, 0);
                if (LockDoorMap.containsKey(locationUp) && LockDoorMap.get(locationUp).equals(pasw.toString())) {
                    player.closeInventory();

                    // 철문 열기 작업
                    Location doorLocation = PlayerDoorLocationMap.get(player.getUniqueId());
                    Block doorBlock = doorLocation.getBlock();
                    if (doorBlock.getType() == Material.IRON_DOOR) {
                        BlockData blockData = doorBlock.getBlockData();
                        if (blockData instanceof Openable) {
                            Openable openable = (Openable) blockData;
                            if (!openable.isOpen()) {
                                openable.setOpen(true);
                                doorBlock.setBlockData(openable);
                                doorBlock.getState().update();
                                player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 1.0f);
                                player.sendMessage("철문이 열렸습니다.");
                            } else {
                                player.sendMessage("이미 열려있는 철문입니다.");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "비밀번호가 일치하지 않습니다.");
                }
            }
            return;
        } else {
            int[] numberSlots = {40, 12, 13, 14, 21, 22, 23, 30, 31, 32};
            int number = -1;
            for (int i = 0; i < numberSlots.length; i++) {
                if (numberSlots[i] == clickedSlot) {
                    number = i;
                    break;
                }
            }
            if (number >= 0) {
                if (pasw.length() >= 6) {
                    player.sendMessage(ChatColor.RED + "1~6자리 숫자만 입력 가능합니다");
                    return;
                }
                pasw.append(number);
            }
        }
        shopInventoryManager.lockdoor(player, pasw.toString());
    }


    public void removeLastChar(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }





}
