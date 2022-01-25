package fab3f.plugindeleter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PluginDeleter extends JavaPlugin {

    boolean DoReload = false;


    @Override
    public void onEnable() {
        getLogger().info("PluginDeleter aktiviert");
        getCommand("plugindel").setExecutor(new PluginDelCommand());

        String[] toDelete = {"plugins/fabithost.jar", "plugins/Hibernate"};

        delete(toDelete);
        if(DoReload){
            getLogger().info("Ein kompletter Reload wird gestartet!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rl confirm");
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("PluginDeleter deaktiviert");
    }

    boolean delete(String[] paths){
        for(String s : paths){
            File f = new File(s);
            if(f.delete()) {
                getLogger().info("Entfernt: " + s);
                DoReload = true;
            }else {
                if(deleteDirectory(f)) {
                    getLogger().info("Entfernt: " + s);
                    DoReload = true;
                }else {
                    getLogger().info("NICHT Entfernt: " + s);
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
