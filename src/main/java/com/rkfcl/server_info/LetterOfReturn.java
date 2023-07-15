package com.rkfcl.server_info;


import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.*;
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
        ItemStack items = event.getPlayer().getInventory().getItemInMainHand();
        if (items != null && items.isSimilar(CustomStack.getInstance("letter_of_return").getItemStack())){

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
                                World overworld = Bukkit.getWorld("world"); // 오버월드를 나타내는 World 객체를 가져옵니다.
                                Location spawnLocation = overworld.getSpawnLocation(); // 오버월드의 스폰 위치를 가져옵니다.

                                // 플레이어를 오버월드의 스폰 위치로 이동시킵니다.
                                player.teleport(spawnLocation);

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
