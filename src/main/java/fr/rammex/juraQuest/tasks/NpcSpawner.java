package fr.rammex.juraQuest.tasks;

import fr.rammex.juraQuest.npc.NpcManager;
import fr.rammex.juraQuest.npc.NpcUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class NpcSpawner {

    private final JavaPlugin plugin;
    private NpcUtils npcUtils = new NpcUtils();

    public NpcSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
        startSpawning();
    }

    public void startSpawning() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            NPC randomNpc = NpcManager.getRandomNpc();
            if(randomNpc == null){
                return;
            }
            Location location = NpcManager.getLocation(randomNpc);
            randomNpc.spawn(location);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (randomNpc != null && randomNpc.isSpawned()) {
                    randomNpc.despawn();
                }
            }, 100L); // 100 ticks = 5 seconds

        }, 0L, 200L); // 200 ticks = 10 seconds
    }
}