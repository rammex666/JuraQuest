package fr.rammex.juraQuest.npc;

import fr.rammex.juraQuest.JuraQuest;
import fr.rammex.juraQuest.npc.trait.TraitOnClick;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.List;

public class NpcUtils {

    public static NPC createNpc(EntityType entityType, String name, String skinName, Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, name);

        npc.getOrAddTrait(SkinTrait.class).setSkinName(skinName);
        npc.getOrAddTrait(TraitOnClick.class).getNPC();


        addToConfig(name, skinName, location);
        return npc;
    }


    private static void addToConfig(String name, String skinName, Location location){
        FileConfiguration config = JuraQuest.getInstance().getConfig();

        config.set("npcs."+name+".name", name);
        config.set("npcs."+name+".skin_name", skinName);
        config.set("npcs."+name+".location", location);

        JuraQuest.getInstance().saveConfig();
    }

    public static boolean isNpcExist(String name){
        ConfigurationSection npcSection = JuraQuest.getInstance().getConfig().getConfigurationSection("npcs");
        if(npcSection == null){
            return false;
        }
        for(String key : npcSection.getKeys(false)){
            String npcName = npcSection.getString(key + ".name");
            if(npcName.equals(name)){
                return true;
            }
        }
        return false;
    }


}
