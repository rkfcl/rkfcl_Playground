package com.rkfcl.server_info;

import com.rkfcl.server_info.ItemManagerCost.OreCost;
import com.rkfcl.server_info.Manager.*;
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
    private PlayerDataManager playerDataManager;
    private final AbilityManager abilityManager;
    private Map<UUID, Boolean> isAwaitingChat = new HashMap<>();

    ItemManager itemManager = new ItemManager();
    OreCost oreCost = new OreCost();
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
                    }
                }


                if (meta != null && meta.getDisplayName().equals("§6[ 직업 ] §f초기화권")) {
                    handleJobPurchase(player, clickEvent, clickType, 9, 30000, 1);
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
                                playerDataManager.increaseMoney(player.getUniqueId(), totalCost);
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
            event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함
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
                    if (amount <= 0 && amount > playerDataManager.getPlayerBalance(playerUUID)) {
                        player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                    } else {
                        playerDataManager.decreaseMoney(player.getUniqueId(), amount); // 플레이어의 잔액을 데이터베이스에서 차감합니다.

                        Bukkit.getScheduler().runTask(pluginInstance, () -> {
                            pluginInstance.updateScoreboard(player); // 새로운 잔액으로 스코어보드 업데이트
                        });

                        ItemStack check = ItemManager.createCheck(amount);
                        player.getInventory().addItem(check);
                        System.out.println("heck");
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
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        String displayName = meta.getDisplayName();

        if (displayName.contains("골드")) {
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
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 1차");
        } else if (jobName.equals("광부 2차")) {
            if (!playerJob.equals("광부 1차")) {
                player.sendMessage("광부 1차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 2차");
        } else if (jobName.equals("광부 3차")) {
            if (!playerJob.equals("광부 2차")) {
                player.sendMessage("광부 2차만 전직 가능합니다.");
                return;
            }
            abilityManager.applyFireResistance(player);
            playerDataManager.setPlayerJob(player.getUniqueId(), "광부 3차");
        } else if (jobName.equals("광부 4차")) {
            if (!playerJob.equals("광부 3차")) {
                player.sendMessage("광부 3차만 전직 가능합니다.");
                return;
            }
           playerDataManager.setPlayerJob(player.getUniqueId(), "광부 4차");
        } else if (jobName.equals("농부 1차")) {
            if (!playerJob.equals("초보자") && !playerJob.equals("백수")) {
                player.sendMessage("직업을 초기화 해야 합니다");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 1차");
        } else if (jobName.equals("농부 2차")) {
            if (!playerJob.equals("농부 1차")) {
                player.sendMessage("농부 1차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 2차");
        } else if (jobName.equals("농부 3차")) {
            if (!playerJob.equals("농부 2차")) {
                player.sendMessage("농부 2차만 전직 가능합니다.");
                return;
            }
            abilityManager.applyFireResistance(player);
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 3차");
        } else if (jobName.equals("농부 4차")) {
            if (!playerJob.equals("농부 3차")) {
                player.sendMessage("농부 3차만 전직 가능합니다.");
                return;
            }
            playerDataManager.setPlayerJob(player.getUniqueId(), "농부 4차");
        } else if (jobName.equals("초기화권")) {
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
