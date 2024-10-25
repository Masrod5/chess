package dataaccess;

import model.gameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {

    private final ArrayList<gameData> games = new ArrayList<>();


    public void createGame() {

        gameData game;
//        games.add(game);
    }


    public gameData getGame(int id) {
        for (gameData game: games) {
            if(game.gameID() == id) {
                return game;
            }
        }
        return null; // need to throw an exception or something
    }

    public ArrayList<gameData> listGames() {
        return games;
    }

    public gameData updateGame(gameData game) {
        for (gameData gameIndex: games) {
            if (game.gameID() == gameIndex.gameID()) {
                gameIndex = game;
                break;
            }
        }
        return game;
    }



}