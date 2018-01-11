package de.kid2407.womh.command;

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

    private static final String[] COMMANDS = {"create", "invite", "start", "pause", "unpause"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.setHealthScale(player.getHealthScale() + 10d);
            player.sendMessage("Success!");

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        //convert to list
        final List<String> completions = new ArrayList<>(Arrays.asList(COMMANDS));
        //copy matches of first argument from list
        StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
        // sorting
        Collections.sort(completions);

        return completions;
    }
}
