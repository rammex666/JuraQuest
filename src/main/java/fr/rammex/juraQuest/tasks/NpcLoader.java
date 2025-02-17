package fr.rammex.juraQuest.tasks;

import fr.rammex.juraQuest.JuraQuest;
import fr.rammex.juraQuest.npc.NpcManager;
import fr.rammex.juraQuest.npc.NpcUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.List;

public class NpcLoader {

    public void loadNpcs() {
        ConfigurationSection npcSection = JuraQuest.getInstance().getConfig().getConfigurationSection("npcs");
        if (npcSection == null) {
            return;
        }

        for (String key : npcSection.getKeys(false)) {
            String name = npcSection.getString(key + ".name");
            String skinName = npcSection.getString(key + ".skin_name");
            Location location = (Location) npcSection.get(key + ".location");

            if (name == null || skinName == null || location == null) {
                System.err.println("NPC configuration is missing required fields: " + key);
                continue;
            }

            try{
                if(CitizensAPI.getNPCRegistry().sorted() != null){
                    for(NPC npc : CitizensAPI.getNPCRegistry().sorted()){
                        if(npc.getName().equals(name)){
                            npc.despawn();
                            npc.destroy();
                        }
                    }
                }
            } catch (Exception ignored){
            }


            NpcManager.addNpc(NpcUtils.createNpc(EntityType.PLAYER, name, skinName, location), location);
        }
    }
}