package service;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

public class Service {

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
}
