package de.kid2407.womh.command;

import de.kid2407.womh.WomhPlugin;
import de.kid2407.womh.game.Game;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tobias Franz on 10.01.2018.
 */
public class CommandHandlerWomh implements CommandExecutor, TabCompleter {

    private static final String[] COMMANDS = {"addBlindness", "allowInvite", "create", "delete", "day", "invite", "join", "leave", "removePlayer", "removeBlindness", "night", "unpause", "pause", "start", "votekill", "voteprotect"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args[0].equals("create")) {
                if (WomhPlugin.game == null) {
                    WomhPlugin.game = new Game(player);
                    player.sendMessage("Das Spiel wurde erstellt! Lade Leute mit /womh invite ein!");
                } else {
                    player.sendMessage("Es gibt bereits ein Spiel!");
                }
            }
            if (args[0].equals("delete")) {
                if (WomhPlugin.game == null) {
                    player.sendMessage("Es gibt kein Spiel!");
                } else {
                    WomhPlugin.game = null;
                    player.sendMessage("Das Spiel wurde erfolgreich gelöscht!");
                }
            }
            if (args[0].equals("invite")) {
                if (player.getDisplayName() == WomhPlugin.game.getCreator().getDisplayName()) {
                    if (args.length == 2) {
                        if (WomhPlugin.game.invite(args[1])) {
                            player.sendMessage("Wurde erfolgreich eingeladen!");
                        } else{
                            player.sendMessage("Dieser Spieler ist nicht online!");
                        }
                    } else{
                        player.sendMessage("Es wurde kein Spielername angegeben!");
                    }
                } else {
                    player.sendMessage("Da du dieses Spiel nicht erstellt hast, darfst du niemanden einladen!");
                }
            }

            if (args[0].equals("allowInvite")) {
                if (player.getDisplayName() == WomhPlugin.game.getCreator().getDisplayName()) {

                }
            }

            if (args[0].equals("join")) {
                if (WomhPlugin.game != null) {
                    if (WomhPlugin.game.isPlayerInvited(player)) {
                        if (WomhPlugin.game.isPlayerInGame(player)) {
                            player.sendMessage("Du bist bereits in dieser Runde!");
                        } else {
                            WomhPlugin.game.addPlayer(player);
                            player.sendMessage("Du bist erfolgreich dem Spiel beigetreten!");
                        }
                    } else {
                        player.sendMessage("Du bist nicht eingeladen!");
                    }
                } else {
                    player.sendMessage("Es existiert kein Spiel, dem beigetreten werden könnte!");
                }
            }

            if (args[0].equalsIgnoreCase("start")) {
                if (WomhPlugin.game != null) {
                    WomhPlugin.game.generateCreatorBook();
                    WomhPlugin.game.prepareGame();
                    WomhPlugin.game.startGame();
                } else {
                    player.sendMessage("Kein Spiel vorhanden!");
                }
            }

            if (args[0].equalsIgnoreCase("kill")) {
                if (WomhPlugin.game != null) {
                    WomhPlugin.game.killPlayer(player);
                }
            }
            if (args[0].equals("leave")) {
                if (WomhPlugin.game.isPlayerInGame(player)) {
                    WomhPlugin.game.removePlayer(player);
                    player.sendMessage("Du hat das Spiel erfolgreich verlassen!");
                } else {
                    player.sendMessage("Du bist in keinem Spiel!");
                }
            }
            if (args[0].equals("night")) {
                for (Player womhplayer : WomhPlugin.game.getPlayers()) {
                    womhplayer.setWalkSpeed(0);
                    womhplayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                    womhplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
                }
                player.getWorld().setTime(18000);
            }
            if (args[0].equals("day")) {
                for (Player womhplayer : WomhPlugin.game.getPlayers()) {
                    womhplayer.setWalkSpeed(0.2f);
                    womhplayer.removePotionEffect(PotionEffectType.BLINDNESS);
                    womhplayer.removePotionEffect(PotionEffectType.JUMP);
                }
                player.getWorld().setTime(6000);
            }
            if (args[0].equals("addBlindness")) {
                WomhPlugin.game.addBlindness(player);
            }
            if (args[0].equals("removeBlindness")) {
                WomhPlugin.game.removeBlindness(player);
            }
            if (args[0].equals("removePlayer")) {
                WomhPlugin.game.removePlayer(player);
            }
            if (args[0].equals("votekill")) {
                WomhPlugin.game.votekill(args[1]);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            //convert to list
            List<String> completions = new ArrayList<>();
            //copy matches of first argument from list
            StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
            // sorting
            Collections.sort(completions);

            return completions;
        }

        return new ArrayList<>();
    }
}
