package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

public class Service {

    static int gameID = 0;
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    // constructer that uses a userDAO
    public Service(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public void joinGame(JoinGameRequest request, String auth) throws DataAccessException {
        if (request.playerColor() == null || gameDAO.getGame(request.gameID()) == null){
            throw new DataAccessException("bad request");
        }
//        if (){
//            throw new DataAccessException("bad request");
//        }
        if (authDAO.getAuth(auth) == null) {
            throw new DataAccessException("unauthorized");
        }

        String color = request.playerColor();

        if (Objects.equals(color, "WHITE")) {
            if (gameDAO.getGame(request.gameID()).whiteUsername() != null) {
                throw new DataAccessException("already taken");
            }
            GameData oldGame = gameDAO.getGame(request.gameID());
            GameData updatedGame = new GameData(oldGame.gameID(), authDAO.getAuth(auth).username(), oldGame.blackUsername(), oldGame.gameName(), oldGame.game());

            gameDAO.updateGame(updatedGame);
        } else
        if (Objects.equals(color, "BLACK")) {
            if (gameDAO.getGame(request.gameID()).blackUsername() != null) {
                throw new DataAccessException("already taken");
            }
            GameData oldGame = gameDAO.getGame(request.gameID());
            GameData updatedGame = new GameData(oldGame.gameID(), oldGame.whiteUsername(), authDAO.getAuth(auth).username(), oldGame.gameName(), oldGame.game());

            gameDAO.updateGame(updatedGame);
        } else {
            throw new DataAccessException("bad request");
        }

    }

    public GameData createGame(String authToken, GameData game) throws DataAccessException {

        gameID++;
        if(game.gameName().isEmpty()){
            throw new DataAccessException("bad request");
        }
        if (authDAO.getAuth(authToken) == null){
            throw new DataAccessException("unauthorized");
        }

        gameDAO.createGame(new GameData(gameID, null, null, game.gameName(), new ChessGame()));

        return new GameData(gameID, null, null, null, null);
    }

    public ListGames listGames(String authToken) throws DataAccessException {

        if (authDAO.getAuth(authToken) != null){
            ArrayList<GameData> thing = new ArrayList<>(gameDAO.listGames());
//            gameDAO.listGames();
            return new ListGames(thing);
        }else {
            throw new DataAccessException("unauthorized");
        }
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    public AuthData register(UserData user) throws DataAccessException {

        UserData info = userDAO.getUser(user.username());
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
//        return null;

    }


    public AuthData login(LoginRequest user) throws DataAccessException {

        // get user by username
        UserData info = userDAO.getUser(user.username());

        if (info != null && BCrypt.checkpw(user.password(), info.password())){

            AuthData newAuth = new AuthData(generateToken(), user.username());

            authDAO.createAuth(newAuth);

            return newAuth;
        }

        throw new DataAccessException("unauthorized");
    }


    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void logout(String auth) throws DataAccessException {


//        String user = auth.authToken();
        if (auth == null || authDAO.getAuth(auth) == null){
            throw new DataAccessException("unauthorized");
        }

//        if (auth != null && authDAO.getAuth(auth) != null){
            authDAO.deleteAuth(authDAO.getAuth(auth));

//        }

    }

}
