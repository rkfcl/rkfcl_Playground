package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.Manager.ItemManager;
import com.rkfcl.server_info.Manager.PlayerDataManager;
import com.rkfcl.server_info.test;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCheckCommand implements CommandExecutor {
    private PlayerDataManager playerDataManager;
    private test plugin;

    public GiveCheckCommand(test plugin, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "플레이어만 이 명령어를 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "사용할 금액을 입력해주세요.");
            return true;
        }

        String amountString = args[0];
        try {
            int amount = Integer.parseInt(amountString);
            if (amount <= 0) {
                player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                return true;
            }

            int playerBalance = playerDataManager.getPlayerBalance(player.getUniqueId());
            if (playerBalance < amount) {
                player.sendMessage(ChatColor.RED + "보유한 소지금보다 큰 금액은 사용할 수 없습니다.");
                return true;
            }

            playerDataManager.decreaseMoney(player.getUniqueId(), amount); // 플레이어의 잔액을 데이터베이스에서 차감합니다.
            issueCheck(player, amount); // 수표 발행
            plugin.updateScoreboard(player); // 새로운 잔액으로 스코어보드를 업데이트합니다.
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
        }

        return true;
    }

    private void issueCheck(Player player, int amount) {
        ItemStack check = ItemManager.createCheck(amount);
        player.getInventory().addItem(check);
    }
}
