package com.rkfcl.server_info.Manager;


import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {
    public static HashMap<UUID, Integer> playerBalances = new HashMap<UUID, Integer>();
    public static HashMap<UUID, String> playerJob = new HashMap<UUID, String>();




    public void setPlayerBalance(UUID player, int balance) {
        playerBalances.put(player, balance);

    }
    public void setPlayerJob(UUID player, String Job) {
        playerJob.put(player, Job);

    }

    public void increaseMoney(UUID player,int balance){
        playerBalances.put(player,playerBalances.get(player)+balance);
    }

    public void decreaseMoney(UUID player, int balance) {
        playerBalances.put(player,playerBalances.get(player)-balance);
    }


    public int getPlayerBalance(UUID player) {
        if (playerBalances.containsKey(player)) {
            return playerBalances.get(player);
        }
        return 0;
    }

    public String getPlayerJob(UUID player) {
        if (playerJob.containsKey(player)) {
            return playerJob.get(player);
        }
        return "초보자";
    }

}



