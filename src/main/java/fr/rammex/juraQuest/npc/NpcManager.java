package fr.rammex.juraQuest.npc;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

import java.util.*;

public class NpcManager {

    private static List<NPC> npcList = new ArrayList<>();
    private static Map<NPC, Location> locationNpc = new HashMap<>();

    public static void addNpc(NPC npc, Location location){
        npcList.add(npc);
        locationNpc.put(npc, location);
    }

    public static void removeNpc(NPC npc){
        npcList.remove(npc);
        locationNpc.remove(npc);
    }

    public static List<NPC> getNpcList(){
        return npcList;
    }

    public static NPC getNpcFromName(String name){
        for(NPC npc : npcList){
            if(npc.getName().equals(name)){
                return npc;
            }
        }
        return null;
    }

    public static Location getLocation(NPC npc){
        return locationNpc.get(npc);
    }

    public static NPC getRandomNpc(){
        Random random = new Random();
        if(npcList.isEmpty()){
            return null;
        }
        int index = random.nextInt(npcList.size());
        return npcList.get(index);
    }
}
