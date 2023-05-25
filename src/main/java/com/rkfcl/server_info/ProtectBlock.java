package com.rkfcl.server_info;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectBlock implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.hasItem() && event.getItem().getType() == Material.STICK) {
            // 특정 아이템(여기서는 STICK)을 클릭했을 때 실행할 명령어
            boolean isOp = player.isOp();
            try {
                player.setOp(true);
                player.performCommand("/hpos1");
                player.performCommand("/hpos2");
                player.performCommand("/expand 5 5 east");
                player.performCommand("/expand 5 5 south");
                player.performCommand("/expand 10 up");
                player.performCommand("/outline red_stained_glass");
                player.performCommand("/rg define"+player.getName());
                player.performCommand("/rg addowner"+player.getName()+player.getName());
            } finally {
                if (!isOp) {
                    player.setOp(false);
                }
            }
        }
    }
}
