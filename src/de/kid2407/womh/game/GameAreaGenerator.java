package de.kid2407.womh.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Tobias Franz on 30.01.2018.
 */
public class GameAreaGenerator {

    public static void generateBaseCircle(Location loc, Material mat, int r) {
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for (int x = cx - r; x <= cx + r; x++) {
            for (int z = cz - r; z <= cz + r; z++) {
                if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                    w.getBlockAt(x, cy, z).setType(mat);
                }
            }
        }
    }

    /**
     * @param players Liste der Mitspieler
     * @param center Position des Spielleiters bei starten
     * @param radius Radius des Spielfeldes
     */
    public static void generatePlayerPositions(ArrayList<Player> players, Location center, int radius) {
        int playercount = players.size();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        float angle = 360f / playercount;
        for (int i = 0; i < playercount; i++) {
            int currentX = centerX + (int) (radius * Math.cos(Math.toRadians((double) (270 - angle * i))));
            int currentZ = centerZ + (int) (radius * Math.sin(Math.toRadians((double) (270 - angle * i))));
            Location playerLocation = new Location(center.getWorld(), currentX, centerY, currentZ);
            playerLocation.setY(playerLocation.getY() +1.5d);
            players.get(i).teleport(playerLocation);
            System.out.println("Wert von i: " + i);
            playerLocation.getBlock().setType(Material.GLOWSTONE);
        }
    }
}
