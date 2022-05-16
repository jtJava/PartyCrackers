package me.jaden.partycrackers.listener;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.jaden.partycrackers.PartyCrackers;
import me.jaden.partycrackers.cracker.Cracker;
import me.jaden.partycrackers.cracker.CrackerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class DropListener implements Listener {
    private final CrackerManager crackerManager;

    @EventHandler(ignoreCancelled = true)
    void onDrop(PlayerDropItemEvent event) {
        ItemStack droppedStack = event.getItemDrop().getItemStack();

        String persistentString = droppedStack.getItemMeta().getPersistentDataContainer().get(PartyCrackers.getPlugin().getNamedspacedKey(), PersistentDataType.STRING);

        if (persistentString == null) {
            return;
        }

        Optional<Cracker> crackerOptional = this.crackerManager.getCracker(persistentString);
        if (crackerOptional.isPresent()) {
            event.getItemDrop().setCanPlayerPickup(false);
            event.getItemDrop().setCanMobPickup(false);

            Cracker cracker = crackerOptional.get();
            cracker.onDrop(event.getItemDrop().getLocation());
            cracker.onUse(event.getItemDrop());
        }
    }
}
