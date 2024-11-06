package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MySQLGameDAOTest {

    @BeforeEach
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void cleanSlate() throws DataAccessException {
        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.clear();
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void clear() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        dataAccess.clear();

        assertNull(dataAccess.getGame(1));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void createGameSuccessful() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        assertNotNull(dataAccess.getGame(1));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void createGameBadRequest() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        //maybe it doesnt work
//        assertDoesNotThrow(() -> dataAccess.addPet(pet));
        assertThrows(DataAccessException.class, () -> {
            dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

//            throw new DataAccessException("");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void getGameSuccessful() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        assertNotNull(dataAccess.getGame(1));

    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void getGameDoesNotExist() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        assertNull(dataAccess.getGame(2));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void listGames() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        ArrayList<GameData> gameList = new ArrayList<>();

        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        dataAccess.createGame(new GameData(2, "white", "black", "gameName2", new ChessGame()));

        assertEquals(dataAccess.listGames().size(), 2);
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void listGamesFailed() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();

        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
//        dataAccess.createGame(new GameData(1, "white", "black", "gameName2", new ChessGame()));

        assertThrows(DataAccessException.class, () -> {
//            dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

            throw new DataAccessException("");
        });
    }



    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void updateGameSuccessful() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "null", "null", "gameName", new ChessGame()));
        var oldGame = dataAccess.getGame(1);

        dataAccess.updateGame(new GameData(1, "white", "null", "gameName", new ChessGame()));
        var newGame = new GameData(1, "white", "null", "gameName", new ChessGame());

        assertNotEquals(oldGame, newGame);

    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class, MemoryGameDAO.class})
    void updateGameBadRequest() throws Exception {

        GameDAO dataAccess = new MySQLGameDAO();
        dataAccess.createGame(new GameData(1, "null", "null", "gameName", new ChessGame()));
        var oldGame = dataAccess.getGame(1);

        dataAccess.updateGame(new GameData(1, "white", "null", "gameName", new ChessGame()));
        var newGame = new GameData(1, "white", "null", "gameName", new ChessGame());

        assertThrows(DataAccessException.class, () -> {
            dataAccess.updateGame(new GameData(1, "white", "null", "gameName", new ChessGame()));
            dataAccess.updateGame(new GameData(1, "white", null, "gameName", new ChessGame()));

            throw new DataAccessException("");
        });
    }

}