package service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import dataaccess.*;
import model.LoginRequest;
import model.AuthData;
import model.RegesterRequest;
import model.UserData;
import model.gameData;

public class UserService {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    // constructer that uses a userDAO
    public UserService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ArrayList<gameData> listGames(String authToken) throws DataAccessException {

        if (authDAO.getAuth(authToken) != null){
            ArrayList<gameData> thing = new ArrayList<>(gameDAO.listGames());
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
        int i = 0;
        i+=1;

    }

}
