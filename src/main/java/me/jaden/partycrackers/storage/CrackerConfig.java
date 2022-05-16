package me.jaden.partycrackers.storage;

import java.util.List;
import me.jaden.partycrackers.PartyCrackers;
import me.jaden.partycrackers.cracker.Cracker;
import me.jaden.partycrackers.cracker.CrackerManager;
import org.bukkit.Bukkit;

public class CrackerConfig extends Config {
    public CrackerConfig(PartyCrackers plugin) {
        super(plugin, "config");
    }

    @Override
    protected void onLoad() {
        CrackerManager crackerManager = PartyCrackers.getPlugin().getCrackerManager();
        crackerManager.getPartyCrackers().clear();

        List<Cracker> partyCrackers = this.getCrackerList("crackers");

        if (partyCrackers == null) {
            return;
        }

        for (Cracker partyCracker : partyCrackers) {
            crackerManager.registerCracker(partyCracker);
            Bukkit.getLogger().info("Registered a party cracker with the name of " + partyCracker.getName());
        }
    }

    @Override
    protected void onSave() {
        // No Op
    }
}
