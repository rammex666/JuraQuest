package fr.rammex.juraQuest.commands;

import fr.rammex.juraQuest.JuraQuest;
import fr.rammex.juraQuest.npc.NpcManager;
import fr.rammex.juraQuest.npc.NpcUtils;
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

        if(args[0].equals("npc")){
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
            player.sendMessage("Config reloaded");
        }

        return false;
    }
}
