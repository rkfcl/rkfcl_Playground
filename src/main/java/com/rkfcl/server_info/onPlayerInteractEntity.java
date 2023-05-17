package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.ShopInventoryManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class onPlayerInteractEntity implements Listener {
    ShopInventoryManager shopInventoryManager = new ShopInventoryManager();

    private JavaPlugin plugin;

    public onPlayerInteractEntity(JavaPlugin plugin) {
        this.plugin = plugin;
        disableTrade(); // 주민 거래 비활성화
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClickedEntity = event.getRightClicked();
        if (rightClickedEntity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) rightClickedEntity;
            String villagerName = villager.getCustomName();


            if (villagerName != null && villagerName.equals("전직교관")) {
                shopInventoryManager.openShopJobInventory(player);

                event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다
            }else event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다

            if (villagerName != null && villagerName.equals("광부")) {
                shopInventoryManager.openShopMineInventory(player);

                event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다
            }else event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다
        }
    }


    public void enableTrade() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void disableTrade() {
        PlayerInteractEntityEvent.getHandlerList().unregister(this);
    }
}