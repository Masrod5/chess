package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {

    /** createGame
     * getGame
     * listGames
     * updateGame
     */

    void clear() throws DataAccessException;
    void createGame(GameData game) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;

}
