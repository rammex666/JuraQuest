package fr.rammex.juraQuest.npc.trait;

import fr.rammex.juraQuest.JuraQuest;
import fr.skytasul.quests.api.QuestsAPI;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;

import java.util.List;

public class TraitOnClick extends Trait {
    public TraitOnClick(){
        super("onClick");
        JuraQuest plugin = JuraQuest.getInstance();
    }

    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        List<String> commands = JuraQuest.getInstance().getConfig().getStringList("npcs." + npc.getName() + ".commands_on_click");
        if(commands == null){
            return;
        }

        for(String command : commands){
            command = command.replace("%player%", event.getClicker().getName());
            JuraQuest.getInstance().getServer().dispatchCommand(JuraQuest.getInstance().getServer().getConsoleSender(), command);
        }
    }

    @Override
    public void onSpawn() {
        JuraQuest plugin = JuraQuest.getInstance();

        FileConfiguration config = plugin.getConfig();

        boolean broadcast = config.getBoolean("broadcast_on_spawn.enabled");
        if(broadcast){
            String message = config.getString("broadcast_on_spawn.message");
            plugin.getServer().broadcastMessage(message);
        }
    }


}
