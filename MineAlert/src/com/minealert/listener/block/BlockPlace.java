package com.minealert.listener.block;

import com.minealert.patches.PatchesUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();
        switch (type) {
            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case DIAMOND_ORE:
            case EMERALD:
                PatchesUtil.getInstance().addBlockLocation(block.getLocation());
                break;
            case SPAWNER:
                EntityType entityType = ((CreatureSpawner) block.getState()).getSpawnedType();
                if (entityType == EntityType.SKELETON || entityType == EntityType.ZOMBIE || entityType == EntityType.BLAZE ||
                        entityType == EntityType.CAVE_SPIDER || entityType == EntityType.SPIDER) {
                    PatchesUtil.getInstance().addBlockLocation(block.getLocation());
                }
                break;
            default:
                break;
        }
    }
}
