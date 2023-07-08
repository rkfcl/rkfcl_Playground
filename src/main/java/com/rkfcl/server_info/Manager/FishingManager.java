package com.rkfcl.server_info.Manager;

import com.rkfcl.server_info.test;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class FishingManager implements Listener {
    List<String> fisher3rd = Arrays.asList(
            "anglerfish",
            "electric_eel",
            "gar",
            "halibut",
            "herring",
            "minnow",
            "muskellunge",
            "perch",
            "pink_salmon",
            "pollock",
            "red_bellied_piranha",
            "tuna"
    );
    PlayerDataManager playerDataManager;
    private test plugin;
    final int[] currentPosition = {0}; // 현재 물고기 위치 (final 배열로 선언)

    private ItemStack caughtFish;
    private ItemStack additionalItem;


    // BukkitTask 객체를 저장할 변수
    BukkitTask task;
    InventoryClickEventHandler clickEventHandler;
    public FishingManager(PlayerDataManager playerDataManager,test plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
        this.task = null; // 초기값으로 null 할당
        this.clickEventHandler = new InventoryClickEventHandler();
        Bukkit.getPluginManager().registerEvents(clickEventHandler, plugin);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            Entity caughtEntity = event.getCaught();

            if (caughtEntity instanceof Item) {
                this.caughtFish = ((Item) caughtEntity).getItemStack();

                // 어부 직업을 확인하는 로직
                if (isFisher(player).contains("어부")) {
                    if (caughtFish.getType() == Material.COD ||
                            caughtFish.getType() == Material.SALMON ||
                            caughtFish.getType() == Material.TROPICAL_FISH ||
                            caughtFish.getType() == Material.PUFFERFISH) {
                        // 어부인 경우 물고기를 잡았을 때만 작동
                        if (!isFisher(player).contains("1차")) {
                            if (Math.random() <= 0.1) { // 10% 확률로 작동
                                giveRandomFish(player);
                            }
                        }
                    }
                } else {
                    // 어부가 아닌 경우
                    // cod, salmon, tropical fish만 얻을 수 있도록 처리
                    if (caughtFish.getType() == Material.COD) {
                        player.getInventory().addItem(new ItemStack(Material.COD));
                    } else if (caughtFish.getType() == Material.SALMON) {
                        player.getInventory().addItem(new ItemStack(Material.SALMON));
                    } else if (caughtFish.getType() == Material.TROPICAL_FISH) {
                        player.getInventory().addItem(new ItemStack(Material.TROPICAL_FISH));
                    }

                    event.getHook().remove(); // 낚시줄 제거
                    event.setCancelled(true);

                }
            }
        }
    }

    private String isFisher(Player player) {
        String fisher = playerDataManager.getPlayerJob(player.getUniqueId());
        return fisher;
    }

    private void giveRandomFish(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Fishing Rewards");
        currentPosition[0]=0;
        gui.clear();
        // GUI 슬롯에 아이템 추가
        ItemStack fishItem = new ItemStack(Material.COD);
        ItemStack glassPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE); // 초록색 유리판 아이템
        ItemStack slimeBall = new ItemStack(Material.SLIME_BALL); // 슬라임볼 아이템

        // 초기 상태에서 맨 왼쪽 슬롯에 물고기 아이템 추가
        gui.setItem(0, fishItem);

        // GUI 슬롯에 랜덤한 위치에 빨간색 유리판 생성
        for (int i = 0; i < 3; i++) {
            int randomSlot = (int) (Math.random() * 9) + 9; // 9부터 17까지 랜덤한 슬롯 번호
            ItemStack redPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            gui.setItem(randomSlot, redPane);
        }

        // 맨 오른쪽 슬롯에 슬라임볼 아이템 추가
        gui.setItem(26, slimeBall);

        // GUI를 플레이어에게 열어줌
        player.openInventory(gui);


        task=Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                // 현재 물고기 위치에서 오른쪽으로 이동
                currentPosition[0]++;
                if (currentPosition[0] >= 9) {
                    currentPosition[0] = 0;
                }

                // 이동한 슬롯에 물고기 아이템 추가
                gui.setItem(currentPosition[0], fishItem);

                // 원래 있던 위치에 초록색 유리판 아이템 추가
                int originalPosition = currentPosition[0] - 1;
                if (originalPosition < 0) {
                    originalPosition = 8;
                }
                gui.setItem(originalPosition, glassPane);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);

                if (currentPosition[0] == 0) {

                    // 실패 소리 재생
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                    // GUI 창 닫기
                    player.closeInventory();
                    if (task != null) {
                        task.cancel();
                    }
                }
            }
        }, 0, 10);

        // 아이템 이동 방지를 위해 클릭 이벤트 취소
        player.getOpenInventory().setCursor(null);

    }

    // InventoryClickEvent를 처리하는 리스너 클래스
    private class InventoryClickEventHandler implements Listener {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            InventoryView view = event.getView();
            if (view.getTitle().equals("Fishing Rewards")) { // Fishing Rewards GUI 창인 경우
                event.setCancelled(true); // 이벤트 취소하여 아이템을 메뉴로 옮기지 못하도록 함

                int clickedSlot = event.getRawSlot();
                if (clickedSlot == 26) { // 슬라임볼 아이템이 클릭된 경우

                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem != null && clickedItem.getType() == Material.SLIME_BALL) {
                        int fishUnderSlot = currentPosition[0] + 9; // 현재 물고기 위치 위의 슬롯
                        ItemStack fishUnder = view.getItem(fishUnderSlot); // 현재 물고기 위치 아래의 아이템
                            Player player = (Player) event.getWhoClicked();

                            // 물고기 위치와 그 아래 아이템이 빨간 유리인지 확인

                        if (fishUnder != null) {
                            if (fishUnder.getType() == Material.RED_STAINED_GLASS_PANE) {
                                if (isFisher(player).contains("2차")||isFisher(player).contains("3차")) {
                                    player.getInventory().addItem(additionalItem); // 플레이어 인벤토리에 아이템 추가
                                }else if (isFisher(player).contains("4차")) {
                                    player.getInventory().addItem(additionalItem); // 플레이어 인벤토리에 아이템 추가
                                    player.getInventory().addItem(additionalItem);
                                }
                                player.sendMessage("성공적으로 추가 물고기를 획득했습니다!"); // 성공 메시지 출력
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                                if (task != null) {
                                    task.cancel();
                                }
                            } else {
                                // 실패 소리 재생
                                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                                if (task != null) {
                                    task.cancel();
                                }
                            }
                        } else {
                            // 실패 소리 재생
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                            if (task != null) {
                                task.cancel();
                            }
                        }
                        player.closeInventory();
                    }
                }
            }
        }
    }
}
