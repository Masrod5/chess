package service;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import record.*;

public class Service {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public Service(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public AuthData register(UserData userData) throws DataAccessException {

        UserData getUser = userDAO.getUser(userData.username());
        if (getUser == null){
            userDAO.createUser(userData);
            AuthData newAuth = new AuthData(generateToken(), userData.username());
            authDAO.createAuth(newAuth);
            return newAuth;
        }

        throw new DataAccessException("already taken");
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }



}
