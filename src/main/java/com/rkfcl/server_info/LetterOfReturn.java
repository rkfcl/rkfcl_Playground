package com.rkfcl.server_info;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;


public class LetterOfReturn implements Listener {
    private test plugin;

    public LetterOfReturn(test plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 좌클릭인 경우 처리하지 않음
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.PAPER) {
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == 100) {
                Location spawnLocation = player.getWorld().getSpawnLocation(); // 스폰 위치 설정
                Location initialLocation = player.getLocation();
                ItemStack usedItem = player.getInventory().getItemInMainHand();
                usedItem.setAmount(usedItem.getAmount() - 1); // 아이템 소비
                new BukkitRunnable() {
                    int timeLeft = 5;
                    boolean cancel = false;

                    @Override
                    public void run() {
                        if (!cancel && !player.isDead() && !player.isSprinting() && !player.isSwimming() && !player.isFlying()) {
                            if (player.getLocation().distance(initialLocation) > 0 || player.getNoDamageTicks() != 0) {
                                // 플레이어가 움직이거나 공격을 받은 경우 귀환 취소
                                cancel = true;
                                player.sendMessage(ChatColor.RED + "귀환이 취소되었습니다.");
                                return;
                            }
                            if (timeLeft > 0) {
                                player.sendMessage(ChatColor.YELLOW + "귀환까지 " + timeLeft + "초 남았습니다."); // 남은 시간 메시지 전송
                                timeLeft--;
                            } else {
                                player.teleport(spawnLocation); // 이동
                                cancel(); // 타이머 종료
                            }
                        } else {
                            cancel(); // 타이머 종료
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);
            }
        }
    }


}
