package service;

import java.util.Objects;
import java.util.UUID;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.LoginRequest;
import model.AuthData;
import model.RegesterRequest;
import model.UserData;

public class UserService {

    UserDAO userDAO;
    AuthDAO authDAO;
    // constructer that uses a userDAO
    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData register(UserData user) throws DataAccessException {

        UserData info = userDAO.getUser(user.userName());

        if (info == null){
            userDAO.createUser(user);
            AuthData newAuth = new AuthData(generateToken(), user.userName());
            authDAO.createAuth(newAuth);
            return newAuth;
        }

        return null;

    }


    public AuthData login(LoginRequest user) throws DataAccessException {

        // get user by username
        UserData info = userDAO.getUser(user.username());
        // check if the passwords match
        if (info != null && Objects.equals(user.password(), info.password())){
            AuthData newAuth = new AuthData(generateToken(), user.username());


            authDAO.createAuth(newAuth);

            // create a new auth token
            // add the new auth token to the database somehow
            // return the auth token
            return newAuth;
        }

        return null;
    }


    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void logout(AuthData auth) throws DataAccessException {


//        String user = auth.authToken();
        if (auth != null && authDAO.getAuth(auth.authToken()) != null){
            authDAO.deleteAuth(authDAO.getAuth(auth.authToken()));
        }
    }

}
