package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO implements GameDAO {

    private final List<GameData> games = new ArrayList<>();

    public void clear() {
        games.clear();
    }

    public int createGame(GameData game) {
        games.add(game);
        return game.gameID();
    }

    public GameData getGame(int id) {
        for (GameData game: games) {
            if(game.gameID() == id) {
                return game;
            }
        }
        return null; // need to throw an exception or something
    }

    public List<GameData> listGames() {
        return games;
    }

    public void updateGame(GameData newGame) {
        int index = 0;
        for (GameData game: games) {
            if (game.gameID() == newGame.gameID()) {
                games.set(index, newGame);
            }
            index++;
        }
    }
}