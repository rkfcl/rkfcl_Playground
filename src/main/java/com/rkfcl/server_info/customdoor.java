package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.ShopInventoryManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class customdoor implements Listener {
    private Plugin plugin;
    private boolean interactCooldown;
    ShopInventoryManager shopInventoryManager = new ShopInventoryManager();
    private StringBuilder pasw = new StringBuilder();
    public static HashMap<Location, Integer> LockDoorMap= new HashMap<Location, Integer>() ;
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
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (interactCooldown || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 쿨다운 중이거나 우클릭 이벤트가 아닌 경우 이벤트를 무시합니다.
        }

        interactCooldown = true; // 쿨다운 설정

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        ItemStack itemStack = event.getItem();
        if (itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == 1){
            Location doorLocation = clickedBlock.getLocation().add(0,1,0);
            LockDoorMap.put(doorLocation, 1);
            pasw = new StringBuilder(); // pasw 초기화
            shopInventoryManager.lockdoorsettings(player, pasw.toString());
        }
        if(LockDoorMap.containsKey(clickedBlock.getLocation())) {
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
                    player.sendMessage("철문을 열거나 닫았습니다.");
                }
            }
        }
        // 지연 후 쿨다운을 재설정하기 위해 작업을 예약합니다.
        Bukkit.getScheduler().runTaskLater(plugin, () -> interactCooldown = false, 2L); // 20L = 1초
    }

    @EventHandler
    public void AccountProtectAddInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase("비밀번호 문 설정")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함

            if (event.getSlot() < 0 || event.getSlot() >= inventory.getSize()) {
                return; // 클릭한 슬롯이 메뉴 슬롯이 아닌 경우 종료
            }

            if (event.getClick() == ClickType.LEFT) {
                if (event.getSlot() == 12) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("1");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 13) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("2");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 14) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("3");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 21) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("4");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 22) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("5");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 23) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("6");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 30) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("7");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 31) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("8");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 32) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("9");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 40) {
                    if(pasw.length()>=6){
                        player.sendMessage(ChatColor.RED+"1~6자리 숫자만 입력 가능합니다");
                        return;
                    }
                    pasw.append("0");
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
                if (event.getSlot() == 36) {
                    removeLastChar(pasw);
                    shopInventoryManager.lockdoorsettings(player, pasw.toString());
                }
            }
        }
    }
    public void removeLastChar(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }





}
