package fr.rammex.juraQuest;

import fr.rammex.juraQuest.commands.CommandJuraQuest;
import fr.rammex.juraQuest.npc.trait.TraitOnClick;
import fr.rammex.juraQuest.tasks.NpcLoader;
import fr.rammex.juraQuest.tasks.NpcSpawner;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JuraQuest extends JavaPlugin {

    @Getter
    public static JuraQuest instance;

    @Override
    public void onEnable() {
        instance = this;

        // CONFIG
        this.saveDefaultConfig();

        // COMMANDS
        this.getCommand("juraquest").setExecutor(new CommandJuraQuest());


        // NPC TRAITS
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(TraitOnClick.class));

        // LOAD NPCS
        loadNpc();

        // RANDOM SPAWN
        NpcSpawner npcSpawner = new NpcSpawner(this);
        npcSpawner.startSpawning();
    }

    @Override
    public void onDisable() {
    }

    private void loadNpc(){
        Bukkit.getScheduler().runTaskLater(this, () -> {
            NpcLoader npcLoader = new NpcLoader();
            npcLoader.loadNpcs();
        }, 80L);
    }
}
