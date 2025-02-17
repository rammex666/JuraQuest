package fr.rammex.juraQuest.commands;

import fr.rammex.juraQuest.JuraQuest;
import fr.rammex.juraQuest.npc.NpcManager;
import fr.rammex.juraQuest.npc.NpcUtils;
import fr.rammex.juraQuest.tasks.NpcLoader;
import fr.rammex.juraQuest.tasks.NpcSpawner;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandJuraQuest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use this command");
        }
        Player player = (Player) sender;

        if(args[0].equals("create")){
            String npcName = args[1];
            String skinName = args[2];
            Location location = player.getLocation();
            EntityType entityType = EntityType.PLAYER;

            if(!NpcUtils.isNpcExist(npcName)){
                NpcManager.addNpc(NpcUtils.createNpc(entityType, npcName, skinName, location), location);
                player.sendMessage("NPC "+npcName+" created");
            } else {
                player.sendMessage("This NPC already exists");
            }
        }

        if(args[0].equals("reload")){
            JuraQuest.getInstance().reloadConfig();

            // Despawn tout les npcs
            for (NPC npc : NpcManager.getNpcList()) {
                try{
                    npc.despawn();
                } catch(Exception ignored){}
            }

            // Reload le random spawn des NPC
            NpcSpawner npcSpawner = new NpcSpawner(JuraQuest.getInstance());
            npcSpawner.stopSpawning();
            Bukkit.getScheduler().runTaskLater(JuraQuest.getInstance(), () -> {
                npcSpawner.startSpawning();
            }, 40L);

            player.sendMessage("Config reloaded");
        }

        if(args[0].equals("delete")){
            String npcName = args[1];
            if(NpcUtils.isNpcExist(npcName)){
                NpcManager.removeNpc(NpcManager.getNpcFromName(npcName));
                NpcManager.getNpcFromName(npcName).despawn();
                NpcManager.getNpcFromName(npcName).destroy();
                deleteNpcInConfig(npcName);
                player.sendMessage("NPC "+npcName+" deleted");
            } else {
                player.sendMessage("This NPC does not exist");
            }
        }

        return false;
    }


    private void deleteNpcInConfig(String npcName){
        JuraQuest.getInstance().getConfig().set("npcs."+npcName, null);
        JuraQuest.getInstance().saveConfig();
    }
}
