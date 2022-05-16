package me.jaden.partycrackers.cracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;

@Getter
public class CrackerManager {
    private final Map<String, Cracker> partyCrackers = new HashMap<>();

    public void registerCracker(Cracker cracker) {
        this.partyCrackers.put(cracker.getName(), cracker);
    }

    public Optional<Cracker> getCracker(String name) {
        return Optional.ofNullable(this.partyCrackers.get(name));
    }
}
