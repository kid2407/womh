package de.kid2407.womh.game;

import de.kid2407.womh.WomhPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tobias Franz on 12.01.2018.
 */
public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> playersAlive = new ArrayList<>();
    private HashMap<Role, ArrayList<Player>> roles = new HashMap<>();
    private Player creator;
    private boolean firstnight = true;

    public Game(Player creator) {
        this.creator = creator;
    }

    public boolean startGame() {
        if (players.size() > 0) {
            Player player = Bukkit.getServer().getPlayer(creator.getName());
            player.sendMessage("Das Spiel wurde gestartet.");
            return true;
        } else {
            return false;
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        this.playersAlive.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.playersAlive.remove(player);
    }

    public void addBlindness(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
    }

    public void removeBlindness(Player player) {
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public boolean isPlayerInGame(Player player) {
        return this.players.contains(player);
    }

    public boolean isPlayerAlive(Player player) {
        return playersAlive.contains(player);
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean killPlayer(Player player) {
        if (playersAlive.contains(player)) {
            playersAlive.remove(player);
            return true;
        }
        return false;
    }

    public Player getCreator() {
        return creator;
    }

    public void generateCreatorBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        String page = "Liste aller Spieler:\n\n";
        for (Player womhPlayer : WomhPlugin.game.getPlayers()) {
            if (this.isPlayerAlive(womhPlayer)) {
                page += ChatColor.DARK_GREEN.toString() + womhPlayer.getDisplayName() + ChatColor.RESET + "\n";
            } else {
                page += ChatColor.DARK_RED.toString() + ChatColor.STRIKETHROUGH + womhPlayer.getDisplayName() + ChatColor.RESET + "\n";
            }
        }
        bookMeta.addPage(page);
        bookMeta.setAuthor("Das Plugin");
        bookMeta.setTitle("Buch der Spieler");
        bookMeta.setPages(page);

        book.setItemMeta(bookMeta);

        WomhPlugin.game.getCreator().getInventory().remove(Material.WRITTEN_BOOK);

        WomhPlugin.game.getCreator().updateInventory();

        WomhPlugin.game.getCreator().getInventory().addItem(book);
    }
}
