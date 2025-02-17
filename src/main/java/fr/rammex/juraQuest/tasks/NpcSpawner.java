package fr.rammex.juraQuest.tasks;

import fr.rammex.juraQuest.JuraQuest;
import fr.rammex.juraQuest.npc.NpcManager;
import fr.rammex.juraQuest.npc.NpcUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class NpcSpawner {

    private final JavaPlugin plugin;
    private NpcUtils npcUtils = new NpcUtils();

    public NpcSpawner(JavaPlugin plugin) {
        this.plugin = plugin;
        startSpawning();
    }

    int timeNpcStay = JuraQuest.getInstance().getConfig().getInt("npc_stay");
    int npcTimeToRespawn = JuraQuest.getInstance().getConfig().getInt("npc_time_to_respawn");

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
            }, timeNpcStay); // 100 ticks = 5 seconds 100/5*timeNpcStay

        }, 0L, npcTimeToRespawn);
    }

    public void stopSpawning() {
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}