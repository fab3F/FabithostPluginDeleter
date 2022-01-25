package fab3f.plugindeleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class PluginDelCommand implements CommandExecutor {

    boolean DoReload = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.isOp()) {

            String[] toDelete = {"plugins/fabithost.jar", "plugins/Hibernate"};

            delete(toDelete, sender);
            if (DoReload) {
                sender.sendMessage("Ein kompletter Reload wird gestartet!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rl confirm");
            }

        }else{
            sender.sendMessage("Du bist kein Operator. Dieser Befehl dient dem Entfernen von ungenutzten Plugins!");
            return false;
        }

        return true;
    }


    boolean delete(String[] paths, CommandSender cs){
        for(String s : paths){
            File f = new File(s);
            if(f.delete()) {
                cs.sendMessage("Entfernt: " + s);
                DoReload = true;
            }else {
                if(deleteDirectory(f)) {
                    cs.sendMessage("Entfernt: " + s);
                    DoReload = true;
                }else {
                    cs.sendMessage("NICHT Entfernt: " + s);
                }
            }
        }
        return false;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
