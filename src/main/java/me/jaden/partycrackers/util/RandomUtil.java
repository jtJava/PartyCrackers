package me.jaden.partycrackers.util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtil {
    public boolean tryChance(int chance) {
        return ThreadLocalRandom.current().nextInt(0, 101) <= chance;
    }

    public <T> T randomElement(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
