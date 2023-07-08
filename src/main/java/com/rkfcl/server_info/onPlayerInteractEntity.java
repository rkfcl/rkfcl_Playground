package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.ShopInventoryManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

            if (villagerName != null) {
                switch (villagerName) {
                    case "전직교관":
                        shopInventoryManager.openShopJobInventory(player);
                        break;
                    case "광부":
                        shopInventoryManager.openShopMineInventory(player);
                        break;
                    case "어부":
                        shopInventoryManager.openShopFishInventory(player);
                        break;
                    case "농부":
                        shopInventoryManager.openShopFarmer1Inventory(player);
                        break;
                    case "농부2":
                        shopInventoryManager.openShopFarmer2Inventory(player);
                        break;
                    case "잡화상점":
                        shopInventoryManager.openShopItemsInventory(player);
                        break;
                    case "도축업자":
                        shopInventoryManager.openShopButcherInventory(player);
                        break;
                    case "코인상점":
                        shopInventoryManager.coinshopinventory(player);
                        break;
                    default:
                        event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다
                        return;
                }

                event.setCancelled(true); // 기본 상호작용을 방지하기 위해 이벤트를 취소합니다
            }
        }
    }

        @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damagedEntity = event.getEntity();
        Entity damager = event.getDamager();

        if (damagedEntity instanceof Villager && damager instanceof Player) {
            event.setCancelled(true); // Villager가 플레이어에게서의 공격을 받을 때 이벤트를 취소하여 데미지를 막습니다.
        }
    }


    public void enableTrade() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void disableTrade() {
        PlayerInteractEntityEvent.getHandlerList().unregister(this);
    }
}