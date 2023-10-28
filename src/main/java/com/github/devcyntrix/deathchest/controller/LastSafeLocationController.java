package com.github.devcyntrix.deathchest.controller;

import com.github.devcyntrix.deathchest.DeathChestPlugin;
import com.github.devcyntrix.deathchest.util.LastLocationMetadata;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import javax.annotation.Nullable;
import java.util.List;

public class LastSafeLocationController {

    private static final String lastSafePosition = "last-safe-position";

    private final DeathChestPlugin plugin;

    public LastSafeLocationController(DeathChestPlugin plugin) {
        this.plugin = plugin;
    }

    public void updatePosition(Player player) {
        Location blockLoc = player.getLocation().getBlock().getLocation();
        if (!plugin.canPlaceChestAt(blockLoc))
            return;

        List<MetadataValue> metadata = player.getMetadata(lastSafePosition);
        if (metadata.isEmpty()) {
            player.setMetadata(lastSafePosition, new LastLocationMetadata(plugin, blockLoc.clone()));
        } else {
            MetadataValue metadataValue = metadata.get(0);
            if (!(metadataValue instanceof LastLocationMetadata meta))
                return;
            if (System.currentTimeMillis() - meta.getUpdatedAt() < 500)
                return;

            meta.setLocation(blockLoc.clone());
        }
    }

    public @Nullable Location getPosition(Player player) {
        List<MetadataValue> metadata = player.getMetadata(lastSafePosition);
        if (metadata.isEmpty())
            return null;

        MetadataValue metadataValue = metadata.get(0);
        if (!(metadataValue instanceof LastLocationMetadata meta))
            return null;
        return meta.value();
    }
}
