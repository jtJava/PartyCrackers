package me.jaden.partycrackers.cracker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import me.jaden.partycrackers.PartyCrackers;
import me.jaden.partycrackers.util.RandomUtil;
import me.jaden.partycrackers.util.ReflectedSerializable;
import me.jaden.partycrackers.util.SerializedItem;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitScheduler;

@Getter
public class Cracker extends ReflectedSerializable implements ICracker {
    private String name;
    private int seconds;

    private List<String> particleNames;

    private List<String> explosionSoundNames;
    private String dropSound;

    private SerializedItem item;

    private List<CrackerReward> rewards;

    public Cracker(Map<String, Object> map) {
        super(map);
    }

    @Override
    public void onDrop(Location location) {
        location.getWorld().playSound(location, Sound.valueOf(this.dropSound), 1, 1);
    }

    @Override
    public void onUse(Entity entity) {
        BukkitScheduler scheduler = PartyCrackers.getPlugin().getServer().getScheduler();

        float radius = 1.5f;
        AtomicReference<Float> angle = new AtomicReference<>(0f);

        scheduler.runTaskTimer(PartyCrackers.getPlugin(), task -> {
            if (entity.isValid()) {
                Location location = entity.getLocation();

                double x = (radius * Math.sin(angle.get()));
                double z = (radius * Math.cos(angle.get()));
                angle.updateAndGet(v -> (float) (v + 0.1));

                location.getWorld().spawnParticle(this.getRandomParticle(), location.getX() + x, location.getY(), location.getZ() + z, 0, 0, 1, 0);
            } else {
                task.cancel();
            }
        }, 0, 1);

        scheduler.runTaskLater(PartyCrackers.getPlugin(), () -> {
            World world = entity.getWorld();
            Location location = entity.getLocation();

            for (CrackerReward reward : this.rewards) {
                if (RandomUtil.tryChance(reward.getChance())) {
                    world.dropItemNaturally(location, reward.getReward().toItem());
                }
            }

            world.playSound(location, this.getRandomSound(), 1, 1);
            entity.remove();
        }, this.seconds * 20L);
    }

    private Sound getRandomSound() {
        return Sound.valueOf(RandomUtil.randomElement(this.explosionSoundNames));
    }

    private Particle getRandomParticle() {
        return Particle.valueOf(RandomUtil.randomElement(this.particleNames));
    }
}
