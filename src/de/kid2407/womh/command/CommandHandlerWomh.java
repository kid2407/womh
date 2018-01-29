package de.kid2407.womh.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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
        commandSender.sendMessage("Success!");
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
