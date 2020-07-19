package com.minealert.main;

import com.minealert.alert.MineAlertDataTypes;
import com.minealert.commands.MineAlertCommands;
import com.minealert.config.ConfigManager;
import com.minealert.listener.block.BlockBreak;
import com.minealert.listener.inventory.*;
import com.minealert.listener.join.ConnectingListeners;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class MineAlert extends JavaPlugin {

    public static Plugin plugin;
    private static int interval;

    public void onEnable() {
        plugin = this;
        interval = ConfigManager.getInstance().getInteger("Interval");
        registerCommands();
        registerListeners();
        registerConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                interval--;
                if (interval <= 0) {
                    resetDataTypes();
                    interval = ConfigManager.getInstance().getInteger("Interval");
                }
            }
        }.runTaskTimerAsynchronously(this, 20, 20);
    }

    public void onDisable() {
        plugin = null;

    }

    private void registerCommands() {
        addCommand("minealert", new MineAlertCommands("minealert"));
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockBreak(), this);
        pm.registerEvents(new ConnectingListeners(), this);
        pm.registerEvents(new CloseInventory(), this);
        pm.registerEvents(new CancelInventoryClick(), this);
        pm.registerEvents(new ItemPickUpSetting(), this);
        pm.registerEvents(new NightVision(), this);
        pm.registerEvents(new SpectateMode(), this);
    }

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void addCommand(String cmd, BukkitCommand bc) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(cmd, bc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetDataTypes() {
        MineAlertDataTypes.coalMined.clear();
        MineAlertDataTypes.ironMined.clear();
        MineAlertDataTypes.goldMined.clear();
        MineAlertDataTypes.lapisMined.clear();
        MineAlertDataTypes.redstoneMined.clear();
        MineAlertDataTypes.diamondsMined.clear();
        MineAlertDataTypes.emeraldsMined.clear();
        MineAlertDataTypes.spawnersMined.clear();

        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            addToDataTypes(all);
        }
        if (MineAlertDataTypes.receiver.isEmpty()) {
            return;
        }
    }

    public static void addToDataTypes(Player player) {
        boolean check = MineAlertDataTypes.coalMined.containsKey(player.getName()) &&
                MineAlertDataTypes.ironMined.containsKey(player.getName()) && MineAlertDataTypes.goldMined.containsKey(player.getName()) &&
                MineAlertDataTypes.lapisMined.containsKey(player.getName()) && MineAlertDataTypes.redstoneMined.containsKey(player.getName()) &&
                MineAlertDataTypes.diamondsMined.containsKey(player.getName()) && MineAlertDataTypes.emeraldsMined.containsKey(player.getName())
                && MineAlertDataTypes.spawnersMined.containsKey(player.getName());
        if (!check) {
            MineAlertDataTypes.coalMined.put(player.getName(), 0);
            MineAlertDataTypes.ironMined.put(player.getName(), 0);
            MineAlertDataTypes.goldMined.put(player.getName(), 0);
            MineAlertDataTypes.lapisMined.put(player.getName(), 0);
            MineAlertDataTypes.redstoneMined.put(player.getName(), 0);
            MineAlertDataTypes.diamondsMined.put(player.getName(), 0);
            MineAlertDataTypes.emeraldsMined.put(player.getName(), 0);
            MineAlertDataTypes.spawnersMined.put(player.getName(), 0);
        }
    }

    public static void removeFromDataTypes(Player player){
        boolean check = MineAlertDataTypes.coalMined.containsKey(player.getName()) &&
                MineAlertDataTypes.ironMined.containsKey(player.getName()) && MineAlertDataTypes.goldMined.containsKey(player.getName()) &&
                MineAlertDataTypes.lapisMined.containsKey(player.getName()) && MineAlertDataTypes.redstoneMined.containsKey(player.getName()) &&
                MineAlertDataTypes.diamondsMined.containsKey(player.getName()) && MineAlertDataTypes.emeraldsMined.containsKey(player.getName())
                && MineAlertDataTypes.spawnersMined.containsKey(player.getName());
        if (check) {
            MineAlertDataTypes.coalMined.remove(player.getName());
            MineAlertDataTypes.ironMined.remove(player.getName());
            MineAlertDataTypes.goldMined.remove(player.getName());
            MineAlertDataTypes.lapisMined.remove(player.getName());
            MineAlertDataTypes.redstoneMined.remove(player.getName());
            MineAlertDataTypes.diamondsMined.remove(player.getName());
            MineAlertDataTypes.emeraldsMined.remove(player.getName());
            MineAlertDataTypes.spawnersMined.remove(player.getName());
        }
    }

    public static int getInterval() {
        return interval;
    }
}
