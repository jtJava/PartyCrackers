package me.jaden.partycrackers.cracker;

import java.util.Map;
import lombok.Getter;
import me.jaden.partycrackers.util.ReflectedSerializable;
import me.jaden.partycrackers.util.SerializedItem;

@Getter
public class CrackerReward extends ReflectedSerializable {
    // Chance out of 100
    private int chance;
    private SerializedItem reward;

    public CrackerReward(Map<String, Object> map) {
        super(map);
    }
}
