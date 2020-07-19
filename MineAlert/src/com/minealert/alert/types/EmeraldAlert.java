package com.minealert.alert.types;

import com.minealert.alert.MineAlertDataTypes;
import com.minealert.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmeraldAlert {

    private static EmeraldAlert instance;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();


    private EmeraldAlert(){

    }

    public static synchronized EmeraldAlert getInstance() {
        if(instance == null){
            instance = new EmeraldAlert();
        }
       return instance;
    }

    public void addToDataType(Player miner) {
        //Adding on to the hashmap
        if (MineAlertDataTypes.emeraldsMined.containsKey(miner.getName())) {
            MineAlertDataTypes.emeraldsMined.put(miner.getName(), MineAlertDataTypes.emeraldsMined.get(miner.getName()) + 1);
        }
    }

    public void notifyPlayers(Player miner) {
        //Notify When Mining
        if (MineAlertDataTypes.emeraldsMined.get(miner.getName()) >= ConfigManager.getInstance().getInteger("EmeraldOre")) {
            Bukkit.getOnlinePlayers().forEach(spies -> {
                if (!spies.hasPermission("minealert.notify")) {
                    return;
                }

                if (!MineAlertDataTypes.alert.get(spies.getName()) || !MineAlertDataTypes.alert.containsKey(spies.getName())) {
                    return;
                }


                if (MineAlertDataTypes.alert.get(spies.getName())) {
                    String prefix = ConfigManager.getInstance().getString("MineAlertPrefix");
                    String message = ConfigManager.getInstance().getString("MineAlertMinedEmerald");
                    message = message.replace("%time%", dtf.format(now));
                    message = message.replace("%player%", miner.getName());
                    message = message.replace("%amount%", MineAlertDataTypes.emeraldsMined.get(miner.getName()).toString());
                    spies.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
                }
            });
        }
    }

    public void call(Player miner) {
        addToDataType(miner);
        notifyPlayers(miner);
    }
}
