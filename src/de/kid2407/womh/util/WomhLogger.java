package de.kid2407.womh.util;

import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * Created by Tobias Franz on 10.01.2018.
 */
public class WomhLogger {

    public static void info(String message) {
        Bukkit.getLogger().info(message);
    }

    public static void debug(String message) {
        Bukkit.getLogger().log(Level.FINEST, message);
    }

    public static void error(String message) {
        Bukkit.getLogger().severe(message);
    }
}
