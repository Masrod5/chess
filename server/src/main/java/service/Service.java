package service;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;

public class Service {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public Service(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
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



}
