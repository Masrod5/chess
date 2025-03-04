package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {
    @BeforeEach
    @ValueSource(classes = {MemoryAuthDAO.class})
    void cleanSlate() throws DataAccessException {
        AuthDAO dataAccess = new MemoryAuthDAO();
        dataAccess.clear();
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryAuthDAO.class})
    void clearAuth(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MemoryAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        dataAccess.clear();

        assertNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryUserDAO.class})
    void clearUser(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MemoryUserDAO();
        dataAccess.createUser(new UserData("username", "password", "email"));

        dataAccess.clear();

        assertNull(dataAccess.getUser("username"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void clearGame(Class<? extends GameDAO> dbClass) throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        dataAccess.createGame(new GameData(1,"authToken", "username", "gamename", new ChessGame()));

        dataAccess.clear();

        assertNull(dataAccess.getGame(1));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryAuthDAO.class})
    void createAuthSuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MemoryAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        assertNotNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryAuthDAO.class})
    void createAuthUnsuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MemoryAuthDAO();
        dataAccess.createAuth(new AuthData("", "username"));

        assertThrows(DataAccessException.class, () -> {
            throw new DataAccessException("bad request");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryAuthDAO.class})
    void deleteAuthSuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MemoryAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));
        dataAccess.deleteAuth(new AuthData("authToken", "username"));

        assertNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void createGameSuccessful() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        var expected = new GameData(1, "white", "black", "gameName", new ChessGame());

        assertEquals(dataAccess.getGame(1), expected);
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void getGameSuccessful() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        assertNotNull(dataAccess.getGame(1));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void getGameDoesNotExist() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));

        assertNull(dataAccess.getGame(2));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void listGames() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        ArrayList<GameData> gameList = new ArrayList<>();

        dataAccess.createGame(new GameData(1, "white", "black", "gameName", new ChessGame()));
        dataAccess.createGame(new GameData(2, "white", "black", "gameName2", new ChessGame()));
        dataAccess.createGame(new GameData(3, "white", "black", "gameName2", new ChessGame()));

        assertEquals(dataAccess.listGames().size(), 3);
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void updateGameSuccessful() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();
        dataAccess.createGame(new GameData(1, "null", "null", "gameName", new ChessGame()));
        var oldGame = dataAccess.getGame(1);

        dataAccess.updateGame(new GameData(1, "white", "null", "gameName", new ChessGame()));
        var newGame = new GameData(1, "white", "null", "gameName", new ChessGame());

        assertNotEquals(oldGame, newGame);
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryGameDAO.class})
    void updateGameBadRequest() throws Exception {

        GameDAO dataAccess = new MemoryGameDAO();

        assertThrows(DataAccessException.class, () -> {
            dataAccess.updateGame(new GameData(1, "white", "null", "gameName", new ChessGame()));
            dataAccess.updateGame(new GameData(1, "white", null, "gameName", new ChessGame()));

            throw new DataAccessException("");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryUserDAO.class})
    void createUserSuccessful(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MemoryUserDAO();
        dataAccess.createUser(new UserData("username", "password", "email"));

        assertNotNull(dataAccess.getUser("username"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryUserDAO.class})
    void createUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MemoryUserDAO();
        dataAccess.createUser(new UserData("username", "", "email"));

        assertThrows(DataAccessException.class, () -> {
            throw new DataAccessException("bad request");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryUserDAO.class})
    void getUserGoodRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MemoryUserDAO();
        dataAccess.createUser(new UserData("username", "password", "email"));

        assertNotNull(dataAccess.getUser("username"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MemoryUserDAO.class})
    void getUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MemoryUserDAO();
        dataAccess.createUser(new UserData("username", "", "email"));

        assertNull(dataAccess.getUser("user"));
    }


}
