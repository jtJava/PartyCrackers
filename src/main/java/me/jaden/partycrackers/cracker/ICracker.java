package me.jaden.partycrackers.cracker;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface ICracker {
    void onDrop(Location location);

    void onUse(Entity entity);
}
