package de.helixdevs.deathchest.hologram;

import com.google.common.base.Preconditions;
import de.helixdevs.deathchest.api.hologram.IHologramTextLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NativeHologramTextLine implements IHologramTextLine {

    private final Location location;
    private final UUID armorStand;

    public NativeHologramTextLine(@NotNull Location location, @NotNull String text) {
        Preconditions.checkNotNull(location.getWorld());
        this.location = location;
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class, stand -> {
            stand.setMarker(true);
            stand.setInvisible(true);
            stand.setCustomName(text);
            stand.setCustomNameVisible(true);
        }).getUniqueId();
    }

    public Location getLocation() {
        return location;
    }

    public ArmorStand getArmorStand() {
        return (ArmorStand) Bukkit.getEntity(armorStand);
    }

    @Override
    public void rename(@NotNull String text) {
        ArmorStand stand = getArmorStand();
        if (stand == null || stand.isDead())
            return;
        stand.setCustomName(text);
    }

    @Override
    public void remove() {
        getArmorStand().remove();
    }
}
