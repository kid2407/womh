package de.kid2407.womh.game;

import java.util.ArrayList;

/**
 * Created by Tobias Franz on 12.01.2018.
 */
public class GameRegistry {

    private static ArrayList<Game> games = new ArrayList<>();

    public static Game createGame(Player creator){
        Game game = new Game(creator);
        games.add(game);
        return game;
    }
}
