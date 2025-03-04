package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO implements GameDAO{

    private final ArrayList<GameData> games = new ArrayList<>();

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public int createGame(GameData game) throws DataAccessException {
        games.add(game);
        return game.gameID();
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData game: games){
            if (game.gameID() == gameID){
                return game;
            }
        }
        return null;
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        return games;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        for (int i = 0; i < games.size(); i++){
            if (games.get(i).gameID() == game.gameID()){
                games.set(i, game);
                break;
            }
        }
    }
}
