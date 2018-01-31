package de.kid2407.womh.game;

import de.kid2407.womh.WomhPlugin;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
    private ArrayList<Player> invitedplayers = new ArrayList<>();
    private ArrayList<Player> playersAlive = new ArrayList<>();
    private HashMap<Role, ArrayList<Player>> roles = new HashMap<>();
    private Player creator;
    private boolean firstnight = true;

    public Game(Player creator) {
        this.creator = creator;
    }

    public void prepareGame() {
        Location creatorLocation = this.getCreator().getLocation();
        creatorLocation.add(0, -1, 0);
        int radius = 16;
        GameAreaGenerator.generateBaseCircle(creatorLocation, Material.OBSIDIAN, radius);
        GameAreaGenerator.generatePlayerPositions(players, creatorLocation, radius - 2);
        creatorLocation.getBlock().setType(Material.STAINED_GLASS);
        creatorLocation.getBlock().setData((byte) 14);

    }

    public void startGame() {
        Player creator = this.getCreator();
        if (this.players.size() > 3) {
            creator.sendMessage("Das Spiel wurde gestartet.");
        } else {
            creator.sendMessage("Es sind nicht genügend Spieler vorhanden.");
        }
    }

    public boolean invite(String player) {
        Player invitedplayer = Bukkit.getServer().getPlayer(player);
        if (invitedplayer == null) {
            return false;
        }

        if (invitedplayer.getDisplayName() == creator.getDisplayName()) {
            creator.sendMessage("Der Spielleiter darf sich nicht selber einladen!");
            return false;
        }
        if (Bukkit.getServer().getOnlinePlayers().contains(invitedplayer)) {
            JSONMessage.create("Du wurdest zu eine Runde eingeladen! benutze ").then("/womh join").color(ChatColor.DARK_GREEN).runCommand("/womh join").then(" um beizutreten.").send(invitedplayer);
            this.invitedplayers.add(invitedplayer);
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

    public boolean isPlayerInvited(Player player) {
        return invitedplayers.contains(player);
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
