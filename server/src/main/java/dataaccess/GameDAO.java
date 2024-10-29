package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {

    /** createGame
     * getGame
     * listGames
     * updateGame
     */

    void createGame(GameData game) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    GameData updateGame(GameData game) throws DataAccessException;

}
