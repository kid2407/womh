package de.kid2407.womh;

import de.kid2407.womh.command.CommandHandlerWomh;
import de.kid2407.womh.util.Constants;
import de.kid2407.womh.util.WomhLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tobias Franz on 10.01.2018.
 */
public class WomhPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        WomhLogger.info("Enabling womh VERSION " + Constants.VERSION);
        registerCommands();
    }

    @Override
    public void onDisable() {
        WomhLogger.info("Disabling womh VERSION " + Constants.VERSION);
    }

    private void registerCommands() {
        getCommand("womh").setExecutor(new CommandHandlerWomh());
    }
}
