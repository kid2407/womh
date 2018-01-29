package de.kid2407.womh;

import de.kid2407.womh.command.CommandHandlerWomh;
import de.kid2407.womh.game.Game;
import de.kid2407.womh.util.Constants;
import de.kid2407.womh.util.WomhLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Tobias Franz on 10.01.2018.
 */
public class WomhPlugin extends JavaPlugin {

    public static Game game;

    @Override
    public void onEnable() {
        WomhLogger.info("Enabling womh VERSION " + Constants.VERSION);
        registerCommands();

        try {
            final File[] libs = new File[]{
                    new File(getDataFolder(), "gson-2.8.1.jar"),
                    new File(getDataFolder(), "guava-23.6-jre.jar")};
            for (final File lib : libs) {
                if (!lib.exists()) {
                    JarUtils.extractFromJar(lib.getName(),
                            lib.getAbsolutePath());
                }
            }
            for (final File lib : libs) {
                if (!lib.exists()) {
                    getLogger().warning(
                            "There was a critical error loading My plugin! Could not find lib: "
                                    + lib.getName());
                    Bukkit.getServer().getPluginManager().disablePlugin(this);
                    return;
                }
                addClassPath(JarUtils.getJarUrl(lib));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        WomhLogger.info("Disabling womh VERSION " + Constants.VERSION);
    }

    private void registerCommands() {
        getCommand("womh").setExecutor(new CommandHandlerWomh());
    }

    private void addClassPath(final URL url) throws IOException {
        final URLClassLoader sysloader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(sysloader, new Object[]{url});
        } catch (final Throwable t) {
            t.printStackTrace();
            throw new IOException("Error adding " + url + " to system classloader");
        }
    }
}
