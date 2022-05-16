package me.jaden.partycrackers;

import co.aikar.commands.PaperCommandManager;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import me.jaden.partycrackers.command.CrackerCommand;
import me.jaden.partycrackers.cracker.Cracker;
import me.jaden.partycrackers.cracker.CrackerManager;
import me.jaden.partycrackers.cracker.CrackerReward;
import me.jaden.partycrackers.listener.DropListener;
import me.jaden.partycrackers.storage.Config;
import me.jaden.partycrackers.storage.CrackerConfig;
import me.jaden.partycrackers.util.SerializedItem;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PartyCrackers extends JavaPlugin {
    @Getter
    private static PartyCrackers plugin;
    public NamespacedKey namedspacedKey;

    private CrackerManager crackerManager;

    private PaperCommandManager commandManager;

    private Config crackerConfig;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();

        ConfigurationSerialization.registerClass(SerializedItem.class);
        ConfigurationSerialization.registerClass(CrackerReward.class);
        ConfigurationSerialization.registerClass(Cracker.class);

        namedspacedKey = new NamespacedKey(PartyCrackers.getPlugin(), "cracker");

        this.crackerManager = new CrackerManager();
        this.crackerConfig = new CrackerConfig(this);
        
        this.commandManager = new PaperCommandManager(this);
        this.commandManager.getCommandCompletions().registerCompletion("subcommands", string -> Arrays.asList("give", "reload"));
        this.commandManager.getCommandCompletions().registerCompletion("crackers", string ->
                // Replace spaces so that tab completion works.
                new ArrayList<>(this.crackerManager.getPartyCrackers().keySet())
        );
        this.commandManager.registerCommand(new CrackerCommand());

        this.getServer().getPluginManager().registerEvents(new DropListener(this.crackerManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
