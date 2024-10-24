package dataaccess;

import model.gameData;

import java.util.List;

public interface GameDAO {

    /** createGame
     * getGame
     * listGames
     * updateGame
     */

    gameData createGame() throws DataAccessException;
    gameData getGame(int gameID) throws DataAccessException;
    List<gameData> listGames() throws DataAccessException;
    gameData updateGame(gameData game) throws DataAccessException;

}
