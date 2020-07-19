package com.minealert.listener.inventory;
import com.minealert.builder.ItemBuilder;
import com.minealert.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpectateMode implements Listener {

    private String prefix = ConfigManager.getInstance().getString("MineAlertPrefix");

    @EventHandler
    public void giveNightVision(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Settings Menu")) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(ChatColor.GREEN + "Enable Spectator Mode")) {
                    e.setCancelled(true);
                    if (!player.hasPermission("minealert.spectate")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou don't have enough permission to use this option!"));
                        player.closeInventory();
                        return;
                    }

                    if (player.getGameMode() == GameMode.SPECTATOR) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You're already in Spectator Mode!!"));
                        return;
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You have enabled Spectator Mode!"));
                    player.setGameMode(GameMode.SPECTATOR);
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void clearEffect(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Settings Menu")) {
            if (ItemBuilder.hasDisplayName(e.getCurrentItem())) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                if (name.equalsIgnoreCase(ChatColor.RED + "Disable Spectator Mode")) {
                    e.setCancelled(true);
                    if (!player.hasPermission("minealert.spectate")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou don't have enough permission to use this option!"));
                        player.closeInventory();
                        return;
                    }

                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You're not in Spectator Mode!!"));
                        return;
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You have disable Spectator Mode!"));
                    player.setGameMode(GameMode.SURVIVAL);
                    player.closeInventory();
                }
            }
        }
    }
}
