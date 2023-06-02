package com.rkfcl.server_info.Manager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class FishingManager implements Listener {
    PlayerDataManager playerDataManager;
    public FishingManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            Entity caughtEntity = event.getCaught();

            if (caughtEntity instanceof Item) {
                ItemStack caughtFish = ((Item) caughtEntity).getItemStack();
                // 어부 직업을 확인하는 로직
                if (isFisher(player).contains("어부")) {
                    // 어부인 경우
                    player.sendMessage("어부 맞음!");
                    player.getInventory().addItem(caughtFish);
                } else {
                    // 어부가 아닌 경우
                    // cod, salmon, tropical fish만 얻을 수 있도록 처리
                    if (caughtFish.getType() == Material.COD || caughtFish.getType() == Material.SALMON || caughtFish.getType() == Material.TROPICAL_FISH) {
                        player.getInventory().addItem(new ItemStack(Material.COD));
                        event.getHook().remove(); // 낚시줄 제거
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    private String isFisher(Player player) {
        String fisher = playerDataManager.getPlayerJob(player.getUniqueId());
        return fisher;
    }
}
