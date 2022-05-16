package me.jaden.partycrackers.util;

import java.util.List;
import java.util.Map;
import me.jaden.partycrackers.PartyCrackers;
import me.jaden.partycrackers.cracker.Cracker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SerializedItem extends ReflectedSerializable {
    private final transient Material material;

    private String materialName;
    private String displayName;
    private List<String> lore;
    private int amount;
    private boolean glow;

    public SerializedItem(Map<String, Object> map) {
        super(map);
        this.material = Material.getMaterial(this.materialName);
    }

    public ItemStack toItem() {
        ItemBuilder builder = new ItemBuilder(this.material);
        if (this.amount > 0) builder.amount(this.amount);
        if (this.lore != null) builder.lore(this.lore);
        if (this.displayName != null) builder.displayname(this.displayName);
        if (this.glow) builder.glow();

        return builder.build();
    }

    public ItemStack toCrackerItem(Cracker cracker, int amount) {
        ItemBuilder builder = new ItemBuilder(this.material).amount(amount);
        if (this.lore != null) builder.lore(this.lore);
        if (this.displayName != null) builder.displayname(this.displayName);
        if (this.glow) builder.glow();

        ItemStack stack = builder.build();

        ItemMeta newItemMeta = stack.getItemMeta();
        newItemMeta.getPersistentDataContainer().set(PartyCrackers.getPlugin().getNamedspacedKey(), PersistentDataType.STRING, cracker.getName());
        stack.setItemMeta(newItemMeta);

        return stack;
    }
}
