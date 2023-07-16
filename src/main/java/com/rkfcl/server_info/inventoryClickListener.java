package com.rkfcl.server_info;

import com.rkfcl.server_info.ItemManagerCost.ItemCost;
import com.rkfcl.server_info.Manager.*;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.*;
import com.rkfcl.server_info.Manager.ShopInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static com.rkfcl.server_info.ItemRegistration.*;

public class inventoryClickListener implements Listener {
    private test pluginInstance;
    private PlayerDataManager playerDataManager;
    private final AbilityManager abilityManager;
    ShopInventoryManager shopInventoryManager = new ShopInventoryManager();

    private Map<UUID, Boolean> isAwaitingChat = new HashMap<>();

    ItemCost itemCost = new ItemCost();
    public inventoryClickListener(test pluginInstance,PlayerDataManager playerDataManager) {
        this.abilityManager = new AbilityManager(playerDataManager,pluginInstance);
        this.pluginInstance = pluginInstance;
        this.playerDataManager = playerDataManager;
    }



    private void handleJobPurchase(Player player, InventoryClickEvent clickEvent, ClickType clickType, int jobType, int jobCost, int itemCount) {
        if (clickEvent.isLeftClick()) {
            try {
                int setCount = (clickType == ClickType.SHIFT_LEFT) ? 64 : 1; // 구입할 세트 수
                int totalCost = jobCost * setCount; // 총 비용 계산

                if (playerDataManager.getPlayerBalance(player.getUniqueId()) < totalCost) {
                    player.sendMessage(ChatColor.RED + "금액이 부족합니다.");
                    return;
                }

                playerDataManager.decreaseMoney(player.getUniqueId(), totalCost); // 플레이어의 잔액을 데이터베이스에서 차감합니다.
                pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트

                for (int i = 0; i < setCount; i++) {
                    switch (jobType) {
                        case 1:
                            player.getInventory().addItem(ItemManager.createMineJob1Item());
                            break;
                        case 2:
                            player.getInventory().addItem(ItemManager.createMineJob2Item());
                            break;
                        case 3:
                            player.getInventory().addItem(ItemManager.createMineJob3Item());
                            break;
                        case 4:
                            player.getInventory().addItem(ItemManager.createMineJob4Item());
                            break;
                        case 5:
                            player.getInventory().addItem(ItemManager.createFarmerJob1Item());
                            break;
                        case 6:
                            player.getInventory().addItem(ItemManager.createFarmerJob2Item());
                            break;
                        case 7:
                            player.getInventory().addItem(ItemManager.createFarmerJob3Item());
                            break;
                        case 8:
                            player.getInventory().addItem(ItemManager.createFarmerJob4Item());
                            break;
                        case 9:
                            player.getInventory().addItem(ItemManager.createresetJobItem());
                            break;
                        case 10:
                            player.getInventory().addItem(ItemManager.createFisherJob1Item());
                            break;
                        case 11:
                            player.getInventory().addItem(ItemManager.createFisherJob2Item());
                            break;
                        case 12:
                            player.getInventory().addItem(ItemManager.createFisherJob3Item());
                            break;
                        case 13:
                            player.getInventory().addItem(ItemManager.createFisherJob4Item());
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                return;
            }
        } else if (clickEvent.isRightClick()) {
            if (clickEvent.isShiftClick()) {
                player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
            } else {
                player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
            }
        }
    }
    @EventHandler
    public void ShopJobInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        InventoryClickEvent clickEvent = (InventoryClickEvent) event;
        ClickType clickType = event.getClick();
        if (event.getClickedInventory() == null) return;

        //전직교관 상점
        if (event.getView().getTitle().equalsIgnoreCase("전직교관 상점")) {
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            }

            if (event.getCurrentItem().getType() == Material.PAPER) {
                event.setCancelled(true);
                // 플레이어에게 전직 종이 주기
                ItemMeta meta = event.getCurrentItem().getItemMeta();

                if (meta != null) {
                    String displayName = meta.getDisplayName();

                    if (displayName.startsWith("§6[ 직업 ] §f광부")) {
                        if (displayName.endsWith("1차")) {
                            handleJobPurchase(player, clickEvent, clickType, 1, 3000, 1);
                        } else if (displayName.endsWith("2차")) {
                            handleJobPurchase(player, clickEvent, clickType, 2, 7000, 1);
                        } else if (displayName.endsWith("3차")) {
                            handleJobPurchase(player, clickEvent, clickType, 3, 30000, 1);
                        } else if (displayName.endsWith("4차")) {
                            handleJobPurchase(player, clickEvent, clickType, 4, 55000, 1);
                        }
                    } else if (displayName.startsWith("§6[ 직업 ] §f농부")) {
                        if (displayName.endsWith("1차")) {
                            handleJobPurchase(player, clickEvent, clickType, 5, 5000, 1);
                        } else if (displayName.endsWith("2차")) {
                            handleJobPurchase(player, clickEvent, clickType, 6, 10000, 1);
                        } else if (displayName.endsWith("3차")) {
                            handleJobPurchase(player, clickEvent, clickType, 7, 30000, 1);
                        } else if (displayName.endsWith("4차")) {
                            handleJobPurchase(player, clickEvent, clickType, 8, 55000, 1);
                        }
                    } else if (displayName.startsWith("§6[ 직업 ] §f어부")) {
                        if (displayName.endsWith("1차")) {
                            handleJobPurchase(player, clickEvent, clickType, 10, 5000, 1);
                        } else if (displayName.endsWith("2차")) {
                            handleJobPurchase(player, clickEvent, clickType, 11, 8000, 1);
                        } else if (displayName.endsWith("3차")) {
                            handleJobPurchase(player, clickEvent, clickType, 12, 30000, 1);
                        } else if (displayName.endsWith("4차")) {
                            handleJobPurchase(player, clickEvent, clickType, 13, 45000, 1);
                        }
                    }
                }


                if (meta != null && meta.getDisplayName().equals("§6[ 직업 ] §f초기화권")) {
                    handleJobPurchase(player, clickEvent, clickType, 9, 30000, 1);
                }
            }
        }
    }
    @EventHandler
    public void ShopInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        InventoryClickEvent clickEvent = (InventoryClickEvent) event;
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 어부 상점
        if (event.getView().getTitle().equalsIgnoreCase("어부 상점")||event.getView().getTitle().equalsIgnoreCase("광부 상점")||event.getView().getTitle().equalsIgnoreCase("농부 상점")||event.getView().getTitle().equalsIgnoreCase("농부2 상점")||event.getView().getTitle().equalsIgnoreCase("잡화 상점")||event.getView().getTitle().equalsIgnoreCase("도축업자 상점")) {
            event.setCancelled(true);

            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            } else {
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null) {
                    ItemMeta meta = clickedItem.getItemMeta();

                    if (meta != null) {
                        Material itemType = clickedItem.getType();
                        int customModelData = clickedItem.getItemMeta().getCustomModelData();

                        if (customModelData == getCustomModelData(clickedItem)) {
                            if (clickEvent.isRightClick()) {

                                // 아이템 설명에 "판매불가"가 있는지 확인
                                List<String> lore = meta.getLore();
                                boolean isSellBlocked = false;

                                if (lore != null) {
                                    for (String line : lore) {
                                        if (line.contains("§c판매 불가")) {
                                            isSellBlocked = true;
                                            break;
                                        }
                                    }
                                }
                                if (isSellBlocked) {
                                    player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
                                    return; // 판매불가 아이템인 경우 처리 중단
                                }
                                // 판매 처리
                                int itemCount = countItems(player.getInventory(), itemType, customModelData);

                                if (itemCount == 0) {
                                    player.sendMessage("§6[상점] §f판매할 아이템이 없습니다.");
                                    return; // 판매할 아이템이 없는 경우 처리 중단
                                }


                                int setCount = calculateSetCount(clickType, itemCount);
                                int individualCost= itemCost.itemCost(clickedItem,customModelData);;
                                int totalCost = individualCost * setCount;

                                // 판매할 아이템과 동일한 종류와 커스텀 모델 데이터 값을 가진 아이템을 찾아서 개수 확인
                                int availableItemCount = countItems(player.getInventory(), itemType, customModelData);

                                if (availableItemCount >= setCount) {
                                    // 판매 가능한 개수 이상인 경우 판매 처리
                                    ItemStack sellItem = new ItemStack(itemType);
                                    ItemMeta sellItemMeta = sellItem.getItemMeta();
                                    sellItemMeta.setCustomModelData(customModelData);
                                    sellItem.setItemMeta(sellItemMeta);
                                    sellItem.setAmount(setCount);

                                    // 판매한 아이템 개수만큼 플레이어 인벤토리에서 제거
                                    removeItems(player.getInventory(), itemType,customModelData,setCount);
                                    player.sendMessage("§6[상점] §f아이템을 " + setCount + "개 판매하였습니다. §e(+" + totalCost + "$)");

                                    // 플레이어의 돈 증가 및 스코어보드 업데이트 등 추가 처리
                                    playerDataManager.increaseMoney(player.getUniqueId(), totalCost);
                                    pluginInstance.updateScoreboard(player);
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
                                } else {
                                    player.sendMessage("§6[상점] §f판매할 아이템이 충분하지 않습니다.");
                                }
                            } else if (clickEvent.isLeftClick()) {

                                // 아이템 설명에 "구매불가"가 있는지 확인
                                List<String> lore = meta.getLore();
                                boolean isSellBlocked = false;

                                if (lore != null) {
                                    for (String line : lore) {
                                        if (line.contains("§c구매 불가")) {
                                            isSellBlocked = true;
                                            break;
                                        }
                                    }
                                }
                                if (isSellBlocked) {
                                    player.sendMessage("§6[상점] §f구매가 불가능한 아이템입니다.");
                                    return; // 판매불가 아이템인 경우 처리 중단
                                }
                                // 구매 처리
                                int setCount = clickEvent.isShiftClick() ? 64 : 1; // 쉬프트+좌클릭인 경우
                                int individualCost = itemCost.itemCost(clickedItem, customModelData);
                                int totalCost = individualCost * setCount;

                                // 플레이어의 돈 확인
                                int playerMoney = playerDataManager.getPlayerBalance(player.getUniqueId());
                                if (playerMoney < totalCost) {
                                    player.sendMessage("§6[상점] §f돈이 부족합니다.");
                                    return; // 돈이 부족한 경우 처리 중단
                                }

                                // 구매 처리
                                if (setCount > 0) {
                                    clickedItem.setAmount(setCount);
                                    ItemMeta itemmeta = clickedItem.getItemMeta();
                                    if (itemmeta != null) {
                                        itemmeta.setLore(new ArrayList<>()); // 아이템 설명을 없애기 위해 빈 리스트로 설정
                                        clickedItem.setItemMeta(itemmeta);
                                    }
                                    // 플레이어에게 아이템 추가
                                    if (customModelData == 10000 && clickedItem.getType().equals(Material.OAK_PLANKS)) { //농토
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "iagive " + player.getName() + " customcrops:farmland " + setCount);
                                    }else if (customModelData == 10000 && clickedItem.getType().equals(Material.BREAD)) { //츄르
                                        ItemStack Item = ItemsAdder.getCustomItem("chur");
                                        Item.setAmount(setCount);
                                        player.getInventory().addItem(Item);
                                    }else if (customModelData == 5005 && clickedItem.getType().equals(Material.PAPER)) { //츄르
                                        ItemStack Item = ItemsAdder.getCustomItem("lock");
                                        Item.setAmount(setCount);
                                        player.getInventory().addItem(Item);
                                    }else {
                                        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(clickedItem);
                                        if (!remainingItems.isEmpty()) {
                                            player.sendMessage("§6[상점] §f인벤토리에 공간이 부족합니다.");
                                            return; // 인벤토리 공간 부족한 경우 처리 중단
                                        }
                                    }


                                    // 돈 차감 및 스코어보드 업데이트 등 추가 처리
                                    playerDataManager.decreaseMoney(player.getUniqueId(), totalCost);
                                    pluginInstance.updateScoreboard(player);
                                    player.sendMessage("§6[상점] §f아이템을 " + setCount + "개 구매하였습니다. §e(-" + totalCost + "$)");
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
                                    //지금 열어 있는 인벤토리 다시열기
                                    if (event.getView().getTitle().equalsIgnoreCase("잡화 상점")){
                                        shopInventoryManager.openShopItemsInventory(player);
                                    }else if (event.getView().getTitle().equalsIgnoreCase("농부2 상점")){
                                        shopInventoryManager.openShopFarmer2Inventory(player);
                                    }
                                } else {
                                    player.sendMessage("§6[상점] §f구매할 아이템이 없습니다.");
                                }
                            }

                        }
                    }
                }
            }
        } else if (event.getView().getTitle().equalsIgnoreCase("코인 상점")) {
            event.setCancelled(true);

            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            } else {
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null) {
                    ItemMeta meta = clickedItem.getItemMeta();

                    if (meta != null) {
                        Material itemType = clickedItem.getType();
                        int customModelData = clickedItem.getItemMeta().getCustomModelData();

                        if (customModelData == getCustomModelData(clickedItem)) {
                            if (clickEvent.isRightClick()) {

                                // 아이템 설명에 "판매불가"가 있는지 확인
                                List<String> lore = meta.getLore();
                                boolean isSellBlocked = false;

                                if (lore != null) {
                                    for (String line : lore) {
                                        if (line.contains("§c판매 불가")) {
                                            isSellBlocked = true;
                                            break;
                                        }
                                    }
                                }
                                if (isSellBlocked) {
                                    player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
                                    return; // 판매불가 아이템인 경우 처리 중단
                                }
                            } else if (clickEvent.isLeftClick()) {

                                // 아이템 설명에 "구매불가"가 있는지 확인
                                List<String> lore = meta.getLore();
                                boolean isSellBlocked = false;

                                if (lore != null) {
                                    for (String line : lore) {
                                        if (line.contains("§c구매 불가")) {
                                            isSellBlocked = true;
                                            break;
                                        }
                                    }
                                }
                                if (isSellBlocked) {
                                    player.sendMessage("§6[상점] §f구매가 불가능한 아이템입니다.");
                                    return; // 판매불가 아이템인 경우 처리 중단
                                }
                                boolean whatcoin = false;
                                if (lore != null) {
                                    for (String line : lore) {
                                        if (line.contains("사냥 코인")) {
                                            whatcoin = true;
                                            break;
                                        }
                                    }
                                }
                                // 구매 처리
                                int setCount = clickEvent.isShiftClick() ? 64 : 1; // 쉬프트+좌클릭인 경우
                                int individualCost = itemCost.itemCoinCost(clickedItem, customModelData);
                                int totalCost = individualCost * setCount;
                                int availableHuntCoinCount = 0;
                                int availableCoinCount = 0;
                                // 플레이어의 돈 확인
                                if (whatcoin) {
                                    availableHuntCoinCount = countItems(player.getInventory(), Material.PAPER, 5004);
                                    if (availableHuntCoinCount < totalCost) {
                                        player.sendMessage("§6[상점] §f코인이 부족합니다.");
                                        return; // 코인이 부족한 경우 처리 중단
                                    }
                                }else {
                                    availableCoinCount = countItems(player.getInventory(), Material.PAPER, 5001);
                                    if (availableCoinCount < totalCost) {
                                        player.sendMessage("§6[상점] §f코인이 부족합니다.");
                                        return; // 코인이 부족한 경우 처리 중단
                                    }
                                }
                                // 구매 처리
                                if (setCount > 0) {
                                    clickedItem.setAmount(setCount);
                                    ItemMeta itemmeta = clickedItem.getItemMeta();
                                    if (itemmeta != null) {
                                        itemmeta.setLore(new ArrayList<>()); // 아이템 설명을 없애기 위해 빈 리스트로 설정
                                        clickedItem.setItemMeta(itemmeta);
                                    }
                                    // 플레이어에게 아이템 추가
                                    if (whatcoin) {
                                        removeItems(player.getInventory(), Material.PAPER, 5004, totalCost);
                                    }else {
                                        removeItems(player.getInventory(), Material.PAPER, 5001, totalCost);
                                    }
                                    if (customModelData == 5003 && clickedItem.getType().equals(Material.ENCHANTED_BOOK)) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "iagive " + player.getName() + " protectblock:randombook " + setCount);
                                    }else if (customModelData == 6000 && clickedItem.getType().equals(Material.BOOK)) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "iagive " + player.getName() + " protectblock:enhancement_scroll " + setCount);
                                    }else {
                                        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(clickedItem);
                                        if (!remainingItems.isEmpty()) {
                                            player.sendMessage("§6[상점] §f인벤토리에 공간이 부족합니다.");
                                            return; // 인벤토리 공간 부족한 경우 처리 중단
                                        }
                                    }
                                    if (whatcoin){
                                        player.sendMessage("§6[상점] §f아이템을 " + setCount + "개 구매하였습니다. §e(-" + totalCost + " 사냥 코인)");
                                    }else {
                                        player.sendMessage("§6[상점] §f아이템을 " + setCount + "개 구매하였습니다. §e(-" + totalCost + " 갈치 코인)");
                                    }
                                    
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
                                    //지금 열어 있는 인벤토리 다시열기
                                    if (event.getView().getTitle().equalsIgnoreCase("코인 상점")){
                                        shopInventoryManager.coinshopinventory(player);
                                    }
                                } else {
                                    player.sendMessage("§6[상점] §f구매할 아이템이 없습니다.");
                                }
                            }

                        }
                    }
                }
            }
        }
    }



    @EventHandler
    public void ShopMenuInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 메뉴 상점
        if (event.getView().getTitle().equalsIgnoreCase("[ 갈치의 놀이터 ] 메뉴")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            }

            if (event.getCurrentItem().getType() == Material.HEART_OF_THE_SEA) {
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "발행할 금액을 채팅으로 입력해주세요.");
                player.sendMessage(ChatColor.YELLOW + "취소하려면 '취소'을 입력하세요.");

                // 채팅 입력 대기 상태로 설정
                isAwaitingChat.put(player.getUniqueId(), true);
            }
            if (event.getSlot() == 13){
                shopInventoryManager.openexchangeInventory(player,1);
                //거래소 기능에 대해서 설명하는 메세지 출력
                player.sendMessage("§6[ 거래소 ] §f/거래소 : 거래소를 오픈합니다.");
                player.sendMessage("§6[ 거래소 ] §f/거래소 등록 [가격] [수량] : 거래소에 판매할 아이템을 등록 합니다.");
                player.sendMessage("§6[ 거래소 ] §f/거래소 등록아이템 : 등록한 아이템을 확인 및 관리합니다.");
                player.sendMessage("§6[ 거래소 ] §f/거래소 반환아이템 : 반환된 아이템을 확인 및 수령합니다");
                player.sendMessage("§6[ 거래소 ] §f아이템 클릭시 아이템을 구매 합니다");
                player.sendMessage("§6[ 거래소 ] §f등록된 아이템은 쉬프트 좌클릭시 아이템을 반환 합니다.");
            }
            if (event.getSlot() == 15){

            }
        }
    }
    @EventHandler
    public void exchangeInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 메뉴 상점
        if (event.getView().getTitle().contains("거래소")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            }
            if (event.getSlot() == 45) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int previousPage = currentPage - 1;
                shopInventoryManager.openexchangeInventory(player, previousPage);
            }
            if (event.getSlot() == 47){
                shopInventoryManager.openRegisteredInventory(player,1);
            }
            if (event.getSlot() == 48){
                shopInventoryManager.openReturnedInventory(player,1);
            }
            if (event.getSlot() == 53) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int nextPage = currentPage + 1;

                Map<ItemStack, UUID> registeredItems = ItemRegistration.getRegisteredItems();
                List<ItemStack> itemList = new ArrayList<>(registeredItems.keySet());
                int maxSlotsPerPage = 45;
                int startIndex = (nextPage - 1) * maxSlotsPerPage;

                if (startIndex >= 0 && startIndex < itemList.size()) {
                    shopInventoryManager.openexchangeInventory(player, nextPage);
                }
            }


            if (event.getSlot() >= 0 && event.getSlot() <= 44) {
                ItemStack clickedItem = event.getCurrentItem();
                if (!registeredItems.get(clickedItem).equals(player.getUniqueId())) {
                    if (clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasLore()) {
                        List<String> lore = clickedItem.getItemMeta().getLore();
                        if (lore.size() >= 3) {
                            String priceLine = lore.get(lore.size() - 5); // 가격이 있는 줄의 인덱스
                            String priceText = ChatColor.stripColor(priceLine).replace("| 가격: ", "").replace(",", "");
                            int price = Integer.parseInt(priceText);
                            int playerBalance = playerDataManager.getPlayerBalance(player.getUniqueId());

                            if (playerBalance >= price) {
                                // 플레이어의 소지금이 가격 이상인 경우 아이템을 구매하고 소지금에서 차감
                                ItemStack purchasedItem = clickedItem.clone();
                                playerDataManager.increaseMoney(registeredItems.get(purchasedItem), price); //판매자 소지금 증가
                                //판매자에게 판매 메세지 출력
                                String itemname = clickedItem.getType().name();
                                Bukkit.getPlayer(registeredItems.get(purchasedItem)).sendMessage(itemname+ChatColor.GREEN+" 아이템 판매완료");
                                pluginInstance.updateScoreboard(Bukkit.getPlayer(registeredItems.get(purchasedItem))); // 판매자 스코어 보드 업데이트
                                unregisterItem(purchasedItem);
                                //아이템 설명 원래대로
                                ItemMeta meta = purchasedItem.getItemMeta();
                                if (meta != null && meta.hasLore()) {
                                    List<String> lores = meta.getLore();

                                    // 마지막 인덱스부터 6개의 줄 제거
                                    int startIndex = Math.max(0, lores.size() - 6);
                                    int endIndex = Math.max(0, lores.size() - 1);
                                    if (startIndex < lores.size()) {
                                        lores.subList(startIndex, endIndex + 1).clear();
                                    }

                                    meta.setLore(lores);
                                    purchasedItem.setItemMeta(meta);
                                }
                                player.getInventory().addItem(purchasedItem);
                                playerDataManager.decreaseMoney(player.getUniqueId(), price);
                                player.sendMessage(ChatColor.GREEN + "아이템을 구매하였습니다.§e(-" + price + "$)");
                                pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드를 업데이트합니다.
                                // 구매 후 등록된 아이템 목록을 업데이트하고 새로운 등록된 아이템 메뉴를 열도록 함
                                shopInventoryManager.openexchangeInventory(player, 1);
                                // 소리 재생
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);

                            } else {
                                player.sendMessage(ChatColor.RED + "소지금이 부족하여 아이템을 구매할 수 없습니다.");
                            }
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "자신이 등록한 아이템은 구매할 수 없습니다.");
                }
            }
        }
    }

    @EventHandler
    public void RegisteredInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 메뉴 상점
        if (event.getView().getTitle().contains("등록된 아이템")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            }
            if (clickType == ClickType.SHIFT_LEFT){
                ItemStack returnItem = event.getCurrentItem();
                unregisterItem(returnItem);
                ItemReturn.returnItem(returnItem,player.getUniqueId());
                shopInventoryManager.openRegisteredInventory(player,1);
            }
            if (event.getSlot() == 45) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int previousPage = currentPage - 1;
                shopInventoryManager.openRegisteredInventory(player, previousPage);
            }
            if (event.getSlot() == 48){
                shopInventoryManager.openReturnedInventory(player,1);
            }
            if (event.getSlot() == 53) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int nextPage = currentPage + 1;

                Map<ItemStack, UUID> registeredItems = ItemRegistration.getRegisteredItems();
                List<ItemStack> itemList = new ArrayList<>(registeredItems.keySet());
                int maxSlotsPerPage = 45;
                int startIndex = (nextPage - 1) * maxSlotsPerPage;

                if (startIndex >= 0 && startIndex < itemList.size()) {
                    shopInventoryManager.openRegisteredInventory(player, nextPage);
                }
            }
        }
    }
    @EventHandler
    public void ReturnedInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (event.getClickedInventory() == null) return;

        // 메뉴 상점
        if (event.getView().getTitle().contains("반환된 아이템")) {
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
                return;
            }
            if (event.getSlot() == 45) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int previousPage = currentPage - 1;
                shopInventoryManager.openReturnedInventory(player, previousPage);
            }
            if (event.getSlot() == 47){
                shopInventoryManager.openRegisteredInventory(player,1);
            }
            if (event.getSlot() == 53) {
                InventoryView inventoryView = event.getView();
                String title = inventoryView.getTitle();
                int currentPage = Integer.parseInt(title.substring(title.lastIndexOf(":") + 2, title.lastIndexOf("페이지")).trim());
                int nextPage = currentPage + 1;

                Map<ItemStack, UUID> ReturnedItems = ItemReturn.getReturnedItemsItems();
                List<ItemStack> itemList = new ArrayList<>(ReturnedItems.keySet());
                int maxSlotsPerPage = 45;
                int startIndex = (nextPage - 1) * maxSlotsPerPage;

                if (startIndex >= 0 && startIndex < itemList.size()) {
                    shopInventoryManager.openReturnedInventory(player, nextPage);
                }
            }
            if (clickType == ClickType.SHIFT_LEFT){

                ItemStack returnItem = event.getCurrentItem();
                ItemReturn.unReturnItem(returnItem);
                ItemMeta meta = returnItem.getItemMeta();
                if (meta != null && meta.hasLore()) {
                    List<String> lore = meta.getLore();

                    // 마지막 인덱스부터 6개의 줄 제거
                    int startIndex = Math.max(0, lore.size() - 6);
                    int endIndex = Math.max(0, lore.size() - 1);
                    if (startIndex < lore.size()) {
                        lore.subList(startIndex, endIndex + 1).clear();
                    }

                    meta.setLore(lore);
                    returnItem.setItemMeta(meta);
                }
                player.getInventory().addItem(returnItem);
                shopInventoryManager.openReturnedInventory(player,1);
                // 소리 재생
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            }
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (isAwaitingChat.containsKey(playerUUID)) {
            event.setCancelled(true); // 채팅 이벤트 취소

            String message = event.getMessage();
            if (message.equalsIgnoreCase("취소")) {
                // 발행 취소
                player.sendMessage(ChatColor.YELLOW + "수표 발행이 취소되었습니다.");
            } else {
                int amount;
                try {
                    amount = Integer.parseInt(message);
                    if (amount <= 0 || amount > playerDataManager.getPlayerBalance(playerUUID)) {
                        player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                    } else {
                        playerDataManager.decreaseMoney(player.getUniqueId(), amount); // 플레이어의 잔액을 데이터베이스에서 차감합니다.

                        Bukkit.getScheduler().runTask(pluginInstance, () -> {
                            pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트
                        });

                        ItemStack check = ItemManager.createCheck(amount);
                        player.getInventory().addItem(check);
                        // 수표 발행 처리
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                }
            }

            // 채팅 입력 대기 상태 해제
            isAwaitingChat.remove(playerUUID);
        }
    }


    private int countItems(Inventory inventory, Material itemType, int customModelData) {
        int count = 0;
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == itemType && getCustomModelData(itemStack) == customModelData) {
                count += itemStack.getAmount();
            }
        }
        return count;
    }
    public static void removeItems(Inventory inventory, Material itemType, int customModelData, int count) {
        int remaining = count; // 남은 개수를 추적하기 위한 변수

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() == itemType && getCustomModelData(itemStack) == customModelData) {
                int amount = itemStack.getAmount();

                if (amount <= remaining) {
                    inventory.remove(itemStack);
                    remaining -= amount;
                } else {
                    itemStack.setAmount(amount - remaining);
                    break; // 남은 개수만큼 제거하고 반복 종료
                }
            }
        }
    }

    // 아이템의 CustomModelData 값을 가져오는 메서드
    public static int getCustomModelData(ItemStack itemStack) {
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasCustomModelData()) {
            return itemStack.getItemMeta().getCustomModelData();
        }
        return 0;
    }
    private int calculateSetCount(ClickType clickType, int itemCount) {
        int setCount = 0;
        if (clickType == ClickType.RIGHT) {
            setCount = 1;
        } else if (clickType == ClickType.SHIFT_RIGHT) {
            setCount = itemCount;
        }
        return setCount;
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 좌클릭인 경우 처리하지 않음
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // 아이템이 존재하고 아이템 메타데이터가 존재하는 경우 처리
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        String displayName = meta.getDisplayName();

        if (displayName.contains("§6골드")) {
            handleGoldItem(player, item, displayName);
        } else if (displayName.contains("§6[ 직업 ]")) {
            handleJobItem(player, item, displayName);
        }
    }

    private void handleGoldItem(Player player, ItemStack item, String displayName) {
        String amountString = ChatColor.stripColor(displayName).replace("골드", "");
        try {
            int amount = Integer.parseInt(amountString);
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().remove(item);
            }
            playerDataManager.increaseMoney(player.getUniqueId(), amount);
            pluginInstance.updateScoreboard(player);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "수표 사용 중 오류가 발생했습니다.");
        }
    }

    private void handleJobItem(Player player, ItemStack item, String displayName) {
        String jobName = displayName.replace("§6[ 직업 ] §f", "");
        String playerJob = playerDataManager.getPlayerJob(player.getUniqueId());

        if (jobName.equals("광부 1차")) {
            if (!playerJob.equals("초보자") && !playerJob.equals("백수")) {
                player.sendMessage("직업을 초기화 해야 합니다");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 광부1차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 1차");
        } else if (jobName.equals("광부 2차")) {
            if (!playerJob.equals("광부 1차")) {
                player.sendMessage("광부 1차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 광부2차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 2차");
        } else if (jobName.equals("광부 3차")) {
            if (!playerJob.equals("광부 2차")) {
                player.sendMessage("광부 2차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 광부3차로 전직 했습니다.");
            abilityManager.applyFireResistance(player);
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 3차");
        } else if (jobName.equals("광부 4차")) {
            if (!playerJob.equals("광부 3차")) {
                player.sendMessage("광부 3차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 광부4차로 전직 했습니다.");
           playerDataManager.setPlayerJob(player.getUniqueId(), "광부 4차");
        } else if (jobName.equals("농부 1차")) {
            if (!playerJob.equals("초보자") && !playerJob.equals("백수")) {
                player.sendMessage("직업을 초기화 해야 합니다");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 농부1차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 1차");
        } else if (jobName.equals("농부 2차")) {
            if (!playerJob.equals("농부 1차")) {
                player.sendMessage("농부 1차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 농부2차로 전직 했습니다.");
            ItemStack watering_can = ItemsAdder.getCustomItem("watering_can");
            player.getInventory().setItemInMainHand(watering_can);
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 2차");
        } else if (jobName.equals("농부 3차")) {
            if (!playerJob.equals("농부 2차")) {
                player.sendMessage("농부 2차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 농부3차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 3차");
        } else if (jobName.equals("농부 4차")) {
            if (!playerJob.equals("농부 3차")) {
                player.sendMessage("농부 3차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 농부4차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 4차");
        } else if (jobName.equals("어부 1차")) {
            if (!playerJob.equals("초보자") && !playerJob.equals("백수")) {
                player.sendMessage("직업을 초기화 해야 합니다");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 어부1차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "어부 1차");
        }else if (jobName.equals("어부 2차")) {
            if (!playerJob.equals("어부 1차")) {
                player.sendMessage("어부 1차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 어부2차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "어부 2차");
        }else if (jobName.equals("어부 3차")) {
            if (!playerJob.equals("어부 2차")) {
                player.sendMessage("어부 2차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 어부3차로 전직 했습니다.");
            AbilityManager.applyWaterResistance(player);
            playerDataManager.setPlayerJob(player.getUniqueId(), "어부 3차");
        }else if (jobName.equals("어부 4차")) {
            if (!playerJob.equals("어부 3차")) {
                player.sendMessage("어부 3차만 전직 가능합니다.");
                return;
            }
            Bukkit.getServer().broadcastMessage(player.getDisplayName()+"가 어부4차로 전직 했습니다.");
            playerDataManager.setPlayerJob(player.getUniqueId(), "어부 4차");
        } else if (jobName.equals("요리사 1차")) {
            if (!playerJob.equals("초보자") && !playerJob.equals("백수")) {
                player.sendMessage("직업을 초기화 해야 합니다");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "요리사 1차");
        }else if (jobName.equals("요리사 2차")) {
            if (!playerJob.equals("요리사 1차")) {
                player.sendMessage("요리사 1차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "요리사 2차");
        }else if (jobName.equals("요리사 3차")) {
            if (!playerJob.equals("요리사 2차")) {
                player.sendMessage("요리사 2차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "요리사 3차");
        }else if (jobName.equals("요리사 4차")) {
            if (!playerJob.equals("요리사 3차")) {
                player.sendMessage("요리사 3차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "요리사 4차");
        }else if (jobName.equals("초기화권")) {
            if (playerJob.equals("백수")) {
                player.sendMessage("직업이 없습니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "백수");
        }

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().remove(item);
        }

        pluginInstance.updateScoreboard(player);
    }
}
