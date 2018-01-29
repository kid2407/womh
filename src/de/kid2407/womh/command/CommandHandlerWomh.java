package de.kid2407.womh.command;

import de.kid2407.womh.WomhPlugin;
import de.kid2407.womh.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tobias Franz on 10.01.2018.
 */
public class CommandHandlerWomh implements CommandExecutor, TabCompleter {

    private static final String[] COMMANDS = {"create", "invite", "join", "start", "pause", "unpause"};

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
            if (args[0].equals("invite")){
                player.sendMessage("Du wurdest zu einem Spiel eingeladen! Schreibe " + "{\"text\":\"test\"}" + ChatColor.WHITE + "zum beitreten!");
            }
            if (args[0].equals("join")){
                if (WomhPlugin.game.isPlayerInGame(player)) {
                    player.sendMessage("Du bist bereits in dieser Runde!");
                } else{
                  WomhPlugin.game.addPlayer(player);
                  player.sendMessage("Du bist erfolgreich dem Spiel beigetreten!");
                }
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
