package service;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

public class Service {

    static int gameID = 0;
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public Service(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public void logout(String header) throws DataAccessException {
        if(authDAO.getAuth(header) == null){
            throw new DataAccessException("unauthorized");
        }
        authDAO.deleteAuth(authDAO.getAuth(header));
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDAO.getAuth(authToken);
    }

    public AuthData register(UserData user) throws DataAccessException {

        UserData info;
        if (userDAO == null){
            info = null;
        }else {
            info = userDAO.getUser(user.username());
        }
        if (user.password() == null || user.username() == null || user.email() == null){
            throw new DataAccessException("bad request");
        }
        if (user.password().isEmpty() || user.username().isEmpty() || user.email().isEmpty()){
            throw new DataAccessException("bad request");
        }
        if (info == null){
            userDAO.createUser(user);
            AuthData newAuth = new AuthData(generateToken(), user.username());
            authDAO.createAuth(newAuth);
            return newAuth;
        }
        throw new DataAccessException("already taken");
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    public AuthData login(LoginRequest loginRequest) throws DataAccessException {
        UserData user = userDAO.getUser(loginRequest.username());

        if (user != null && BCrypt.checkpw(loginRequest.password(), user.password())) {
            AuthData newAuth = new AuthData(generateToken(), loginRequest.username());
            authDAO.createAuth(newAuth);
            return newAuth;
        }
        throw new DataAccessException("unauthorized");
    }

    public ListGames listGames(String authToken) throws DataAccessException {
        if (authDAO.getAuth(authToken) != null) {
            ArrayList<GameData> games = new ArrayList<>(gameDAO.listGames());
            return new ListGames(games);
        }else {
            throw new DataAccessException("unauthorized");
        }
    }

    public GameData createGame(String authorization, GameData game) throws DataAccessException {
        gameID++;
        if (game.gameName().isEmpty()){
            throw new DataAccessException("bad request");
        }
        if (authDAO.getAuth(authorization) == null){
            throw new DataAccessException("unauthorized");
        }

        var i = gameDAO.createGame(new GameData(gameID, null, null, game.gameName(), game.game()));

        return new GameData(i, null, null, null, null);
    }

    public void joinGame(JoinGameRequest joinRequest, String auth) throws DataAccessException {

        if (joinRequest.playerColor() == null || gameDAO.getGame(joinRequest.gameID()) == null){
            throw new DataAccessException("bad request");
        }
        if (authDAO.getAuth(auth) == null){
            throw new DataAccessException("unauthorized");
        }

        String color = joinRequest.playerColor();
        int gameID = joinRequest.gameID();

        GameData oldGame = gameDAO.getGame(gameID);
        String newWhiteName = oldGame.whiteUsername();
        String newBlackName = oldGame.blackUsername();

        if (Objects.equals(color, "WHITE")){
            if (gameDAO.getGame(gameID).whiteUsername() != null){
                throw new DataAccessException("already taken");
            }
            newWhiteName = authDAO.getAuth(auth).username();

        }else if (Objects.equals(color, "BLACK")){
            if (gameDAO.getGame(gameID).blackUsername() != null){
                throw new DataAccessException("already taken");
            }
            newBlackName = authDAO.getAuth(auth).username();

        }else{
            throw new DataAccessException("bad request");
        }

        GameData updatedGame = new GameData(oldGame.gameID(), newWhiteName, newBlackName, oldGame.gameName(), oldGame.game());

        gameDAO.updateGame(updatedGame);
    }
}
