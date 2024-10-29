package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import chess.ChessGame;
import dataaccess.*;
import model.*;

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

    public void joinGame(JoinGameRequest game, String auth) {

        GameData color = game;
        String yes = "lalala";

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

    public ArrayList<GameData> listGames(String authToken) throws DataAccessException {

        if (authDAO.getAuth(authToken) != null){
            ArrayList<GameData> thing = new ArrayList<>(gameDAO.listGames());
            gameDAO.listGames();
            return thing;
        }else {
            throw new DataAccessException("unauthorized");
        }
    }

    public AuthData register(UserData user) throws DataAccessException {

        UserData info = userDAO.getUser(user.username());

        if (info == null){
            if (user.password().isEmpty() || user.username().isEmpty() || user.email().isEmpty()){
                throw new DataAccessException("bad request");
            }

            userDAO.createUser(user);
            AuthData newAuth = new AuthData(generateToken(), user.username());
//            authDAO.createAuth(newAuth);
            return newAuth;
        }

        throw new DataAccessException("already taken");
//        return null;

    }


    public AuthData login(LoginRequest user) throws DataAccessException {

        // get user by username
        UserData info = userDAO.getUser(user.username());

        if (info != null && Objects.equals(user.password(), info.password())){


            AuthData newAuth = new AuthData(generateToken(), user.username());

//            String test = authDAO.toString();

//            if (authDAO.toString() == "this"){
//                throw new DataAccessException("you are logged in");
//            }
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
        if (auth == null){
            throw new DataAccessException("unauthorized");
        }
        if (authDAO.getAuth(auth) == null){
            throw new DataAccessException("you are not logged in");
        }

        if (auth != null && authDAO.getAuth(auth) != null){
            authDAO.deleteAuth(authDAO.getAuth(auth));

        }

    }

}
