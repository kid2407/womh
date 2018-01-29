package de.kid2407.womh.game;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Tobias Franz on 12.01.2018.
 */
public class Game {

    private ArrayList<Player> players;
    private Map<Role, ArrayList<Player>> roles;
    private Player creator;
    private boolean firstnight = true;

    public Game(Player creator) {
        this.creator = creator;
    }

    public boolean startGame() {
        if (players.size() > 0) {
            org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(creator.getName());
            player.sendMessage("Das Spiel wurde gestartet.");
            return true;
        } else {
            return false;
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public int getPlayerCount() {
        return this.players.size();
    }
}
