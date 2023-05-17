package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.test;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class opcommand implements CommandExecutor {
    private final test plugin;

    public opcommand(test plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "사용법: /setmoney [플레이어] [값]");
            return true;
        }

        String playerName = args[0];
        Player targetPlayer = plugin.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "플레이어를 찾을 수 없습니다.");
            return true;
        }

        String amountString = args[1];
        try {
            int amount = Integer.parseInt(amountString);
            if (amount < 0) {
                sender.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
                return true;
            }

            plugin.setMoneyInDatabase(targetPlayer, amount); // 플레이어의 데이터베이스 소지금을 설정합니다.
            plugin.updateScoreboard(targetPlayer); // 플레이어의 스코어보드를 업데이트합니다.
            sender.sendMessage(ChatColor.GREEN + targetPlayer.getName() + "의 소지금이 설정되었습니다.");
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "올바른 금액을 입력해주세요.");
        }

        return true;
    }
}
