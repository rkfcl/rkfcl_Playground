package com.rkfcl.server_info;

import com.rkfcl.server_info.ItemManagerCost.OreCost;
import com.rkfcl.server_info.Manager.AbilityManager;
import com.rkfcl.server_info.Manager.DatabaseManager;
import com.rkfcl.server_info.Manager.ItemManager;
import com.rkfcl.server_info.Manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class inventoryClickListener implements Listener {

    private test pluginInstance;

    private  DatabaseManager databaseManager;
    private final PlayerManager playerManager;
    private final AbilityManager abilityManager;
    private Map<UUID, Boolean> isAwaitingChat = new HashMap<>();

    ItemManager itemManager = new ItemManager();
    OreCost oreCost = new OreCost();
    public inventoryClickListener(test pluginInstance,DatabaseManager databaseManager) {
        this.playerManager = new PlayerManager(databaseManager);
        this.abilityManager = new AbilityManager(databaseManager);
        this.pluginInstance = pluginInstance;
        this.databaseManager = databaseManager;
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
            }

            if (event.getCurrentItem().getType() == Material.PAPER) {
                event.setCancelled(true);
                // 플레이어에게 광부 전직 종이 주기
                ItemMeta meta = event.getCurrentItem().getItemMeta();

                if (meta != null && (meta.getDisplayName().equals("§6[ 직업 ] §f광부 1차")||meta.getDisplayName().equals("§6[ 직업 ] §f광부 2차")||meta.getDisplayName().equals("§6[ 직업 ] §f광부 3차")||meta.getDisplayName().equals("§6[ 직업 ] §f광부 4차"))) {
                    if (clickEvent.isLeftClick()) {
                            try {
                                int setCount = 1; // 구입할 세트 수
                                if (clickType == ClickType.LEFT) {
                                    setCount = 1;
                                } else if (clickType == ClickType.SHIFT_LEFT) {
                                    setCount = 64; // 한 번에 64세트 구입
                                }
                                int totalCost=0;
                                if(meta.getDisplayName().equals("§6[ 직업 ] §f광부 1차")){
                                    totalCost = 3000 * setCount; // 총 비용 계산
                                }else if(meta.getDisplayName().equals("§6[ 직업 ] §f광부 2차")){
                                    totalCost = 7000 * setCount; // 총 비용 계산
                                } else if (meta.getDisplayName().equals("§6[ 직업 ] §f광부 3차")) {
                                    totalCost = 30000 * setCount; // 총 비용 계산
                                } else if (meta.getDisplayName().equals("§6[ 직업 ] §f광부 4차")) {
                                    totalCost = 55000 * setCount; // 총 비용 계산
                                }


                                if (databaseManager.getPlayerMoney(player) < totalCost) {
                                    player.sendMessage(ChatColor.RED + "금액이 부족합니다.");
                                    return;
                                }

                                databaseManager.decreaseMoney(player, totalCost); // 플레이어의 잔액을 데이터베이스에서 차감합니다.
                                pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트

                                for (int i = 0; i < setCount; i++) {
                                    if(totalCost==3000) {
                                        player.getInventory().addItem(ItemManager.createMineJobItem());
                                    } else if (totalCost==7000) {
                                        player.getInventory().addItem(ItemManager.createMineJob2Item());
                                    } else if (totalCost==30000) {
                                        player.getInventory().addItem(ItemManager.createMineJob3Item());
                                    } else if (totalCost==55000) {
                                        player.getInventory().addItem(ItemManager.createMineJob4Item());
                                    }
                                }
                                player.closeInventory();
                            } catch (NumberFormatException e) {
                                player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                                return;
                            }

                    } else if (clickEvent.isRightClick()) {
                        if(clickEvent.isShiftClick()){
                            player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
                        }else{
                            player.sendMessage("§6[상점] §f판매가 불가능한 아이템입니다.");
                        }

                    }
                }




                if (meta != null && meta.getDisplayName().equals("§6[ 직업 초기화권 ]")) {
                    player.getInventory().addItem(ItemManager.createresetJobItem());
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void ShopMineInventory(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        InventoryClickEvent clickEvent = (InventoryClickEvent) event;
        ClickType clickType = event.getClick();
        if (event.getClickedInventory() == null) return;

        // 광부 상점
        if (event.getView().getTitle().equalsIgnoreCase("광부 상점")) {
            event.setCancelled(true);
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            } else {
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null) {
                    ItemMeta meta = clickedItem.getItemMeta();
                    if (meta != null) {
                        Material itemType = clickedItem.getType();
                        if (isMineralItem(itemType)) {
                            if (clickEvent.isRightClick()) {
                                // 판매 처리
                                int itemCount = countItems(player.getInventory(), itemType);
                                if (itemCount == 0) {
                                    return; // 판매할 아이템이 없는 경우 처리 중단
                                }

                                int setCount = calculateSetCount(clickType, itemCount);
                                int individualCost = oreCost.calculateIndividualCost(itemType);
                                int totalCost = individualCost * setCount;

                                player.sendMessage("§6[상점] §f아이템을 " + setCount + "개 판매 하였습니다.","§e(+"+totalCost+"$)");
                                HashMap<Integer, ItemStack> removedItems = player.getInventory().removeItem(new ItemStack(itemType, setCount));
                                databaseManager.increaseMoney(player, totalCost);
                                pluginInstance.updateScoreboard(player);

                            } else if (clickEvent.isLeftClick()) {
                                // 구매 불가능한 아이템 처리
                                player.sendMessage("§6[상점] §f구매가 불가능한 아이템입니다.");
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
            if (inventory != null && inventory.getType() == InventoryType.PLAYER) {
                // 클릭한 인벤토리가 플레이어 인벤토리인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
            }

            if (event.getCurrentItem().getType() == Material.HEART_OF_THE_SEA) {
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "발행할 금액을 채팅으로 입력해주세요.");
                player.sendMessage(ChatColor.YELLOW + "취소하려면 '취소'을 입력하세요.");

                // 채팅 입력 대기 상태로 설정
                isAwaitingChat.put(player.getUniqueId(), true);
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
                    if (amount <= 0) {
                        player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                    } else {
                        databaseManager.decreaseMoney(player, amount); // 플레이어의 잔액을 데이터베이스에서 차감합니다.
                        pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트
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

    private boolean isMineralItem(Material itemType) {
        return itemType == Material.FLINT || itemType == Material.IRON_INGOT || itemType == Material.GOLD_INGOT || itemType == Material.DIAMOND ||
                itemType == Material.QUARTZ || itemType == Material.AMETHYST_SHARD || itemType == Material.COPPER_INGOT ||
                itemType == Material.EMERALD || itemType == Material.NETHERITE_INGOT || itemType == Material.COAL_BLOCK ||
                itemType == Material.LAPIS_BLOCK || itemType == Material.REDSTONE_BLOCK || itemType == Material.OBSIDIAN ||
                itemType == Material.CRYING_OBSIDIAN || itemType == Material.GLOWSTONE;
    }


    private int countItems(Inventory inventory, Material itemType) {
        int itemCount = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == itemType) {
                itemCount += item.getAmount();
            }
        }
        return itemCount;
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
        if (item != null && item.getItemMeta() != null) {
            // 아이템의 메타데이터 가져오기
            ItemMeta meta = item.getItemMeta();
            if (meta.getDisplayName().contains("골드")) { //수표 사용
                String amountString = ChatColor.stripColor(meta.getDisplayName()).replace("골드", "");
                try {
                    int amount = Integer.parseInt(amountString);
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    databaseManager.increaseMoney(player, amount);
                    pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "수표 사용 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }
            if (meta.getDisplayName().contains("§6[ 직업 ] §f광부 1차")) {
                if (!databaseManager.getPlayerJob(player).equals("초보자") && !databaseManager.getPlayerJob(player).equals("백수")) {
                    player.sendMessage("직업을 초기화 해야 합니다");
                    return;
                }


                try {
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    databaseManager.setPlayerJob(player, "광부 1차");
                    pluginInstance.updateScoreboard(player); // 스코어보드 업데이트

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "전직 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }else if (meta.getDisplayName().contains("§6[ 직업 ] §f광부 2차")) {
                if(!databaseManager.getPlayerJob(player).equals("광부 1차")){
                    player.sendMessage("광부 1차만 전직 가능합니다.");
                    return;
                }
                try {
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    databaseManager.setPlayerJob(player, "광부 2차");
                    pluginInstance.updateScoreboard(player); // 스코어보드 업데이트

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "전직 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }else if (meta.getDisplayName().contains("§6[ 직업 ] §f광부 3차")) {
                if(!databaseManager.getPlayerJob(player).equals("광부 2차")){
                    player.sendMessage("광부 2차만 전직 가능합니다.");
                    return;
                }
                try {
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    abilityManager.applyFireResistance(player); // 화염 저항 버프 함수 호출
                    databaseManager.setPlayerJob(player, "광부 3차");
                    pluginInstance.updateScoreboard(player); // 스코어보드 업데이트

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "전직 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }else if (meta.getDisplayName().contains("§6[ 직업 ] §f광부 4차")) {
                if(!databaseManager.getPlayerJob(player).equals("광부 3차")){
                    player.sendMessage("광부 3차만 전직 가능합니다.");
                    return;
                }
                try {
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    databaseManager.setPlayerJob(player, "광부 4차");
                    pluginInstance.updateScoreboard(player); // 스코어보드 업데이트

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "전직 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }
            if (meta.getDisplayName().contains("§6[ 직업 초기화권 ]")) {
                try {
                    if (event.getItem().getAmount() > 1) {
                        // 아이템 스택에 수표가 여러 개 있는 경우 스택 크기 감소
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        // 아이템 스택에 수표가 하나만 있는 경우 플레이어 인벤토리에서 제거
                        player.getInventory().remove(item);
                    }
                    databaseManager.setPlayerJob(player, "백수");
                    pluginInstance.updateScoreboard(player); // 스코어보드 업데이트

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "전직 중 오류가 발생했습니다."); // 금액 변환 실패 시 오류 처리
                }
            }

        }
    }
}
