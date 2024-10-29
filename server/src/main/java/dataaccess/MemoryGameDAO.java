package dataaccess;

import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {

    private final ArrayList<GameData> games = new ArrayList<>();


    public void createGame(GameData game) {
        games.add(game);
    }


    public GameData getGame(int id) {
        for (GameData game: games) {
            if(game.gameID() == id) {
                return game;
            }
        }
        return null; // need to throw an exception or something
    }

    public ArrayList<GameData> listGames() {
        return games;
    }

    public GameData updateGame(GameData game) {
        for (GameData gameIndex: games) {
            if (game.gameID() == gameIndex.gameID()) {
                gameIndex = game;
                break;
            }
        }
        return game;
    }



}