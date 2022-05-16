package me.jaden.partycrackers.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import java.util.Optional;
import me.jaden.partycrackers.PartyCrackers;
import me.jaden.partycrackers.cracker.Cracker;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@CommandAlias("pc|partycrackers")
public class CrackerCommand extends BaseCommand {
    @Subcommand("give")
    @Syntax("[player] [amount] [cracker]")
    @CommandCompletion("@players @range:0-64 @crackers")
    public static void give(Player executor, OnlinePlayer onlinePlayer, int amount, String cracker) {
        Optional<Cracker> optional = PartyCrackers.getPlugin().getCrackerManager().getCracker(cracker);
        if (optional.isPresent()) {
            Cracker actualCracker = optional.get();
            int amountToGive = Math.min(amount, 64);

            onlinePlayer.getPlayer().getInventory().addItem(actualCracker.getItem().toCrackerItem(actualCracker, amountToGive));
            executor.sendMessage(Component.text("You have given " + onlinePlayer.getPlayer().getName() + " " + amountToGive + " " + cracker + " crackers."));
        } else {
            executor.sendMessage(Component.text("We couldn't find a cracker with the given name."));
        }
    }

    @Subcommand("reload")
    public static void reload(Player executor) {
        try {
            PartyCrackers.getPlugin().getCrackerConfig().reload();
            executor.sendMessage("PartyCrackers config successfully reloaded.");
        } catch (Exception exception) {
            exception.printStackTrace();
            executor.sendMessage("PartyCrackers encountered an issue while attempting to reload.");
        }
    }
}
